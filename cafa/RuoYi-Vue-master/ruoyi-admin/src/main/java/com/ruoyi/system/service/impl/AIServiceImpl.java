package com.ruoyi.system.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysAIModel;
import com.ruoyi.system.domain.SysMember;
import com.ruoyi.system.domain.SysOrder;
import com.ruoyi.system.domain.SysProduct;
import com.ruoyi.system.domain.SysTable;
import com.ruoyi.system.service.IAIService;
import com.ruoyi.system.service.ISysAIModelService;
import com.ruoyi.system.service.ISysMemberService;
import com.ruoyi.system.service.ISysOrderService;
import com.ruoyi.system.service.ISysProductService;
import com.ruoyi.system.service.ISysTableService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AIServiceImpl implements IAIService {

    @Autowired
    private ISysAIModelService sysAIModelService;
    
    @Autowired
    private ISysProductService productService;
    
    @Autowired
    private ISysMemberService memberService;
    
    @Autowired
    private ISysTableService tableService;
    
    @Autowired
    private ISysOrderService orderService;

    public String getAIResponse(String question) {
        String context = buildSystemContext();
        String prompt = context + "\n用户问题：" + question + "\n请作为咖啡店管理系统的智能助手回答问题。";
        return callAIApi(prompt);
    }

    private String buildSystemContext() {
        StringBuilder context = new StringBuilder("系统信息：\n");
        
        List<SysProduct> products = productService.selectProductList(new SysProduct());
        StringBuilder productInfo = new StringBuilder("1. 在售产品：");
        for (SysProduct product : products) {
            if (product.getStatus() != null && "1".equals(product.getStatus())) {
                if (productInfo.length() > 6) {
                    productInfo.append("、");
                }
                productInfo.append(product.getProductName()).append("(").append(product.getPrice()).append("元)");
            }
        }
        if (productInfo.length() == 6) {
            productInfo.append("暂无在售产品");
        }
        context.append(productInfo).append("\n");
        
        List<SysMember> members = memberService.selectMemberList(new SysMember());
        context.append("2. 会员：现有会员").append(members.size()).append("人\n");
        
        List<SysTable> tables = tableService.selectTableList(new SysTable());
        long occupiedCount = tables.stream().filter(t -> t.getStatus() != null && t.getStatus().equals("占用")).count();
        context.append("3. 桌台：共").append(tables.size()).append("张桌台，当前占用").append(occupiedCount).append("张\n");
        
        List<SysOrder> allOrders = orderService.selectOrderList(new SysOrder());
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<SysOrder> todayOrders = allOrders.stream()
                .filter(o -> o.getCreateTime() != null && o.getCreateTime().toString().contains(today))
                .collect(Collectors.toList());
        BigDecimal todaySales = todayOrders.stream()
                .filter(o -> o.getStatus() != null && Integer.parseInt(o.getStatus()) >= 1)
                .map(SysOrder::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        context.append("4. 今日销售：已售出").append(todayOrders.size()).append("单，销售额").append(todaySales).append("元\n");
        
        return context.toString();
    }

    private String callAIApi(String prompt) {
        SysAIModel model = sysAIModelService.selectEnabledModel();
        if (model == null) {
            return "抱歉，未配置启用的AI模型，请联系管理员。";
        }

        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionTimeToLive(60, java.util.concurrent.TimeUnit.SECONDS)
                .setDefaultRequestConfig(org.apache.http.client.config.RequestConfig.custom()
                        .setConnectTimeout(60000)
                        .setSocketTimeout(60000)
                        .setConnectionRequestTimeout(60000)
                        .build())
                .build()) {
            
            HttpPost httpPost = new HttpPost(model.getApiUrl());
            httpPost.setHeader("Authorization", "Bearer " + model.getApiKey());
            httpPost.setHeader("Content-Type", "application/json");

            Map<String, Object> requestBody = new HashMap<>();
            // 根据模型类型设置不同的参数
            if (model.getModelCode().equals("doubao")) {
                requestBody.put("model", "doubao-seed-2-0-pro-260215");
            } else if (model.getModelCode().equals("deepseek")) {
                requestBody.put("model", "deepseek-chat");
            } else if (model.getModelCode().equals("openai")) {
                requestBody.put("model", "gpt-3.5-turbo");
            }
            requestBody.put("messages", new Object[] {
                createMessageMap("system", "你是一个咖啡店管理系统的智能助手，要根据提供的系统信息回答用户问题。"),
                createMessageMap("user", prompt)
            });
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 1000);

            Gson gson = new Gson();
            StringEntity entity = new StringEntity(gson.toJson(requestBody), ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String responseString = EntityUtils.toString(responseEntity);
                    System.out.println("大模型返回结果： = " + responseString);
                    JsonObject jsonObject = JsonParser.parseString(responseString).getAsJsonObject();
                    if (jsonObject.has("choices")) {
                        return jsonObject.get("choices").getAsJsonArray()
                                .get(0).getAsJsonObject()
                                .get("message").getAsJsonObject()
                                .get("content").getAsString();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "抱歉，我暂时无法回答您的问题，请稍后再试。";
    }

    private Map<String, String> createMessageMap(String role, String content) {
        Map<String, String> map = new HashMap<>();
        map.put("role", role);
        map.put("content", content);
        return map;
    }
}
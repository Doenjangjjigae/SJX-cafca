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
import com.ruoyi.system.service.IAIService;
import com.ruoyi.system.service.ISysAIModelService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AIServiceImpl implements IAIService {

    @Autowired
    private ISysAIModelService sysAIModelService;

    public String getAIResponse(String question) {
        String context = buildSystemContext();
        String prompt = context + "\n用户问题：" + question + "\n请作为咖啡店管理系统的智能助手回答问题。";
        return callAIApi(prompt);
    }

    private String buildSystemContext() {
        return "系统信息：\n" +
               "1. 产品：拿铁咖啡(30元)、美式咖啡(25元)、卡布奇诺(35元)\n" +
               "2. 库存：咖啡豆剩余10kg、牛奶剩余5L\n" +
               "3. 今日销售：已售出50杯咖啡，销售额1500元\n" +
               "4. 会员：现有会员100人，今日新增2人\n" +
               "5. 桌台：共10张桌台，当前占用6张\n";
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
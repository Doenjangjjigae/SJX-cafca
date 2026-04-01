package com.ruoyi.system.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.service.IAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/system/ai")
public class AIController {

    @Autowired
    private IAIService aiService;

    @PostMapping("/ask")
    public R<String> askAI(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        if (question == null || question.isEmpty()) {
            return R.fail("问题不能为空");
        }
        String response = aiService.getAIResponse(question);
        return R.ok(response);
    }
}
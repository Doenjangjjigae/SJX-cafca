-- AI模型配置表
CREATE TABLE `sys_ai_model` (
  `model_id` bigint NOT NULL AUTO_INCREMENT COMMENT '模型ID',
  `model_name` varchar(100) NOT NULL COMMENT '模型名称',
  `model_code` varchar(100) NOT NULL COMMENT '模型编码',
  `api_key` varchar(255) NOT NULL COMMENT 'API密钥',
  `api_url` varchar(255) NOT NULL COMMENT 'API地址',
  `is_enabled` tinyint(1) DEFAULT '0' COMMENT '是否启用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`model_id`),
  UNIQUE KEY `uk_model_code` (`model_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI模型配置表';

-- 初始数据
INSERT INTO `sys_ai_model` (`model_name`, `model_code`, `api_key`, `api_url`, `is_enabled`) VALUES
('DeepSeek', 'deepseek', 'your_deepseek_api_key', 'https://api.deepseek.com/v1/chat/completions', 0),
('豆包', 'doubao', 'e128c957-a889-4dc9-bc31-9cd4f47f0f62', 'https://ark.cn-beijing.volces.com/api/v3/chat/completions', 1),
('OpenAI', 'openai', 'your_openai_api_key', 'https://api.openai.com/v1/chat/completions', 0);
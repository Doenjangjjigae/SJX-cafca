-- AI客服菜单配置SQL

-- 1. 插入AI客服目录菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('AI客服', 100, 14, 'ai', 'system/ai/index', 1, 0, 'C', '0', '0', 'system:ai:list', 'chat-line-round', 'admin', NOW(), 'admin', NOW());

-- 获取刚插入的AI客服目录ID
SET @ai_menu_id = LAST_INSERT_ID();

-- 2. 插入AI客服列表菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('AI客服', @ai_menu_id, 1, 'ai', 'system/ai/index', 1, 0, 'M', '0', '0', 'system:ai:list', '', 'admin', NOW(), 'admin', NOW());

-- 3. 插入AI模型配置菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('模型配置', @ai_menu_id, 2, 'aiModel', 'system/aiModel/index', 1, 0, 'M', '0', '0', 'system:aiModel:list', '', 'admin', NOW(), 'admin', NOW());

-- 4. 为超级管理员角色分配AI客服权限
-- 假设超级管理员角色ID为1
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
SELECT 1, menu_id FROM `sys_menu` WHERE perms LIKE 'system:ai:%';

-- 5. 为超级管理员角色分配AI模型配置权限
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
SELECT 1, menu_id FROM `sys_menu` WHERE perms LIKE 'system:aiModel:%';
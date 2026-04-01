-- 桌台管理菜单配置SQL

-- 1. 插入桌台管理目录菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('桌台管理', 100, 10, 'table', 'system/table/index', 1, 0, 'C', '0', '0', 'system:table:list', 'table', 'admin', NOW(), 'admin', NOW());

-- 获取刚插入的桌台管理目录ID
SET @table_menu_id = LAST_INSERT_ID();

-- 2. 插入桌台列表菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('桌台列表', @table_menu_id, 1, 'table', 'system/table/index', 1, 0, 'M', '0', '0', 'system:table:list', '', 'admin', NOW(), 'admin', NOW());

-- 3. 插入桌台管理按钮权限
-- 新增按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('新增', @table_menu_id, 2, '#', '', 1, 0, 'F', '0', '0', 'system:table:add', '', 'admin', NOW(), 'admin', NOW());

-- 修改按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('修改', @table_menu_id, 3, '#', '', 1, 0, 'F', '0', '0', 'system:table:edit', '', 'admin', NOW(), 'admin', NOW());

-- 删除按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('删除', @table_menu_id, 4, '#', '', 1, 0, 'F', '0', '0', 'system:table:remove', '', 'admin', NOW(), 'admin', NOW());

-- 导出按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('导出', @table_menu_id, 5, '#', '', 1, 0, 'F', '0', '0', 'system:table:export', '', 'admin', NOW(), 'admin', NOW());

-- 4. 为超级管理员角色分配桌台管理权限
-- 假设超级管理员角色ID为1
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
SELECT 1, menu_id FROM `sys_menu` WHERE perms LIKE 'system:table:%';

-- 积分配置菜单权限SQL

-- 1. 插入积分配置菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
('积分配置', 100, 10, 'pointConfig', 'system/pointConfig/index', 1, 0, 'C', '0', '0', 'system:pointConfig:list', 'config', 'admin', NOW(), 'admin', NOW(), '积分配置管理');

-- 2. 插入积分配置权限
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
('查询积分配置', (SELECT menu_id FROM sys_menu WHERE menu_name = '积分配置' AND path = 'pointConfig'), 1, '#', '', 1, 0, 'F', '0', '0', 'system:pointConfig:query', '', 'admin', NOW(), 'admin', NOW(), '查询积分配置'),
('新增积分配置', (SELECT menu_id FROM sys_menu WHERE menu_name = '积分配置' AND path = 'pointConfig'), 2, '#', '', 1, 0, 'F', '0', '0', 'system:pointConfig:add', '', 'admin', NOW(), 'admin', NOW(), '新增积分配置'),
('修改积分配置', (SELECT menu_id FROM sys_menu WHERE menu_name = '积分配置' AND path = 'pointConfig'), 3, '#', '', 1, 0, 'F', '0', '0', 'system:pointConfig:edit', '', 'admin', NOW(), 'admin', NOW(), '修改积分配置'),
('删除积分配置', (SELECT menu_id FROM sys_menu WHERE menu_name = '积分配置' AND path = 'pointConfig'), 4, '#', '', 1, 0, 'F', '0', '0', 'system:pointConfig:remove', '', 'admin', NOW(), 'admin', NOW(), '删除积分配置');

-- 3. 给管理员角色分配积分配置权限
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
SELECT 1, menu_id FROM sys_menu WHERE perms LIKE 'system:pointConfig:%';

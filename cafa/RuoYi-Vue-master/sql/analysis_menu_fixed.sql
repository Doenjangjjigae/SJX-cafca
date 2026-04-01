-- 经营分析菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('经营分析', 100, 12, 'analysis', 'system/analysis/index', 1, 0, 'C', 0, 0, 'system:analysis:list', 'chart', 'admin', NOW(), 'admin', NOW(), '经营分析');

-- 获取menu_id
SELECT @menu_id := LAST_INSERT_ID();

-- 经营分析按钮权限
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('查询', @menu_id, 1, '#', '', 1, 0, 'F', 0, 0, 'system:analysis:query', '', 'admin', NOW(), 'admin', NOW(), '');

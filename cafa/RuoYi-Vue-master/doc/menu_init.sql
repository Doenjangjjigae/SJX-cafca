-- 会员管理菜单
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('会员管理', 100, 9, 'member', 'system/member/index', 1, 0, 'C', 0, 0, 'system:member:list', 'people', 'admin', NOW(), 'admin', NOW(), '会员管理');

-- 优惠券管理菜单
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('优惠券管理', 100, 10, 'coupon', 'system/coupon/index', 1, 0, 'C', 0, 0, 'system:coupon:list', 'money', 'admin', NOW(), 'admin', NOW(), '优惠券管理');

-- 收银管理菜单
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('收银管理', 100, 11, 'cashier', 'system/cashier/index', 1, 0, 'C', 0, 0, 'system:cashier:list', 'shopping', 'admin', NOW(), 'admin', NOW(), '收银管理');

-- 会员管理按钮权限
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('新增', (SELECT menu_id FROM sys_menu WHERE menu_name = '会员管理'), 1, '#', '', 1, 0, 'F', 0, 0, 'system:member:add', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('修改', (SELECT menu_id FROM sys_menu WHERE menu_name = '会员管理'), 2, '#', '', 1, 0, 'F', 0, 0, 'system:member:edit', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('删除', (SELECT menu_id FROM sys_menu WHERE menu_name = '会员管理'), 3, '#', '', 1, 0, 'F', 0, 0, 'system:member:remove', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '会员管理'), 4, '#', '', 1, 0, 'F', 0, 0, 'system:member:query', '', 'admin', NOW(), 'admin', NOW(), '');

-- 积分管理按钮权限
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('积分管理', (SELECT menu_id FROM sys_menu WHERE menu_name = '会员管理'), 5, '#', '', 1, 0, 'F', 0, 0, 'system:member:points:list', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('积分添加', (SELECT menu_id FROM sys_menu WHERE menu_name = '会员管理'), 6, '#', '', 1, 0, 'F', 0, 0, 'system:member:points:add', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('积分删除', (SELECT menu_id FROM sys_menu WHERE menu_name = '会员管理'), 7, '#', '', 1, 0, 'F', 0, 0, 'system:member:points:remove', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('积分查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '会员管理'), 8, '#', '', 1, 0, 'F', 0, 0, 'system:member:points:query', '', 'admin', NOW(), 'admin', NOW(), '');

-- 消费记录按钮权限
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('消费记录', (SELECT menu_id FROM sys_menu WHERE menu_name = '会员管理'), 9, '#', '', 1, 0, 'F', 0, 0, 'system:member:consume:list', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('消费记录查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '会员管理'), 10, '#', '', 1, 0, 'F', 0, 0, 'system:member:consume:query', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('消费记录添加', (SELECT menu_id FROM sys_menu WHERE menu_name = '会员管理'), 11, '#', '', 1, 0, 'F', 0, 0, 'system:member:consume:add', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('消费记录修改', (SELECT menu_id FROM sys_menu WHERE menu_name = '会员管理'), 12, '#', '', 1, 0, 'F', 0, 0, 'system:member:consume:edit', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('消费记录删除', (SELECT menu_id FROM sys_menu WHERE menu_name = '会员管理'), 13, '#', '', 1, 0, 'F', 0, 0, 'system:member:consume:remove', '', 'admin', NOW(), 'admin', NOW(), '');

-- 优惠券管理按钮权限
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('新增', (SELECT menu_id FROM sys_menu WHERE menu_name = '优惠券管理'), 1, '#', '', 1, 0, 'F', 0, 0, 'system:coupon:add', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('修改', (SELECT menu_id FROM sys_menu WHERE menu_name = '优惠券管理'), 2, '#', '', 1, 0, 'F', 0, 0, 'system:coupon:edit', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('删除', (SELECT menu_id FROM sys_menu WHERE menu_name = '优惠券管理'), 3, '#', '', 1, 0, 'F', 0, 0, 'system:coupon:remove', '', 'admin', NOW(), 'admin', NOW(), '');

-- 收银管理按钮权限
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('收银', (SELECT menu_id FROM sys_menu WHERE menu_name = '收银管理'), 1, '#', '', 1, 0, 'F', 0, 0, 'system:cashier:checkout', '', 'admin', NOW(), 'admin', NOW(), '');

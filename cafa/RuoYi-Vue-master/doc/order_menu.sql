-- 订单管理菜单配置SQL

-- 1. 插入订单管理目录菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('订单管理', 100, 11, 'order', 'system/order/index', 1, 0, 'C', '0', '0', 'system:order:list', 'order', 'admin', NOW(), 'admin', NOW());

-- 获取刚插入的订单管理目录ID
SET @order_menu_id = LAST_INSERT_ID();

-- 2. 插入订单列表菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('订单列表', @order_menu_id, 1, 'order', 'system/order/index', 1, 0, 'M', '0', '0', 'system:order:list', '', 'admin', NOW(), 'admin', NOW());

-- 3. 插入订单管理按钮权限
-- 新增按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('新增', @order_menu_id, 2, '#', '', 1, 0, 'F', '0', '0', 'system:order:add', '', 'admin', NOW(), 'admin', NOW());

-- 修改按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('修改', @order_menu_id, 3, '#', '', 1, 0, 'F', '0', '0', 'system:order:edit', '', 'admin', NOW(), 'admin', NOW());

-- 删除按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('删除', @order_menu_id, 4, '#', '', 1, 0, 'F', '0', '0', 'system:order:remove', '', 'admin', NOW(), 'admin', NOW());

-- 导出按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('导出', @order_menu_id, 5, '#', '', 1, 0, 'F', '0', '0', 'system:order:export', '', 'admin', NOW(), 'admin', NOW());

-- 4. 插入商品管理目录菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('商品管理', 100, 12, 'product', 'system/product/index', 1, 0, 'C', '0', '0', 'system:product:list', 'goods', 'admin', NOW(), 'admin', NOW());

-- 获取刚插入的商品管理目录ID
SET @product_menu_id = LAST_INSERT_ID();

-- 5. 插入商品列表菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('商品列表', @product_menu_id, 1, 'product', 'system/product/index', 1, 0, 'M', '0', '0', 'system:product:list', '', 'admin', NOW(), 'admin', NOW());

-- 6. 插入商品管理按钮权限
-- 新增按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('新增', @product_menu_id, 2, '#', '', 1, 0, 'F', '0', '0', 'system:product:add', '', 'admin', NOW(), 'admin', NOW());

-- 修改按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('修改', @product_menu_id, 3, '#', '', 1, 0, 'F', '0', '0', 'system:product:edit', '', 'admin', NOW(), 'admin', NOW());

-- 删除按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('删除', @product_menu_id, 4, '#', '', 1, 0, 'F', '0', '0', 'system:product:remove', '', 'admin', NOW(), 'admin', NOW());

-- 导出按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('导出', @product_menu_id, 5, '#', '', 1, 0, 'F', '0', '0', 'system:product:export', '', 'admin', NOW(), 'admin', NOW());

-- 7. 插入套餐管理目录菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('套餐管理', 100, 13, 'package', 'system/package/index', 1, 0, 'C', '0', '0', 'system:package:list', 'package', 'admin', NOW(), 'admin', NOW());

-- 获取刚插入的套餐管理目录ID
SET @package_menu_id = LAST_INSERT_ID();

-- 8. 插入套餐列表菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('套餐列表', @package_menu_id, 1, 'package', 'system/package/index', 1, 0, 'M', '0', '0', 'system:package:list', '', 'admin', NOW(), 'admin', NOW());

-- 9. 插入套餐管理按钮权限
-- 新增按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('新增', @package_menu_id, 2, '#', '', 1, 0, 'F', '0', '0', 'system:package:add', '', 'admin', NOW(), 'admin', NOW());

-- 修改按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('修改', @package_menu_id, 3, '#', '', 1, 0, 'F', '0', '0', 'system:package:edit', '', 'admin', NOW(), 'admin', NOW());

-- 删除按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('删除', @package_menu_id, 4, '#', '', 1, 0, 'F', '0', '0', 'system:package:remove', '', 'admin', NOW(), 'admin', NOW());

-- 导出按钮
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('导出', @package_menu_id, 5, '#', '', 1, 0, 'F', '0', '0', 'system:package:export', '', 'admin', NOW(), 'admin', NOW());

-- 10. 为超级管理员角色分配权限
-- 假设超级管理员角色ID为1
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
SELECT 1, menu_id FROM `sys_menu` WHERE perms LIKE 'system:order:%' OR perms LIKE 'system:product:%' OR perms LIKE 'system:package:%';
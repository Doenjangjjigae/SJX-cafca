-- 原料信息表
CREATE TABLE IF NOT EXISTS `sys_material` (
  `material_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '原料ID',
  `material_name` varchar(100) NOT NULL COMMENT '原料名称',
  `specification` varchar(100) DEFAULT NULL COMMENT '原料规格',
  `unit` varchar(20) NOT NULL COMMENT '原料单位',
  `warning_threshold` decimal(10,2) DEFAULT '0.00' COMMENT '库存预警阈值',
  `current_stock` decimal(10,2) DEFAULT '0.00' COMMENT '当前库存',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`material_id`),
  UNIQUE KEY `uk_material_name` (`material_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='原料信息表';

-- 库存操作记录表
CREATE TABLE IF NOT EXISTS `sys_inventory_record` (
  `record_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `material_id` bigint(20) NOT NULL COMMENT '原料ID',
  `operation_type` char(1) NOT NULL COMMENT '操作类型（1进货 2盘点）',
  `quantity` decimal(10,2) NOT NULL COMMENT '操作数量',
  `before_stock` decimal(10,2) DEFAULT '0.00' COMMENT '操作前库存',
  `after_stock` decimal(10,2) DEFAULT '0.00' COMMENT '操作后库存',
  `operator` varchar(64) DEFAULT '' COMMENT '操作人',
  `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`record_id`),
  KEY `idx_material_id` (`material_id`),
  CONSTRAINT `fk_inventory_material` FOREIGN KEY (`material_id`) REFERENCES `sys_material` (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='库存操作记录表';

-- 库存管理菜单
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('库存管理', 100, 12, 'inventory', 'system/inventory/index', 1, 0, 'C', 0, 0, 'system:inventory:list', 'shopping', 'admin', NOW(), 'admin', NOW(), '库存管理');

-- 原料信息维护菜单
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('原料信息', (SELECT menu_id FROM sys_menu WHERE menu_name = '库存管理'), 1, 'material', 'system/inventory/material/index', 1, 0, 'C', 0, 0, 'system:inventory:material:list', 'list', 'admin', NOW(), 'admin', NOW(), '原料信息维护');

-- 库存操作菜单
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('库存操作', (SELECT menu_id FROM sys_menu WHERE menu_name = '库存管理'), 2, 'operation', 'system/inventory/operation/index', 1, 0, 'C', 0, 0, 'system:inventory:operation:list', 'edit', 'admin', NOW(), 'admin', NOW(), '库存操作记录');

-- 库存预警菜单
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('库存预警', (SELECT menu_id FROM sys_menu WHERE menu_name = '库存管理'), 3, 'warning', 'system/inventory/warning/index', 1, 0, 'C', 0, 0, 'system:inventory:warning:list', 'warning', 'admin', NOW(), 'admin', NOW(), '库存预警');

-- 原料信息维护按钮权限
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('新增', (SELECT menu_id FROM sys_menu WHERE menu_name = '原料信息'), 1, '#', '', 1, 0, 'F', 0, 0, 'system:inventory:material:add', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('修改', (SELECT menu_id FROM sys_menu WHERE menu_name = '原料信息'), 2, '#', '', 1, 0, 'F', 0, 0, 'system:inventory:material:edit', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('删除', (SELECT menu_id FROM sys_menu WHERE menu_name = '原料信息'), 3, '#', '', 1, 0, 'F', 0, 0, 'system:inventory:material:remove', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '原料信息'), 4, '#', '', 1, 0, 'F', 0, 0, 'system:inventory:material:query', '', 'admin', NOW(), 'admin', NOW(), '');

-- 库存操作按钮权限
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('进货录入', (SELECT menu_id FROM sys_menu WHERE menu_name = '库存操作'), 1, '#', '', 1, 0, 'F', 0, 0, 'system:inventory:operation:add', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('盘点录入', (SELECT menu_id FROM sys_menu WHERE menu_name = '库存操作'), 2, '#', '', 1, 0, 'F', 0, 0, 'system:inventory:operation:stocktake', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '库存操作'), 3, '#', '', 1, 0, 'F', 0, 0, 'system:inventory:operation:query', '', 'admin', NOW(), 'admin', NOW(), '');

-- 库存预警按钮权限
INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('设置阈值', (SELECT menu_id FROM sys_menu WHERE menu_name = '库存预警'), 1, '#', '', 1, 0, 'F', 0, 0, 'system:inventory:warning:set', '', 'admin', NOW(), 'admin', NOW(), '');

INSERT IGNORE INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '库存预警'), 2, '#', '', 1, 0, 'F', 0, 0, 'system:inventory:warning:query', '', 'admin', NOW(), 'admin', NOW(), '');
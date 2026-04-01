-- 商品原料关联表
DROP TABLE IF EXISTS `sys_product_material`;
CREATE TABLE `sys_product_material` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `material_id` bigint(20) NOT NULL COMMENT '原料ID',
  `quantity` decimal(10,2) NOT NULL COMMENT '原料用量',
  `is_adjustable` char(1) DEFAULT '0' COMMENT '是否可调整（0-否，1-是）',
  `option_name` varchar(50) DEFAULT NULL COMMENT '选项名称（如：甜度）',
  `default_option` varchar(50) DEFAULT NULL COMMENT '默认选项',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_material_id` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品原料关联表';

-- 商品原料选项表
DROP TABLE IF EXISTS `sys_product_material_option`;
CREATE TABLE `sys_product_material_option` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_material_id` bigint(20) NOT NULL COMMENT '商品原料关联ID',
  `option_label` varchar(50) NOT NULL COMMENT '选项标签（如：不加糖、少甜、正常甜）',
  `quantity` decimal(10,2) NOT NULL COMMENT '该选项对应的用量',
  PRIMARY KEY (`id`),
  KEY `idx_product_material_id` (`product_material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品原料选项表';

-- 为订单商品表添加原料选项字段
ALTER TABLE `sys_order_item` ADD COLUMN `material_options` text COMMENT '原料选项JSON' AFTER `subtotal`;

-- 为库存记录表添加消费扣减类型
-- 操作类型：1=进货，2=盘点，3=消费扣减

-- 示例数据：为拿铁咖啡配置原料
-- 假设已有原料：杯子(id=1)、咖啡豆(id=2)、牛奶(id=3)、糖(id=4)
-- 假设拿铁咖啡的product_id=1

-- 添加杯子原料（不可调整）
-- INSERT INTO `sys_product_material` (`product_id`, `material_id`, `quantity`, `is_adjustable`, `create_time`) 
-- VALUES (1, 1, 1.00, '0', NOW());

-- 添加咖啡豆原料（不可调整）
-- INSERT INTO `sys_product_material` (`product_id`, `material_id`, `quantity`, `is_adjustable`, `create_time`) 
-- VALUES (1, 2, 10.00, '0', NOW());

-- 添加牛奶原料（不可调整）
-- INSERT INTO `sys_product_material` (`product_id`, `material_id`, `quantity`, `is_adjustable`, `create_time`) 
-- VALUES (1, 3, 300.00, '0', NOW());

-- 添加糖原料（可调整，支持不同甜度）
-- INSERT INTO `sys_product_material` (`product_id`, `material_id`, `quantity`, `is_adjustable`, `option_name`, `default_option`, `create_time`) 
-- VALUES (1, 4, 5.00, '1', '甜度', '正常甜', NOW());
-- SET @pm_id = LAST_INSERT_ID();

-- 添加糖的选项
-- INSERT INTO `sys_product_material_option` (`product_material_id`, `option_label`, `quantity`) VALUES (@pm_id, '不加糖', 0.00);
-- INSERT INTO `sys_product_material_option` (`product_material_id`, `option_label`, `quantity`) VALUES (@pm_id, '少甜', 3.00);
-- INSERT INTO `sys_product_material_option` (`product_material_id`, `option_label`, `quantity`) VALUES (@pm_id, '正常甜', 5.00);
-- INSERT INTO `sys_product_material_option` (`product_material_id`, `option_label`, `quantity`) VALUES (@pm_id, '多甜', 8.00);

-- 商品配置组表（如：温度、冰量、甜度、加料等）
DROP TABLE IF EXISTS `sys_product_config_group`;
CREATE TABLE `sys_product_config_group` (
  `config_group_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置组ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `group_name` varchar(50) NOT NULL COMMENT '配置组名称（如：温度、冰量、甜度、加料）',
  `group_type` varchar(20) NOT NULL COMMENT '配置组类型（temperature-温度，ice-冰量，sweetness-甜度，topping-加料）',
  `is_required` char(1) DEFAULT '1' COMMENT '是否必选（0-否，1-是）',
  `is_multiple` char(1) DEFAULT '0' COMMENT '是否可多选（0-否，1-是，用于加料）',
  `sort_order` int(11) DEFAULT 0 COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`config_group_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品配置组表';

-- 商品配置选项表（如：热、温、冷、多冰、少冰等）
DROP TABLE IF EXISTS `sys_product_config_option`;
CREATE TABLE `sys_product_config_option` (
  `config_option_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置选项ID',
  `config_group_id` bigint(20) NOT NULL COMMENT '配置组ID',
  `option_name` varchar(50) NOT NULL COMMENT '选项名称（如：热、温、冷、多冰、少冰、正常甜等）',
  `option_value` varchar(50) DEFAULT NULL COMMENT '选项值（用于前端展示和计算）',
  `extra_price` decimal(10,2) DEFAULT 0.00 COMMENT '额外价格（加料时使用）',
  `sort_order` int(11) DEFAULT 0 COMMENT '排序',
  `is_default` char(1) DEFAULT '0' COMMENT '是否默认选项',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`config_option_id`),
  KEY `idx_config_group_id` (`config_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品配置选项表';

-- 商品配置选项关联原料表（每个选项对应的原料用量）
DROP TABLE IF EXISTS `sys_product_config_material`;
CREATE TABLE `sys_product_config_material` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config_option_id` bigint(20) NOT NULL COMMENT '配置选项ID',
  `material_id` bigint(20) NOT NULL COMMENT '原料ID',
  `quantity` decimal(10,2) NOT NULL COMMENT '原料用量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_config_option_id` (`config_option_id`),
  KEY `idx_material_id` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品配置选项关联原料表';

-- 商品默认原料表（商品的基础原料配置）
DROP TABLE IF EXISTS `sys_product_default_material`;
CREATE TABLE `sys_product_default_material` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `material_id` bigint(20) NOT NULL COMMENT '原料ID',
  `quantity` decimal(10,2) NOT NULL COMMENT '原料用量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_material` (`product_id`, `material_id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_material_id` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品默认原料表';

-- 套餐商品项表
DROP TABLE IF EXISTS `sys_package_item`;
CREATE TABLE `sys_package_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `package_id` bigint(20) NOT NULL COMMENT '套餐ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL DEFAULT 1 COMMENT '数量',
  PRIMARY KEY (`id`),
  KEY `idx_package_id` (`package_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='套餐商品项表';

-- 修改订单商品表，添加配置选项JSON字段
ALTER TABLE `sys_order_item` ADD COLUMN `config_options` text COMMENT '配置选项JSON（温度、冰量、甜度、加料等）' AFTER `material_options`;

-- 示例数据：为奶茶商品配置
-- 假设奶茶商品 product_id = 1
-- 假设原料：茶叶(id=1)、牛奶(id=2)、糖浆(id=3)、珍珠(id=4)、椰果(id=5)、冰块(id=6)

-- 配置温度选项
-- INSERT INTO `sys_product_config_group` (`product_id`, `group_name`, `group_type`, `is_required`, `sort_order`) 
-- VALUES (1, '温度', 'temperature', '1', 1);
-- SET @temp_group_id = LAST_INSERT_ID();

-- INSERT INTO `sys_product_config_option` (`config_group_id`, `option_name`, `option_value`, `is_default`, `sort_order`) VALUES 
-- (@temp_group_id, '热', 'hot', '0', 1),
-- (@temp_group_id, '温', 'warm', '1', 2),
-- (@temp_group_id, '冷', 'cold', '0', 3);

-- 配置冰量选项
-- INSERT INTO `sys_product_config_group` (`product_id`, `group_name`, `group_type`, `is_required`, `sort_order`) 
-- VALUES (1, '冰量', 'ice', '1', 2);
-- SET @ice_group_id = LAST_INSERT_ID();

-- INSERT INTO `sys_product_config_option` (`config_group_id`, `option_name`, `option_value`, `is_default`, `sort_order`) VALUES 
-- (@ice_group_id, '多冰', 'extra_ice', '0', 1),
-- (@ice_group_id, '正常冰', 'normal_ice', '1', 2),
-- (@ice_group_id, '少冰', 'less_ice', '0', 3),
-- (@ice_group_id, '去冰', 'no_ice', '0', 4);

-- 配置甜度选项
-- INSERT INTO `sys_product_config_group` (`product_id`, `group_name`, `group_type`, `is_required`, `sort_order`) 
-- VALUES (1, '甜度', 'sweetness', '1', 3);
-- SET @sweet_group_id = LAST_INSERT_ID();

-- INSERT INTO `sys_product_config_option` (`config_group_id`, `option_name`, `option_value`, `is_default`, `sort_order`) VALUES 
-- (@sweet_group_id, '全糖', 'full', '0', 1),
-- (@sweet_group_id, '七分糖', '70%', '0', 2),
-- (@sweet_group_id, '半糖', '50%', '1', 3),
-- (@sweet_group_id, '三分糖', '30%', '0', 4),
-- (@sweet_group_id, '无糖', '0%', '0', 5);

-- 配置加料选项（可多选）
-- INSERT INTO `sys_product_config_group` (`product_id`, `group_name`, `group_type`, `is_required`, `is_multiple`, `sort_order`) 
-- VALUES (1, '加料', 'topping', '0', '1', 4);
-- SET @topping_group_id = LAST_INSERT_ID();

-- INSERT INTO `sys_product_config_option` (`config_group_id`, `option_name`, `option_value`, `extra_price`, `sort_order`) VALUES 
-- (@topping_group_id, '珍珠', 'pearl', 2.00, 1),
-- (@topping_group_id, '椰果', 'coconut', 2.00, 2),
-- (@topping_group_id, '布丁', 'pudding', 3.00, 3);

-- 配置默认原料
-- INSERT INTO `sys_product_default_material` (`product_id`, `material_id`, `quantity`) VALUES 
-- (1, 1, 5.00),  -- 茶叶 5g
-- (1, 2, 200.00), -- 牛奶 200ml
-- (1, 3, 10.00);  -- 糖浆 10ml

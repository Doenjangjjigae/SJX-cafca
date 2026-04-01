-- 订单表
CREATE TABLE `sys_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_number` varchar(32) NOT NULL COMMENT '订单编号',
  `table_id` bigint(20) NOT NULL COMMENT '桌台ID',
  `table_name` varchar(50) NOT NULL COMMENT '桌台名称',
  `customer_name` varchar(50) DEFAULT NULL COMMENT '顾客姓名',
  `customer_phone` varchar(20) DEFAULT NULL COMMENT '顾客电话',
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '总金额',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0-待支付，1-已支付，2-已完成，3-已取消）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `uk_order_number` (`order_number`),
  KEY `idx_table_id` (`table_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 商品表
CREATE TABLE `sys_product` (
  `product_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `product_name` varchar(100) NOT NULL COMMENT '商品名称',
  `product_code` varchar(32) NOT NULL COMMENT '商品编码',
  `price` decimal(10,2) NOT NULL COMMENT '单价',
  `category` varchar(50) DEFAULT NULL COMMENT '分类',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0-正常，1-下架）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `uk_product_code` (`product_code`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 套餐表
CREATE TABLE `sys_package` (
  `package_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '套餐ID',
  `package_name` varchar(100) NOT NULL COMMENT '套餐名称',
  `package_code` varchar(32) NOT NULL COMMENT '套餐编码',
  `price` decimal(10,2) NOT NULL COMMENT '套餐价格',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0-正常，1-下架）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`package_id`),
  UNIQUE KEY `uk_package_code` (`package_code`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='套餐表';

-- 套餐商品关联表
CREATE TABLE `sys_package_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `package_id` bigint(20) NOT NULL COMMENT '套餐ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_package_id` (`package_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='套餐商品关联表';

-- 订单商品关联表
CREATE TABLE `sys_order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `product_name` varchar(100) NOT NULL COMMENT '商品名称',
  `price` decimal(10,2) NOT NULL COMMENT '单价',
  `quantity` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `subtotal` decimal(10,2) NOT NULL COMMENT '小计',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='订单商品关联表';

-- 订单套餐关联表
CREATE TABLE `sys_order_package_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `package_id` bigint(20) NOT NULL COMMENT '套餐ID',
  `package_name` varchar(100) NOT NULL COMMENT '套餐名称',
  `price` decimal(10,2) NOT NULL COMMENT '套餐价格',
  `quantity` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `subtotal` decimal(10,2) NOT NULL COMMENT '小计',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_package_id` (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='订单套餐关联表';

-- 插入示例数据
INSERT INTO `sys_product` (`product_name`, `product_code`, `price`, `category`, `status`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
('宫保鸡丁', 'P001', 38.00, '热菜', '0', '经典川菜', 'admin', NOW(), 'admin', NOW()),
('麻婆豆腐', 'P002', 28.00, '热菜', '0', '麻辣鲜香', 'admin', NOW(), 'admin', NOW()),
('鱼香肉丝', 'P003', 32.00, '热菜', '0', '酸甜可口', 'admin', NOW(), 'admin', NOW()),
('米饭', 'P004', 2.00, '主食', '0', '白米饭', 'admin', NOW(), 'admin', NOW()),
('可乐', 'P005', 8.00, '饮料', '0', '冰镇可乐', 'admin', NOW(), 'admin', NOW());

-- 插入套餐示例数据
INSERT INTO `sys_package` (`package_name`, `package_code`, `price`, `status`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
('单人套餐', 'PK001', 58.00, '0', '包含一个热菜、一份米饭、一杯饮料', 'admin', NOW(), 'admin', NOW()),
('双人套餐', 'PK002', 108.00, '0', '包含两个热菜、两份米饭、两杯饮料', 'admin', NOW(), 'admin', NOW());

-- 插入套餐商品关联示例数据
INSERT INTO `sys_package_product` (`package_id`, `product_id`, `quantity`, `create_time`) VALUES
(1, 1, 1, NOW()), -- 单人套餐包含宫保鸡丁
(1, 4, 1, NOW()), -- 单人套餐包含米饭
(1, 5, 1, NOW()), -- 单人套餐包含可乐
(2, 1, 1, NOW()), -- 双人套餐包含宫保鸡丁
(2, 2, 1, NOW()), -- 双人套餐包含麻婆豆腐
(2, 4, 2, NOW()), -- 双人套餐包含两份米饭
(2, 5, 2, NOW()); -- 双人套餐包含两杯可乐
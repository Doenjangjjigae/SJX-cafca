-- 会员表
CREATE TABLE `sys_member` (
  `member_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `member_name` varchar(50) NOT NULL COMMENT '会员姓名',
  `member_phone` varchar(20) NOT NULL COMMENT '会员电话',
  `member_level` varchar(20) NOT NULL DEFAULT '普通会员' COMMENT '会员等级',
  `discount_rate` decimal(4,2) NOT NULL DEFAULT '1.00' COMMENT '折扣率',
  `points` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0-正常，1-禁用）',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `uk_member_phone` (`member_phone`),
  KEY `idx_member_level` (`member_level`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

-- 优惠券表
CREATE TABLE `sys_coupon` (
  `coupon_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '优惠券ID',
  `coupon_name` varchar(100) NOT NULL COMMENT '优惠券名称',
  `coupon_type` char(1) NOT NULL COMMENT '优惠券类型（1-满减，2-折扣）',
  `discount_value` decimal(10,2) NOT NULL COMMENT '优惠金额/折扣率',
  `min_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最低消费金额',
  `start_time` datetime NOT NULL COMMENT '有效期开始时间',
  `end_time` datetime NOT NULL COMMENT '有效期结束时间',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0-正常，1-过期，2-停用）',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`coupon_id`),
  KEY `idx_coupon_type` (`coupon_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- 会员优惠券关联表
CREATE TABLE `sys_member_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `member_id` bigint(20) NOT NULL COMMENT '会员ID',
  `coupon_id` bigint(20) NOT NULL COMMENT '优惠券ID',
  `receive_time` datetime NOT NULL COMMENT '领取时间',
  `use_status` char(1) NOT NULL DEFAULT '0' COMMENT '使用状态（0-未使用，1-已使用，2-已过期）',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`),
  KEY `idx_coupon_id` (`coupon_id`),
  KEY `idx_use_status` (`use_status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='会员优惠券关联表';

-- 订单优惠券关联表
CREATE TABLE `sys_order_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `coupon_id` bigint(20) NOT NULL COMMENT '优惠券ID',
  `discount_amount` decimal(10,2) NOT NULL COMMENT '优惠金额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_coupon_id` (`coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='订单优惠券关联表';

-- 修改订单表，添加会员ID和实际支付金额字段
ALTER TABLE `sys_order` ADD COLUMN `member_id` bigint(20) DEFAULT NULL COMMENT '会员ID';
ALTER TABLE `sys_order` ADD COLUMN `actual_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '实际支付金额';
ALTER TABLE `sys_order` ADD COLUMN `discount_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '优惠金额';
ALTER TABLE `sys_order` ADD KEY `idx_member_id` (`member_id`);

-- 插入会员示例数据
INSERT INTO `sys_member` (`member_name`, `member_phone`, `member_level`, `discount_rate`, `points`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
('张三', '13800138001', '普通会员', 1.00, 0, '0', 'admin', NOW(), 'admin', NOW()),
('李四', '13800138002', '银卡会员', 0.95, 100, '0', 'admin', NOW(), 'admin', NOW()),
('王五', '13800138003', '金卡会员', 0.90, 500, '0', 'admin', NOW(), 'admin', NOW());

-- 插入优惠券示例数据
INSERT INTO `sys_coupon` (`coupon_name`, `coupon_type`, `discount_value`, `min_amount`, `start_time`, `end_time`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
('满100减20', '1', 20.00, 100.00, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), '0', 'admin', NOW(), 'admin', NOW()),
('9折优惠', '2', 0.90, 50.00, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), '0', 'admin', NOW(), 'admin', NOW()),
('满200减50', '1', 50.00, 200.00, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), '0', 'admin', NOW(), 'admin', NOW());

-- 插入会员优惠券关联示例数据
INSERT INTO `sys_member_coupon` (`member_id`, `coupon_id`, `receive_time`, `use_status`) VALUES
(1, 1, NOW(), '0'),
(1, 2, NOW(), '0'),
(2, 1, NOW(), '0'),
(2, 3, NOW(), '0'),
(3, 2, NOW(), '0'),
(3, 3, NOW(), '0');

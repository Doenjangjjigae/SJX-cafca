-- 创建会员表（如果不存在）
CREATE TABLE IF NOT EXISTS `sys_member` (
  `member_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `member_name` varchar(50) NOT NULL COMMENT '会员姓名',
  `member_code` varchar(32) NOT NULL COMMENT '会员编号',
  `phone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `birthdate` date DEFAULT NULL COMMENT '出生日期',
  `gender` char(1) DEFAULT NULL COMMENT '性别（0-男，1-女）',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `total_points` int(11) DEFAULT '0' COMMENT '总积分',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0-正常，1-禁用）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`member_id`),
  KEY `idx_member_code` (`member_code`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

-- 为会员表添加会员编号列（如果不存在）
ALTER TABLE `sys_member` ADD COLUMN IF NOT EXISTS `member_code` varchar(32) NOT NULL COMMENT '会员编号';

-- 创建会员积分表（如果不存在）
CREATE TABLE IF NOT EXISTS `sys_member_points` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `member_id` bigint(20) NOT NULL COMMENT '会员ID',
  `member_code` varchar(32) NOT NULL COMMENT '会员编号',
  `points` int(11) NOT NULL COMMENT '积分数量（正数为增加，负数为减少）',
  `type` char(1) NOT NULL COMMENT '类型（0-消费获得，1-手动录入，2-积分兑换）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`),
  KEY `idx_member_code` (`member_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='会员积分表';

-- 创建会员消费记录表（如果不存在）
CREATE TABLE IF NOT EXISTS `sys_member_consume` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `member_id` bigint(20) NOT NULL COMMENT '会员ID',
  `member_code` varchar(32) NOT NULL COMMENT '会员编号',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `order_number` varchar(32) DEFAULT NULL COMMENT '订单编号',
  `amount` decimal(10,2) NOT NULL COMMENT '消费金额',
  `points` int(11) NOT NULL DEFAULT '0' COMMENT '获得积分',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0-正常，1-取消）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`),
  KEY `idx_member_code` (`member_code`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='会员消费记录表';

-- 插入会员示例数据
INSERT IGNORE INTO `sys_member` (`member_name`, `member_code`, `phone`, `email`, `birthdate`, `gender`, `address`, `total_points`, `status`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
('张三', 'M001', '13800138001', 'zhangsan@example.com', '1990-01-01', '0', '北京市朝阳区', 150, '0', '普通会员', 'admin', NOW(), 'admin', NOW()),
('李四', 'M002', '13900139001', 'lisi@example.com', '1992-02-02', '1', '上海市浦东新区', 200, '0', '普通会员', 'admin', NOW(), 'admin', NOW()),
('王五', 'M003', '13700137001', 'wangwu@example.com', '1993-03-03', '0', '广州市天河区', 50, '0', '普通会员', 'admin', NOW(), 'admin', NOW());

-- 插入会员积分示例数据
INSERT IGNORE INTO `sys_member_points` (`member_id`, `member_code`, `points`, `type`, `remark`, `create_by`, `create_time`) VALUES
(1, 'M001', 100, '0', '消费获得', 'admin', NOW()),
(1, 'M001', 50, '1', '手动录入', 'admin', NOW()),
(2, 'M002', 200, '0', '消费获得', 'admin', NOW()),
(3, 'M003', 50, '0', '消费获得', 'admin', NOW());

-- 插入会员消费记录示例数据
INSERT IGNORE INTO `sys_member_consume` (`member_id`, `member_code`, `order_id`, `order_number`, `amount`, `points`, `status`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
(1, 'M001', 1, 'ORD20240101001', 1000.00, 100, '0', '购买商品', 'admin', NOW(), 'admin', NOW()),
(2, 'M002', 2, 'ORD20240101002', 2000.00, 200, '0', '购买商品', 'admin', NOW(), 'admin', NOW()),
(3, 'M003', 3, 'ORD20240101003', 500.00, 50, '0', '购买商品', 'admin', NOW(), 'admin', NOW());
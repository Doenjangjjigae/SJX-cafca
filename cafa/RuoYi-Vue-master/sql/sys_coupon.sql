-- 优惠券表
CREATE TABLE `sys_coupon` (
  `coupon_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '优惠券ID',
  `coupon_name` varchar(100) NOT NULL COMMENT '优惠券名称',
  `coupon_type` varchar(1) NOT NULL COMMENT '优惠券类型（1-满减，2-折扣）',
  `discount_value` decimal(10,2) NOT NULL COMMENT '优惠金额',
  `min_amount` decimal(10,2) NOT NULL COMMENT '最低消费',
  `status` varchar(1) DEFAULT '0' COMMENT '状态（0-正常，1-禁用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- 插入示例数据
INSERT INTO `sys_coupon` (`coupon_name`, `coupon_type`, `discount_value`, `min_amount`, `status`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
('满100减20', '1', 20.00, 100.00, '0', '满100元减20元', 'admin', NOW(), 'admin', NOW()),
('8折优惠', '2', 0.80, 50.00, '0', '全场8折', 'admin', NOW(), 'admin', NOW());
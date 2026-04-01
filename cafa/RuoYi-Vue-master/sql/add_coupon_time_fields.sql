-- 添加优惠券时间字段
ALTER TABLE `sys_coupon` ADD COLUMN `start_time` datetime DEFAULT NULL COMMENT '开始时间';
ALTER TABLE `sys_coupon` ADD COLUMN `end_time` datetime DEFAULT NULL COMMENT '结束时间';

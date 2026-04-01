-- 向 sys_coupon 表添加 remark 字段
ALTER TABLE `sys_coupon` ADD COLUMN `remark` varchar(500) DEFAULT NULL COMMENT '备注' AFTER `status`;
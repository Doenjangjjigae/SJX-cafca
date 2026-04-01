-- 积分配置表
CREATE TABLE `sys_point_config` (
  `config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `point_rate` int(11) DEFAULT NULL COMMENT '积分兑换比例（1元=多少积分）',
  `status` varchar(1) DEFAULT '0' COMMENT '状态（0-正常，1-禁用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='积分配置表';

-- 插入默认积分配置（1元=1积分）
INSERT INTO `sys_point_config` (`point_rate`, `status`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
(1, '0', '默认积分配置', 'admin', NOW(), 'admin', NOW());
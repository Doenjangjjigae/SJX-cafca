CREATE TABLE `sys_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '桌台ID',
  `table_name` varchar(50) NOT NULL COMMENT '桌台名称',
  `table_number` varchar(20) NOT NULL COMMENT '桌台编号',
  `capacity` int(11) NOT NULL COMMENT '容纳人数',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0-空闲，1-占用）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`table_id`),
  UNIQUE KEY `uk_table_number` (`table_number`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='桌台表';

-- 插入示例数据
INSERT INTO `sys_table` (`table_name`, `table_number`, `capacity`, `status`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
('1号桌', 'T001', 4, '0', '靠近窗户', 'admin', NOW(), 'admin', NOW()),
('2号桌', 'T002', 6, '0', 'VIP包间', 'admin', NOW(), 'admin', NOW()),
('3号桌', 'T003', 2, '0', '吧台附近', 'admin', NOW(), 'admin', NOW()),
('4号桌', 'T004', 8, '0', '多人桌', 'admin', NOW(), 'admin', NOW()),
('5号桌', 'T005', 4, '0', '角落位置', 'admin', NOW(), 'admin', NOW());
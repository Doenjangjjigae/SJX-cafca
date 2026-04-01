-- 会员管理菜单配置
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
  ('会员管理', '100', '10', 'member', 'system/member/index', '1', '0', 'C', '0', '0', 'system:member:list', 'user', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '会员管理菜单'),
  ('会员列表', '10000', '1', 'list', '#', '0', '0', 'F', '0', '0', 'system:member:list', '', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '会员列表'),
  ('会员添加', '10000', '2', 'add', '#', '0', '0', 'F', '0', '0', 'system:member:add', '', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '会员添加'),
  ('会员修改', '10000', '3', 'edit', '#', '0', '0', 'F', '0', '0', 'system:member:edit', '', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '会员修改'),
  ('会员删除', '10000', '4', 'remove', '#', '0', '0', 'F', '0', '0', 'system:member:remove', '', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '会员删除'),
  ('会员查询', '10000', '5', 'query', '#', '0', '0', 'F', '0', '0', 'system:member:query', '', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '会员查询'),
  ('积分管理', '10000', '6', 'points', '#', '0', '0', 'F', '0', '0', 'system:member:points:list', '', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '积分管理'),
  ('积分添加', '10000', '7', 'pointsAdd', '#', '0', '0', 'F', '0', '0', 'system:member:points:add', '', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '积分添加'),
  ('积分删除', '10000', '8', 'pointsRemove', '#', '0', '0', 'F', '0', '0', 'system:member:points:remove', '', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '积分删除'),
  ('积分查询', '10000', '9', 'pointsQuery', '#', '0', '0', 'F', '0', '0', 'system:member:points:query', '', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '积分查询'),
  ('消费记录', '10000', '10', 'consume', '#', '0', '0', 'F', '0', '0', 'system:member:consume:list', '', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '消费记录'),
  ('消费记录查询', '10000', '11', 'consumeQuery', '#', '0', '0', 'F', '0', '0', 'system:member:consume:query', '', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '消费记录查询');

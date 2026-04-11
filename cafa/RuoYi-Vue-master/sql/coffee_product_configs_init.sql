-- 咖啡产品配置初始化

-- 1. 添加必要的原料（如果不存在）
INSERT IGNORE INTO sys_material (material_name, specification, unit, warning_threshold, current_stock, status, create_by, create_time, update_by, update_time) VALUES
('杯子', '标准咖啡杯', '只', 100, 1000, '0', 'admin', NOW(), 'admin', NOW()),
('咖啡豆', '阿拉比卡咖啡豆', 'g', 500, 5000, '0', 'admin', NOW(), 'admin', NOW());

-- 2. 获取原料ID
-- 注意：实际执行时需要根据实际的自增ID调整下面的material_id值
-- 假设杯子的ID为1，咖啡豆的ID为2

-- 3. 为所有咖啡产品添加默认原料配置
INSERT INTO sys_product_default_material (product_id, material_id, material_name, unit, quantity, create_by, create_time, update_by, update_time) 
SELECT 
    product_id, 
    1 as material_id, 
    '杯子' as material_name, 
    '只' as unit, 
    1 as quantity, 
    'admin' as create_by, 
    NOW() as create_time, 
    'admin' as update_by, 
    NOW() as update_time
FROM sys_product WHERE product_name LIKE '%咖啡%';

INSERT INTO sys_product_default_material (product_id, material_id, material_name, unit, quantity, create_by, create_time, update_by, update_time) 
SELECT 
    product_id, 
    2 as material_id, 
    '咖啡豆' as material_name, 
    'g' as unit, 
    2 as quantity, 
    'admin' as create_by, 
    NOW() as create_time, 
    'admin' as update_by, 
    NOW() as update_time
FROM sys_product WHERE product_name LIKE '%咖啡%';

-- 4. 为所有咖啡产品添加温度配置组
INSERT INTO sys_product_config_group (product_id, group_name, group_type, is_required, is_multiple, sort_order, create_by, create_time, update_by, update_time) 
SELECT 
    product_id, 
    '温度' as group_name, 
    'temperature' as group_type, 
    '1' as is_required, 
    '0' as is_multiple, 
    1 as sort_order, 
    'admin' as create_by, 
    NOW() as create_time, 
    'admin' as update_by, 
    NOW() as update_time
FROM sys_product WHERE product_name LIKE '%咖啡%';

-- 5. 为所有咖啡产品添加糖分配置组
INSERT INTO sys_product_config_group (product_id, group_name, group_type, is_required, is_multiple, sort_order, create_by, create_time, update_by, update_time) 
SELECT 
    product_id, 
    '糖分' as group_name, 
    'sweetness' as group_type, 
    '1' as is_required, 
    '0' as is_multiple, 
    2 as sort_order, 
    'admin' as create_by, 
    NOW() as create_time, 
    'admin' as update_by, 
    NOW() as update_time
FROM sys_product WHERE product_name LIKE '%咖啡%';

-- 6. 为每个温度配置组添加选项（冷、热）
-- 注意：实际执行时需要根据config_group_id的实际值调整
-- 这里假设温度配置组的ID从1开始，按product_id顺序排列

-- 7. 为每个糖分配置组添加选项（不额外加糖，3分糖，5分糖，7分糖，10分糖）
-- 注意：实际执行时需要根据config_group_id的实际值调整
-- 这里假设糖分配置组的ID从31开始（30个产品 × 2个配置组 = 60个配置组）

-- 8. 为所有非ID为6的产品添加冰量配置组
INSERT INTO sys_product_config_group (product_id, group_name, group_type, is_required, is_multiple, sort_order, create_by, create_time, update_by, update_time) 
SELECT 
    product_id, 
    '冰量' as group_name, 
    'ice' as group_type, 
    '1' as is_required, 
    '0' as is_multiple, 
    0 as sort_order, 
    'admin' as create_by, 
    NOW() as create_time, 
    'admin' as update_by, 
    NOW() as update_time
FROM sys_product WHERE product_id != 6;

-- 9. 为所有非ID为6的产品添加甜度配置组
INSERT INTO sys_product_config_group (product_id, group_name, group_type, is_required, is_multiple, sort_order, create_by, create_time, update_by, update_time) 
SELECT 
    product_id, 
    '甜度' as group_name, 
    'sweetness' as group_type, 
    '1' as is_required, 
    '0' as is_multiple, 
    1 as sort_order, 
    'admin' as create_by, 
    NOW() as create_time, 
    'admin' as update_by, 
    NOW() as update_time
FROM sys_product WHERE product_id != 6;

-- 10. 为温度配置组添加热选项
INSERT INTO `ry-vue`.`sys_product_config_option` ( `config_group_id`, `option_name`, `option_value`, `extra_price`, `sort_order`, `is_default`, `create_time`) 
SELECT `config_group_id`, '热', '1', 0.00, 0, '0', '2026-4-2 23:16:31' 
FROM sys_product_config_group 
WHERE group_type = 'temperature';

-- 11. 为所有产品添加默认原料配置
INSERT INTO `ry-vue`.`sys_product_default_material` (`product_id`, `material_id`, `quantity`, `create_time`, `update_time`) 
SELECT 
    product_id, 
    1000 as material_id, 
    1.00 as quantity, 
    '2026-04-02 22:53:44' as create_time, 
    '2026-04-02 22:53:44' as update_time
FROM sys_product;

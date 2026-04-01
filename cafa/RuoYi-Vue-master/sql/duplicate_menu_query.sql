-- 查询sys_menu表中menu_name和path重复的数据，按menu_name和path分组显示
SELECT 
    menu_name, 
    path, 
    GROUP_CONCAT(menu_id SEPARATOR ', ') AS duplicate_ids
FROM sys_menu
GROUP BY menu_name, path
HAVING COUNT(*) > 1
ORDER BY menu_name, path;
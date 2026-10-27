# 数据库

MySQL 8.4，权威结构为 [V001](migrations/V001__create_core_tables.sql) 的 10 张表及有效权限视图。设计见[数据字典](../docs/database/data-dictionary.md)，清洗见[导入规则](../docs/database/import-rules.md)。

| 位置 | 内容 |
| --- | --- |
| [migrations/](migrations/README.md) | 按版本执行的结构迁移 |
| `rollback/` | 对应的结构删除脚本，需备份与维护窗口 |
| [seeds/](seeds/README.md) | 离线导入、稳定编码表与只读核对 SQL |

## 初始化

在独立空库中按顺序执行，`rbac_dev` 替换为已核对的目标库：

```bash
mysql -uroot -p -e "CREATE DATABASE rbac_dev DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci"
mysql -uroot -p --default-character-set=utf8mb4 rbac_dev -e "source database/migrations/V001__create_core_tables.sql"
python database/seeds/import_excel.py --dept data/raw/市政公司部门信息表.xlsx --perm data/raw/市政公司应用软件功能权限清单.xlsx --emp data/raw/市政公司员工信息表.xlsx
mysql -uroot -p --default-character-set=utf8mb4 rbac_dev -e "source data/raw/seed_data.sql"
mysql -uroot -p --default-character-set=utf8mb4 rbac_dev -e "source database/seeds/verify_import.sql"
```

导入器不连接数据库，生成的 SQL 会清空重建 10 表；执行前备份，不能依赖事务回滚。审查失败行、未关联负责人、警告及数量，不能由生成成功推定完整导入成功。

正式原始资料不提交，输出 SQL/报告保存在 `data/raw/`。合成单元测试与原始全量核验分开记录。Docker 空卷初始化和现有库升级见[部署说明](../deploy/README.md)。

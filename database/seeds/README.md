# 数据导入与核对

| 文件 | 职责 |
| --- | --- |
| [import_excel.py](import_excel.py) | XLSX/CSV 清洗并生成离线 SQL 和核对报告，使用标准库 |
| [system_codes.csv](system_codes.csv) | 18 个系统的稳定短码 |
| [function_codes.csv](function_codes.csv) | 359 个功能点的固定编号，不含人员或授权矩阵 |
| [verify_import.sql](verify_import.sql) | 行数、组织关系、权限码、负责人及重复导入只读核对 |

```bash
python database/seeds/import_excel.py --dept data/raw/市政公司部门信息表.xlsx --perm data/raw/市政公司应用软件功能权限清单.xlsx --emp data/raw/市政公司员工信息表.xlsx
```

初始密码由交互输入，所有导入账号强制首次改密。输出 `data/raw/seed_data.sql` 与 `seed_data_report.txt` 含受控资料，均不提交。

生成器不修改数据库；输出 SQL 使用 `TRUNCATE` 全量重建，执行会丢失已有业务修改，须先备份。员工坏行可能被跳过并记录，负责人可能待核对；所有失败、警告、未关联项与全量数量须逐项签收。

新功能默认拒绝导入，确认后使用 `--update-codes` 登记固定编号并审查编码表差异。旧编号不因换序或删除而复用。原清单五角色和平台部门管理员授权分别统计。

具体规则见[导入质量规范](../../docs/database/import-rules.md)；合成单元测试为 `python -m unittest discover -s tests/unit -p 'test_*.py'`。

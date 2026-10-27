# 数据库迁移

当前使用人工受控执行，尚未接入自动迁移工具。文件采用 `VNNN__description.sql` 命名，对应撤销脚本位于 `database/rollback/UNNN__description.sql`。

| 版本 | 迁移 | 内容 | 撤销 |
| --- | --- | --- | --- |
| 001 | [V001__create_core_tables.sql](V001__create_core_tables.sql) | RBAC0 十表及 `v_user_permission` 视图 | [U001](../rollback/U001__create_core_tables.sql) |

迁移不建库或切换库。V001 仅对空库执行一次；已发布结构变更新增迁移版本，并记录前置版本、数据影响、验证与恢复方案。

撤销脚本删除结构及数据；MySQL DDL 会隐式提交，不能作为普通业务事务回滚。先验证备份恢复，再在受控目标库执行。授权结构变更前后需按用户/权限码比较有效权限，防止迁移造成权限扩大或丢失。

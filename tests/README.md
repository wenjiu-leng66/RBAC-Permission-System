# 测试

| 层次 | 位置 | 验证内容 |
| --- | --- | --- |
| 后端单元与分层测试 | `backend-procedural/src/test/java/` | 控制器、服务、持久层职责及关键拒绝路径，按测试类报告核对 |
| 后端 API 集成 | `Stage1ApiTest` | H2 合成库中的认证、权限、数据范围、保护、事务及并发 |
| 前端策略与模拟 API | `tests/frontend/` | 权限并集、范围、路由、原型模拟交互及版本冲突 |
| 离线导入 | `tests/unit/test_import_excel.py` | 合成 XLSX/CSV 清洗、稳定编号、负责人及哈希协议 |
| 契约 | `scripts/check_openapi.py` | 静态 OpenAPI 与运行端方法/路径覆盖 |
| 部署验收 | `scripts/smoke_http.py` | 真实 Cookie/HTTP、拒绝场景、发布入口及分步持久化 |

```bash
mvn -f backend-procedural/pom.xml clean verify
python -m unittest discover -s tests/unit -p 'test_*.py'
node --test tests/frontend/*.test.cjs
```

后端测试与覆盖率报告分别位于 `backend-procedural/target/surefire-reports/`、`backend-procedural/target/site/jacoco/index.html`；报告由对应版本现场生成，不提交构建产物。2026-10-27 后端 90 项、导入 41 项、前端 60 项通过；后端行覆盖率 90.89%、分支覆盖率 75.31%，详见[测试报告](../docs/testing/test-report-2026-10-27.md)。测试数及覆盖率以[验收记录](../docs/stage1/verification-record.md)的本次实测为准。

H2、原型内存测试不能证明 MySQL 全量导入、服务器授权或性能达标。完整步骤见[第一阶段验收](../docs/stage1/README.md)，场景预期见[验收矩阵](../docs/testing/acceptance-matrix.md)。

## 只读SQL测量工具测试

`pwsh -NoProfile -File tests/scripts/measure_full_db_perf.test.ps1`执行8组隔离测试，使用假客户端子进程检查只读约束、数值/样本边界、解析、去敏和超时；不连接MySQL。GitHub自动检查已加入该命令，Gitee可按同一命令复现。

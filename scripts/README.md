# 工程脚本

以下命令在仓库根目录执行。

| 脚本 | 职责 |
| --- | --- |
| `python scripts/check_repository.py` | 文件结构、UTF-8、Markdown 本地链接、冲突标记、禁入文件和合成样例引用 |
| `python scripts/check_openapi.py` | OpenAPI 结构与引用；加 `--base-url` 比较运行端方法/路径集合 |
| `sh scripts/deploy-stage1.sh` | 读取部署环境文件，构建并等待三个服务健康 |
| `python scripts/smoke_http.py --base-url http://127.0.0.1 --env-file deploy/.env --check-web` | 真实 HTTP/Cookie 的一期代表场景及发布入口 |

`check_openapi.py` 需要 `scripts/requirements-validation.txt` 中的依赖；其余 Python 脚本使用标准库。HTTP 冒烟要求独立合成验收环境，会创建和清理临时账号、角色。

持久化验证使用 `persistence-create`、实际重启、`persistence-verify`、`persistence-cleanup` 四步；标记文件保存在本地 `data/raw/`。参数见 `--help`，执行标准见[验收步骤](../docs/stage1/self-test.md)。

仓库静态检查不代替应用测试，也不是全面的秘密或个人信息扫描。部署及数据操作须核对环境和目标库。

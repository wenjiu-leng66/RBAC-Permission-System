# API 契约

[openapi.yaml](openapi.yaml) 为权威接口文件，版本 `0.3.0`。覆盖认证、首次改密、当前权限、门户、员工台账、用户、部门、角色、目录、授权整组替换与健康检查。

- 基础路径：`/api/v1`；会话：`JSESSIONID` Cookie。
- 登录请求：`{account,password}`，`account`接受工号或手机号；用户资料的工号字段为`username`。
- 业务成功响应直接返回对象；分页为 `items/page/pageSize/total`。
- 错误响应：`{error:{code,message,requestId,details}}`。
- 角色授权为 `grants:[{code,scope}]`；仅接受清单权限码，不直接授予派生 `rbac:*`。
- `401` 表示身份无效，`403` 表示权限或范围拒绝，`409` 表示版本冲突或保护规则冲突。

字段、权限映射及版本规则见[接口约定](../docs/standards/api-contract.md)。

## 发布入口

API 文档由 [Apifox 公开文档](https://7u8ub108r6.apifox.cn/)统一展示；现场接口调试使用[服务器 Swagger UI](http://124.70.42.35:8080/swagger-ui/index.html?url=/api-design/openapi.yaml) 加载静态契约；`/api-design/openapi.yaml` 发布该文件，`/v3/api-docs` 为运行后端生成的接口文档。

```bash
python -m pip install -r scripts/requirements-validation.txt
python scripts/check_openapi.py
python scripts/check_openapi.py --base-url http://127.0.0.1
```

在线比较验证 HTTP 方法和路径集合双向一致；字段、响应状态和授权语义另由后端测试及真实 HTTP 验收核对。执行结果见[验收记录](../docs/stage1/verification-record.md)。

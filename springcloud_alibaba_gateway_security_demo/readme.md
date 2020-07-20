自定义拦截访问：

采用自定过滤器TokenFilter配置访问：
curl -X POST http://127.0.0.1:8081/test/1 -d "name=zhs"  -H 'token:62bee314-a585-4d3b-9e11-0efb79d4c492'

采用AuthenticationWebFilter配置访问：
curl -X POST http://127.0.0.1:8081/test/1 -d "name=zhs"  -H 'Authorization:Bearer 62bee314-a585-4d3b-9e11-0efb79d4c492'


Steps to enable feign:
1. Add openfeign dependency
2. Add @EnableFeignClients in the Spring Boot Application main class
3. Create proxies to the webservices needed by the application

Steps to add Ribbon balance loading:
1. Add netflix-ribbon dependency
2. Add @RibbonClient in the proxy
3. Add {ServiceAppName}.ribbon.listOfServers: http(s)://{server}:{port},http(s)://{server}:{port}, (etc...)

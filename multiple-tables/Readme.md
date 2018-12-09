Steps to enable feign:
1. Add openfeign dependency
2. Add @EnableFeignClients in the Spring Boot Application main class
3. Create proxies to the webservices needed by the application

Steps to add Ribbon balance loading:
1. Add netflix-ribbon dependency
2. Add @RibbonClient in the proxy
3. Add {ServiceAppName}.ribbon.listOfServers: http(s)://{server}:{port},http(s)://{server}:{port}, (etc...)

Steps to register to Eureka Naming Server:
1. Add netflix-eureka-client dependency
2. Add @EnableDiscoveryClient in the SpringBootApplication main class
3. Add the eureka configuration - eureka.client.serviceUrl.defaultZone=http://localhost:40002/server-naming/eureka
4. Remove ribbon.listOfServers in the config. Make sure they are gone from all other versions of the config or it may still be in effect otherwise.
# carambola todo 


## 启用p6spy

```ini
spring.datasource.driver-class-name=com.p6spy.engine.spy.P6SpyDriver

spring.datasource.url=jdbc:p6spy:mysql://localhost:3306/db_carambola?useLegacyDatetimeCode=false&serverTimezone=UTC
```

## 串码记录

增加请求串码记录

```ini
logging.pattern.file=%d{YYYY-MM-dd HH:mm:ss.SSS} || %X{REQ_NO} || %-5level || %logger{20} ||  %msg%n
```# wonfu-spring-base

## 创建DB

```sql
create database db_carambola default character set utf8mb4 collate utf8mb4_unicode_ci;
```


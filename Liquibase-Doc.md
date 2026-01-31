# Liquibase


### Add library Liquibase
```pom
<dependency>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-core</artifactId>
</dependency>
```

### Add [changelog-master.xml](src%2Fmain%2Fresources%2Fdb%2Fchangelog-master.xml)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         https://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.20.xsd">

    <include file="db/changelog/insert-user-role.sql"/>


</databaseChangeLog>

```

### Add Sql Query [insert-user-role.sql](src%2Fmain%2Fresources%2Fdb%2Fchangelog%2Finsert-user-role.sql)
```sql
INSERT INTO roles (name)
VALUES
    ('ROLE_ADMIN'),
    ('ROLE_MODERATOR'),
    ('ROLE_USER');
```

### Add properties
```properties
#liquibase
spring.liquibase.enabled=true
spring.liquibase.change-log=classpath:db/changelog-master.xml
```
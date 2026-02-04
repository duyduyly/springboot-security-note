# Overview 

## Require
- User Submit Question
- Reviewer Accept Question
- Mentor Answer Accepted Question
- Admin Manage Question and User

-----------------
<br/>

## Roles and Permissions
- `Roles`: USER, REVIEWER, ADMIN, MENTOR
- `Permissions`: VIEW ,CREATE, UPDATE, ACCEPT, REMOVE, REPLY.
- `User`
  - CREATE
  - UPDATE
  - VIEW
- `Reviewer`
  - VIEW
  - ACCEPT
- `Admin` 
  - ALL without REPLY.
- `Mentor`
  - VIEW
  - REPLY
  - ACCEPT.

-----------------
<br/>

## API
 - Auth
   - sign-in
   - sign-up
   - refresh-token
   - forgot-password
   - otp-verify
   - resend-otp

- Question:
  - submit
  - update
  - delete
  - view
  - accept
  - answer

-----------------
<br/>

## API Document
--> [Api Document.md](component/Api%20Document.md)


-----------------
<br/>

## Physical Erd
- https://drive.google.com/file/d/1OA1mJZFMwNlOPOmZ8IVB9opQNOCVWAFU/view?usp=sharing

<br/>

- ![Spring Security Mono Physical ERD Chart.png](resource/Spring%20Security%20Mono%20Physical%20ERD%20Chart.png)


-----------------
<br/>


### Common Setup

#### Email Setup

- Get username password from email
- Go to Google App Passwords:
    - https://myaccount.google.com/apppasswords
- create App Password for Mail.


##### Liquibase


##### Add library Liquibase
```pom
<dependency>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-core</artifactId>
</dependency>
```

##### Add [changelog-master.xml](src%2Fmain%2Fresources%2Fdb%2Fchangelog-master.xml)
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

##### Add Sql Query [insert-user-role.sql](src%2Fmain%2Fresources%2Fdb%2Fchangelog%2Finsert-user-role.sql)
```xml

<!--  Create Structure ChangeSet-->
    <changeSet id="001-create-users" author="alan">
        <createTable tableName="users">
            <column name="id" type="BIGINT" autoIncrement="true">
                <constraints primaryKey="true" nullable="false"/>
            </column>
            <column name="username" type="VARCHAR(40)">
                <constraints nullable="false" unique="true"/>
            </column>
            <column name="email" type="VARCHAR(50)">
                <constraints nullable="false" unique="true"/>
            </column>
            <column name="password" type="VARCHAR(300)">
                <constraints nullable="false"/>
            </column>
            <column name="active" type="BIT" defaultValueBoolean="true"/>
            <column name="verify" type="BIT" defaultValueBoolean="false"/>
            <column name="role_id" type="BIGINT"/>
        </createTable>
    </changeSet>

<!--Insert-->
<changeSet id="01-insert-roles" author="alan">
<insert tableName="roles">
    <column name="role_name" value="USER"/>
</insert>
<insert tableName="roles">
    <column name="role_name" value="REVIEWER"/>
</insert>
<insert tableName="roles">
    <column name="role_name" value="ADMIN"/>
</insert>
<insert tableName="roles">
    <column name="role_name" value="MENTOR"/>
</insert>
</changeSet>
```

##### Add properties
```properties
#liquibase
spring.liquibase.enabled=true
spring.liquibase.change-log=classpath:db/changelog-master.xml
```
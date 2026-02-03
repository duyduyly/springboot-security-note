# API Document

--> [Back To Home](Document.md)

## Sign Up
### 1. `POST http://localhost:9091/api/auth/signup`
```json
{
  "username": "alandemo2",
  "email": "alandemo1113332@yopmail.com",
  "password":"123456",
  "role": "ADMIN",
  "firstName": "Alan",
  "lastName": "1"
}
```

Response
```json
{
  "message": "User registered successfully!"
}
```
#
#### Error
Request:
```json
{
  "username": "",
  "email": "",
  "password":"123456",
  "role": "ADMIN",
  "firstName": "Alan",
  "lastName": "1"
}
```

Response:
```json
{
  "email": "must not be blank",
  "username": "must not be blank"
}
```







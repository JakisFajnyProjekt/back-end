# Authentication Controller endpoints

## 1. Register user

`request url: /api/auth/register`

### HTTP Request:

`POST: /api/auth/register`

* ### Request Body:

  ```
  {
    "firstName":"firstName",
    "lastName": "lastName",
    "password":"password",
    "email":"email@email.com"
  }
  ```

* ### Patch Variable:
  empty

### Expect:

**JSON** with JWT token with `200 OK` status

Example :

* HTTP status 200 OK

  ```
  {
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIi
  OiJkb21haXNhbmlrQGFzc3NzYS5jb20iLCJpYXQiOjE3MDA0MDcxNT
  ksImV4cCI6MTcwMDQwODU5OSwicm9sZXMiOlsiUk9MRV9VU0VSIl19.UhT3fYP
  pd8pAyZ3QMhjrJbvbpuuthS58kBVK7vKhU0w"
  }
  ```

* Http `400 Bad Request` for each condition not meet for email taken `409 CONFLICT`

   ```
       {
      "message": "Validation failed",
      "errors": {
         "firstName": [
              "At least 2 characters are required"
         ],
         "lastName": [
             "At least 2 characters are required"
         ],
         "password": [
             "At least 1 capital letter is required",
             "At least 1 lowercase letter is required",
              "At least 1digit is required",
             "This field cannot be empty and contain whitespaces",
             "Password must contain minimum 6characters"
         ],
         "email": [
             "Email format is not valid"
         ]
        }
       }
   ```

## 2. Login user

`request url: /api/auth/login`

### HTTP Request:

`POST: /api/auth/login`

* ### Request Body:

  ```
  {

    "password":"password",
    "email":"email@email.com"
  }
  ```

* ### Patch Variable:
  empty

### Expect:

**JSON** with JWT token with `200 OK` status

Example :

* HTTP status 200 OK

  ```
  {
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIi
  OiJkb21haXNhbmlrQGFzc3NzYS5jb20iLCJpYXQiOjE3MDA0MDcxNT
  ksImV4cCI6MTcwMDQwODU5OSwicm9sZXMiOlsiUk9MRV9VU0VSIl19.UhT3fYP
  pd8pAyZ3QMhjrJbvbpuuthS58kBVK7vKhU0w"
  }
  ```

* Http `400 Bad Request` if you give not existing email or password

   ```
   {
    "message": "Invalid credentials",
    "errors": {
        "EMAIL": [
            "Invalid email"
        ]
    }
   },
  {
    "message": "Invalid credentials",
    "errors": {
        "EMAIL": [
            "Invalid password"
        ]
    }
  }
   ```

## 3. Logout user

`request url: /api/auth/logout`

### HTTP Request:

`POST: /api/auth/logout`

* ### Request Body:

  empty

* ### Patch Variable:
  empty

### Expect:

**JSON**  with `200 OK` status



   





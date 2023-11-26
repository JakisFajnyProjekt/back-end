# User Controller endpoints

* `All user endpoints are secured, registration are required otherwise you will get status 403 Forbbiden`

## 1. Get list of all users

`request url: /api/users`

### HTTP Request:

`GET: /api/users`

* ### Request Body:
  empty

* ### Patch Variable:
  empty

### Expect:

**JSON** with array of all users with `200 OK` status

Example :

* HTTP status 200 OK with orders
  ```
  [
  {
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "password": "string",
    "role": "USER"
  },
  {
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "password": "string",
    "role": "USER"
  },
  {
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "password": "string",
    "role": "USER"
  }
  ]
  ```
* HTTP status 200 OK with empty list

  ```
   []
  ```

## 2. Get order By ID

`request url: /api/users/{{userId}}`

### HTTP Request:

`GET: /api/users/{{userId}}`

* ### Request Body:
  empty

* ### Patch Variable:
  user ID required

### Expect:

**JSON**  user with `200 OK` status

Example :

* HTTP status 200 OK with correct user id
  ```
  [
  {
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "password": "string",
    "role": "USER"
  }
  ]
  ```
* HTTP status `404 Not Found` with wrong user id

  ```
  {
  "message": "Not found with given id 1",
  "code": "404 NOT_FOUND",
  "localDate": "2023-11-12"
  }
  ```

## 3. Delete User By ID

`request url: /api/users/{{userId}}`

### HTTP Request:

`DELETE: /api/users/{{userId}`

* ### Request Body:
  empty

* ### Patch Variable:
  user ID required

### Expect:

**JSON**  order with `202 Accepted` status

Example :

* HTTP status `202 Accepted` with correct user id
  ```
  1
  ```

* HTTP status `404 Not Found` with wrong user id

  ```
  {
  "message": "Not found with given id 1",
  "code": "404 NOT_FOUND",
  "localDate": "2023-11-12"
  }

## 4. Modify and add user

`request url: /api/users`

### HTTP Request:

`PUT: /api/users`

* ### Request Body:
  ```
  {
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "password": "string"
  }
  ```

* ### Patch Variable:

  user ID required

### Expect:

**JSON** with status `202 Accepted` and will return te body

Example :

* HTTP status 202 Accepted
  ```
  {
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "password": "string"
  }
  ```

* HTTP status `404 Not Found` with user id:
  ```
  {
  "message": "Not found with given id 1",
  "code": "404 NOT_FOUND",
  "localDate": "2023-11-12"
  }
  ```




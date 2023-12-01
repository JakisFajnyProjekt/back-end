# Address Controller endpoints

* `All addess endpoints are secured, registration are required otherwise you will get status 403 Forbbiden`

## 1. Get list of all restaurants

`request url: /api/addresses/all`

### HTTP Request:

`GET: /api/addresses/all`

* ### Request Body:
  empty

* ### Patch Variable:
  empty

### Expect:

**JSON** with array of all addresses with `200 OK` status

Example :

* HTTP status 200 OK with restaurants
  ```
  [
  {
    "id": 0,
    "houseNumber": "string",
    "street": "string",
    "city": "string",
    "postalCode": "string"
  },
  {
    "id": 1,
    "houseNumber": "string",
    "street": "string",
    "city": "string",
    "postalCode": "string"
  },
  {
    "id": 2,
    "houseNumber": "string",
    "street": "string",
    "city": "string",
    "postalCode": "string"
  }
  ]
  ```
* HTTP status 200 OK with empty list

  ```
   []
  ```

## 2. Get address By ID

`request url: /api/addresses/{{addressId}}`

### HTTP Request:

`GET: /api/addresses/{{addressId}`

* ### Request Body:
  empty

* ### Patch Variable:
  address ID required

### Expect:

**JSON**  address with `200 OK` status

Example :

* HTTP status 200 OK with correct address id
  ```
  {
    "id": 3,
    "houseNumber": "string",
    "street": "string",
    "city": "string",
    "postalCode": "string"
  }
  ```
* HTTP status 404 Not Found with wrong address id

  ```
  {
  "message": "Not found with given id 1",
  "code": "404 NOT_FOUND",
  "localDate": "2023-11-12"
  }
  ```

## 3. Create and add Address

`request url: /api/addresses`

### HTTP Request:

`POST: /api/addresses`

* ### Request Body:
  ```
  {
  "houseNumber": "string",
  "street": "string",
  "city": "string",
  "postalCode": "string"
  } 
  ```

* ### Patch Variable:
  empty

### Expect:

**JSON** with status `200` and will return te body

Example :

* HTTP status 200 OK
  ```
  {
  "houseNumber": "string",
  "street": "string",
  "city": "string",
  "postalCode": "string"
  } 
  ```
    * HTTP status 400 BAD_REQUEST if address is already exist

  ```
  {
    "message": "Address already exist",
    "code": "400 BAD_REQUEST",
    "localDate": "2023-11-13"
   }
  ```
    * HTTP status 400 BAD_REQUEST if one of the value are empty

  ```
  {
    "message": "houseNumber cannot be null",
    "code": "400 BAD_REQUEST",
    "localDate": "2023-11-13"
  }
  
  {
    "message": "street cannot be null",
    "code": "400 BAD_REQUEST",
    "localDate": "2023-11-13"
  }
  
  {
    "message": "city cannot be null",
    "code": "400 BAD_REQUEST",
    "localDate": "2023-11-13"
  }
  
  {
    "message": "postalCode cannot be null",
    "code": "400 BAD_REQUEST",
    "localDate": "2023-11-13"
  }
  ``` 
  ## 4. Delete address By ID

`request url: /api/addresses/{{addressId}}`

### HTTP Request:

`DELETE: /api/addresses/{{addressId}`

* ### Request Body:
  empty

* ### Patch Variable:
  address ID required

### Expect:

**JSON**  address with `202 Accepted` status

Example :

* HTTP status 202 Accepted with correct address id
  ```
  1
  ```
  
  
# Dish Controller endpoints


* `All dish endpoints are secured, registration are required otherwise you will get status 403 Forbbiden`
## 1. Get list of all dishes

`request url: /api/dishes`
### HTTP Request:
`GET: /api/dishes`


* ### Request Body:
  empty

* ### Patch Variable:
  empty

### Expect:
**JSON** with array of all dishes with `200 OK` status

Example :
* HTTP status 200 OK with dishes


  `"category"` are selected from enum list
  ```
  [
  {
    "name": "string",
    "description": "string",
    "price": 0,
    "restaurantId": 0,
    "category": "APPETIZER"
  },
  {
    "name": "string",
    "description": "string",
    "price": 0,
    "restaurantId": 0,
    "category": "APPETIZER"
  },
  {
    "name": "string",
    "description": "string",
    "price": 0,
    "restaurantId": 0,
    "category": "APPETIZER" 
  },
  ]
  ```
  
* HTTP status 200 OK with empty list

  ```
   []
  ```


## 2. Get dish By ID

`request url: /api/dishes/{{dish}}`

### HTTP Request:
`GET: /api/dishes/{{dishId}}`


* ### Request Body:
  empty

* ### Patch Variable:
  dish ID required

### Expect:
**JSON**  dish with `200 OK` status

Example :
* HTTP status 200 OK with correct dish id
  ```
  [
  {
  "name": "string",
  "description": "string",
  "price": 0,
  "restaurantId": 0,
  "category": "APPETIZER"
  }
  ]
  ```
* HTTP status 404 Not Found with wrong dish id

  ```
  {
  "message": "Not found with given id 1",
  "code": "404 NOT_FOUND",
  "localDate": "2023-11-12"
  }
  ```
## 3. Create and add dish

`request url: /api/dishes`
### HTTP Request:

`POST: /api/dishes`

* ### Request Body:
  ```
  {
  "name": "string",
  "description": "string",
  "price": 0,
  "restaurantId": 0,
  "category": "APPETIZER"
  } 
  ```

* ### Patch Variable:
  empty

### Expect:
**JSON** with status `200` and will return te body

Example :
* HTTP status 200 OK with correct restaurant id
  ```
  {
  "name": "string",
  "description": "string",
  "price": 0,
  "restaurantId": 0,
  "category": "APPETIZER"
  } 
  ```
* HTTP status `404 Not Found` with wrong restaurant id:
  ```
  {
    "message": "Restaurant Not Found",
    "code": "404 NOT_FOUND",
    "localDate": "2023-11-15"
  }

## 4. Modify and add dish

`request url: /api/dishes`
### HTTP Request:

`PUT: /api/dishes`

* ### Request Body:
  ```
  {
  "name": "string",
  "description": "string",
  "price": 0,
  "restaurantId": 0,
  "category": "APPETIZER"
  } 
  ```

* ### Patch Variable:

  dish ID required

### Expect:
**JSON** with status `200` and will return te body

Example :
* HTTP status 200 OK
  ```
  {
  "name": "string",
  "description": "string",
  "price": 0,
  "restaurantId": 0,
  "category": "APPETIZER"
  } 
  ```

* HTTP status `404 Not Found` with wrong dish id:
  ```
  {
  "message": "Not found with given id 1",
  "code": "404 NOT_FOUND",
  "localDate": "2023-11-12"
  }
  ```




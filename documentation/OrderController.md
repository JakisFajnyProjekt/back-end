# Order Controller endpoints

* `All order endpoints are secured, registration are required otherwise you will get status 403 Forbbiden`

## 1. Get list of all orders

`request url: /api/orders`

### HTTP Request:

`GET: /api/orders`

* ### Request Body:
  empty

* ### Patch Variable:
  empty

### Expect:

**JSON** with array of all orders with `200 OK` status

Example :

* HTTP status 200 OK with orders
  ```
  [
  {
    "orderTime": "2023-11-15T15:18:39.755Z",
    "totalPrice": 78,
    "userId": 1,
    "dishIds": [
      1,1,3,4,1
    ],
    "deliveryAddressId": 2,
    "restaurantId": 1
  },
  {
    "orderTime": "2023-11-15T15:18:39.755Z",
    "totalPrice": 99,
    "userId": 1,
    "dishIds": [
      1,1,3,4,1,2
    ],
    "deliveryAddressId": 2,
    "restaurantId": 1
  }
  ]
  ```
* HTTP status 200 OK with empty list

  ```
   []
  ```

## 2. Get order By ID

`request url: /api/orders/{{orderId}}`

### HTTP Request:

`GET: /api/orders/{{orderId}}`

* ### Request Body:
  empty

* ### Patch Variable:
  orderId ID required

### Expect:

**JSON**  order with `200 OK` status

Example :

* HTTP status 200 OK with correct order id
  ```
  [
  {
    "orderTime": "2023-11-15T15:18:39.755Z",
    "totalPrice": 78,
    "userId": 1,
    "dishIds": [
      1,1,3,4,1
    ],
    "deliveryAddressId": 2,
    "restaurantId": 1
  }
  ]
  ```
* HTTP status `404 Not Found` with wrong order id

  ```
  {
  "message": "Not found with given id 1",
  "code": "404 NOT_FOUND",
  "localDate": "2023-11-12"
  }
  ```

## 3. Create and add order

`request url: /api/orders`

### HTTP Request:

`POST: /api/orders`

* ### Request Body:
  ```
  {
  "userId": 1,
  "dishIds": [
    1,2,3,4
  ],
  "deliveryAddressId": 1,
  "restaurantId": 1
   }  
  ```
    * #### all ids must be correct otherwise exception will be thrown for each wrong id


* HTTP status `404 Not Found` with wrong ids:
  ```
  {
    "message": "Restaurant Not Found",
    "code": "404 NOT_FOUND",
    "localDate": "2023-11-15"
  }
  
  {
    "message": "User Not Found",
    "code": "404 NOT_FOUND",
    "localDate": "2023-11-15"
  }
  
  {
    "message": "Address Not found",
    "code": "404 NOT_FOUND",
    "localDate": "2023-11-15"
  }
  
  {
    "message": "You have not ordered anything",
    "code": "404 NOT_FOUND",
    "localDate": "2023-11-15"
  }

  ```

* ### Patch Variable:
  empty

### Expect:

**JSON** with status `200` and will return te body

Example :

* HTTP status 200 OK with correct restaurantAddress id
  ```
  {
  "userId": 1,
  "dishIds": [
    1,
    2,
    3,
    4
  ],
  "deliveryAddressId": 1,
  "restaurantId": 1
   } 
  ```

## 4. Delete Order By ID

`request url: /api/orders/{{addressId}}`

### HTTP Request:

`DELETE: /api/orders/{{orderId}`

* ### Request Body:
  empty

* ### Patch Variable:
  order ID required

### Expect:

**JSON**  order with `202 Accepted` status

Example :

* HTTP status `202 Accepted` with correct order id
  ```
  1
  ```

* HTTP status `404 Not Found` with wrong order id

  ```
  {
  "message": "Not found with given id 1",
  "code": "404 NOT_FOUND",
  "localDate": "2023-11-12"
  }




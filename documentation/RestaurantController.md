# Restaurant Controller endpoints

* `All restaurant endpoints are secured, registration are required otherwise you will get status 403 Forbbiden`

## 1. Get list of all restaurants

`request url: /api/restaurants`

### HTTP Request:

`GET: /api/restaurants`

* ### Request Body:
  empty

* ### Patch Variable:
  empty

### Expect:

**JSON** with array of all restaurants with `200 OK` status

Example :

* HTTP status 200 OK with restaurants
  ```
  [
  {
    "restaurantId": 0,
    "name": "The Savory Bistro",
    "restaurantAddress": 1
  },
  {
    "restaurantId": 1,
    "name": "Rustic Flavor Grill",
    "restaurantAddress": 2
  },
  {
    "restaurantId": 3,
    "name": "Oceanview Seafood House",
    "restaurantAddress": 3
  },
  {
    "restaurantId": 6,
    "name": "Fireside Steakhouse & Bar",
    "restaurantAddress": 4
  },
  ]
  ```
* HTTP status 200 OK with empty list

  ```
   []
  ```

## 2. Get restaurant By ID

`request url: /api/restaurants/{{restaurantId}}`

### HTTP Request:

`GET: /api/restaurants/{{restaurantId}}`

* ### Request Body:
  empty

* ### Patch Variable:
  restaurant ID required

### Expect:

**JSON**  restaurants with `200 OK` status

Example :

* HTTP status 200 OK with correct restaurant id
  ```
  [
  {
    "restaurantId": 1,
    "name": "The Savory Bistro",
    "restaurantAddress": 1
  }
  ]
  ```
* HTTP status 404 Not Found with wrong restaurant id

  ```
  {
  "message": "Not found with given id 1",
  "code": "404 NOT_FOUND",
  "localDate": "2023-11-12"
  }
  ```

## 3. Create and add restaurant

`request url: /api/restaurants`

### HTTP Request:

`POST: /api/restaurants`

* ### Request Body:
  ```
  {
  "name": "string",
  "restaurantAddress": 0
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
   "name": "string",
   "restaurantAddress": 0
   }
  ```
* HTTP status 404 Not Found if restaurant ID doesn't exist

  ```
  {
    "message": "Address not found",
    "code": "404 NOT_FOUND",
    "localDate": "2023-11-12"
  }
  ```

## 4. Find order for Restaurant

`request url: /api/restaurants/orders/{restaurantid}`

### HTTP Request:

`GET: /api/restaurants/orders/{restaurantid}`

* ### Request Body:
  empty

* ### Patch Variable:
  restaurantId

### Expect:

**JSON** with status `200` and will return te body

Example :

* HTTP status 200 OK with correct restaurantAddress id
  ```
  [
    {
        "orderId": 0,
        "orderTime": "2023-11-24T19:26:49.011",
        "totalPrice": 121,
        "userId": 4,
        "dishIds": [
            1
        ],
        "deliveryAddressId": 1
    },
  {
        "orderId": 1,
        "orderTime": "2023-11-24T19:26:49.011",
        "totalPrice": 121,
        "userId": 4,
        "dishIds": [
            1
        ],
        "deliveryAddressId": 1
    },
  {
       "orderId": 3,
        "orderTime": "2023-11-24T19:26:49.011",
        "totalPrice": 121,
        "userId": 4,
        "dishIds": [
            1
        ],
        "deliveryAddressId": 1
    }
  ]
  ```
* HTTP status 404 Not Found if restaurant ID doesn't exist

  




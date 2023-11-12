# Restaurant Controller endpoints


* `All addess endpoints are secured, registration are required otherwise you will get status 403 Forbbiden`
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
    "name": "The Savory Bistro",
    "restaurantAddress": 1
  },
  {
    "name": "Rustic Flavor Grill",
    "restaurantAddress": 2
  },
  {
    "name": "Oceanview Seafood House",
    "restaurantAddress": 3
  },
  {
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




POST http://localhost:8082/cart
Content-Type: application/json
{
  "sku": "something",
  "count": 1
}

PATCH http://localhost:8082/cart
Content-Type: application/json
{
  "sku": "something",
  "count": 2
}

POST http://localhost:8082/cart
Content-Type: application/json
{
  "sku": "demo",
  "count": 3
}

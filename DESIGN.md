# DESIGN

```
GET    /cart          () -> { _ }
POST   /cart          {sku: _, count: _} -> {sku: _, count: _, label: _, price: _}
PATCH  /cart          {sku: _, count: _} -> {sku: _, count: _, label: _, price: _}
DELETE /cart          () -> {}
DELETE /cart/<ID>     () -> {}
POST   /cart/checkout () -> { "order": "id" }

POST   /cart/discount { code: _ } -> { code: _, label: _, percentage: _}
DELETE /cart/discount () -> {}
```

Materialized

`GET /cart`

```
{
  "discount": {
    "code": "_",
    "label": "_",
    "percentage": "_"
  }
  "contents": [
    {
      "sku": "_",
      "label": "_",
      "count: _,
      "price": ""
    }
  ],
  ""
}
```

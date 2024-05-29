# README

## ASSIGNMENT

The assignment is to build a shopping cart

- TW1: As a customer, I want a shopping cart to order multiple items in a single transaction
- TW2: As a customer, I want to add a discount coupon to receive my discount
- TW3: As a customer, I want to see the accumulative value of the items in the shopping cart to know how much I need to pay when ordering.

Don't worry about these things:

- The checkout process (including payment and handling customer information like a shipping address) is out of scope.
- Internationalization is out of scope (e.g., dealing with other timezones, currencies, or languages).
- There is no minimum order.
- You can assume that there is only one shopping cart and a single user.
- There are no shipping costs.
- You can assume that every item has an unlimited amount in stock.
- The data layer is out of scope (i.e., you can keep all the data in memory and don't have to persist it to a database, for example)

For details, see the [ASSIGNMENT.md](/ASSIGNMENT.md)

## Decisions/Thoughts

- little time to make a comolete solution
- simple > complex
- keep error handling/modelling very simple.
  - leverage zio, no custom errors, just exception
  - did not check error serialization as json (since api not specified)
- pulled price and description into a random memory storage
  - user should not be able to add description/price
  - but I needed it, so I create them randomly
  - helps with changes in text and prices to delay this
- no proper event handling
  - just "publish" event by writing to console
  - technically event publishing can fail, which would fail API call
    - should probably be fire/forget but depends
    - can we miss events?
- no storage for cart
  - just in memory
- single cart for everyone
  - should probably have an id or should be attached to an account
- PATCH vs POST
  - I added both for changing items and coupons with same semantics
  - not proper REST but acceptable.
  - did not want to take a hard stance there and treat it as upsert
- CRUD vs EventBased
  - went with CRUD
  - could have done EventBased
  - actually that is a missed oppurtunity since there was no requirement of any backchannel except "GET /cart"
    - turn requests into commands, consume with a stream, handle, emit event
      - possiby sse/websocket back to client if backchannel needed
      - frontend would need to be able to deal with items
        - or do it optimistically
        - backend should then maybe return full info on every upsert
    - with CQRS then everything except getCart would be a command
      - and then getCart could be only read
- repo test should probably be separate project (in scala-world integration tests are done thayt way)
  - could be test containers
- proper types for sku and coupon code
  - not much work to use AnyVal but dealing with json formatter is annoying

## Development

Requirements:

- [OpenJDK](https://openjdk.org/) `brew install openjdk@17` (tested with 17)
- [sbt](https://www.scala-sbt.org/) `brew install sbt`

Optional:

- [task](https://taskfile.dev) `brew install go-task`

### via sbt

```
cd server
sbt compile
sbt test
```

## Demo

via `task`

```
# window 1
task run
```

```
# window 2
task demo
```

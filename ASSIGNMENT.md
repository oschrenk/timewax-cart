## Timewax Shopping Cart assignment

The goal for this assignment is to have a starting point for a further conversation about code. That means that it isn’t a coding test. It is okay if you make mistakes or don’t succeed at specific parts of the assignment. It is meant for people at different experience levels, and the expected results vary accordingly. The important thing is that you can explain the choices you have made.

Please implement the logic needed for an e-commerce shopping cart using the user stories below to guide the implementation.

## User stories

### TW1: As a customer, I want a shopping cart to order multiple items in a single transaction

An item is something with an SKU (https://en.wikipedia.org/wiki/Stock_keeping_unit), display name, and price.

**Acceptance criteria:**

- Every item (SKU) in the cart needs to have a quantity between 1 and 1000.
- Every mutation to the shopping cart needs to be recorded as an "event" with a `DateTime` (a future user story could be about writing these mutations to, e.g., Google Analytics, but for now, this is out of scope!).

- Must be able to return a list of items with their quantities (as a materialized view or as is).
- Must be able to add and remove items.
- Must be able to empty the entire cart in one operation.
- Must be able to change the number of individual items.

### TW2: As a customer, I want to add a discount coupon to receive my discount

**Acceptance criteria:**

- We only support fixed percentage discounts (e.g., 10%), and there are no special business rules about combinations, times of the week, minimum amount, etc.
- Discount codes don't expire.
- As there is no database, it is acceptable to configure the available discount codes as part of the source code.
- Only one discount can be applied to the shopping cart.

### TW3: As a customer, I want to see the accumulative value of the items in the shopping cart to know how much I need to pay when ordering.

**Acceptance criteria:**

- Consider the optional discount (see the user story about discounts).
- Shipping costs are out of scope.
- There is no minimum order amount.

## Scope

The assignment is meant to be completed in about 2-4 hours (a little more or less is OK), so keep it simple and approach it as an MVP or prototype implementation. If you need more time than you have, just share a partial implementation instead. You don't have to complete it as long as you have something to share.

You can choose: either to implement the front-end user interface of the shopping cart or the business rules that can also run in the back-end. Of course, implementing both is okay, but you don’t have to.

You have complete freedom regarding the programming language(s), frameworks, libraries, and whatnot. It doesn't have to be something that is already part of our stack. You could implement everything in Haskell, use event sourcing as a data model, or write everything in PHP. The important part is that you can explain your choices (e.g., just pick tools you're comfortable with or use that new programming language you always wanted to try).

Don't worry about these things:

- The checkout process (including payment and handling customer information like a shipping address) is out of scope.
- Internationalization is out of scope (e.g., dealing with other timezones, currencies, or languages).
- There is no minimum order.
- You can assume that there is only one shopping cart and a single user.
- There are no shipping costs.
- You can assume that every item has an unlimited amount in stock.
- The data layer is out of scope (i.e., you can keep all the data in memory and don't have to persist it to a database, for example)

While out of scope, issues like the above might be part of the conversation after you've completed the assignment ("What if we were to add internationalization?").

## The evaluation

Please share the code on GitHub, BitBucket, or similar before the appointment. At that point, we will look at it internally and invite you for a code review session. During that meeting, you will present your work, and we will dive deeper into your choices.

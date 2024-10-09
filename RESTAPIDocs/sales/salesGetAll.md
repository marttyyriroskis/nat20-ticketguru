# Show Accessible Sales

Show all Sales the active User can access and with what permission level.

**URL** : `/api/sales`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

**Data constraints** : `{}`

## Example Request

```json
GET api/sales
Content-Type: application/json
```

## Success Responses

**Condition** : User sees an empty list. There are no Sales to show with user's permissions.

**Code** : `200 OK`

**Content** : `{[]}`

### OR

**Condition** : User can see one or more Sales.

**Code** : `200 OK`

**Content** : In this example, the User can see two Sales:

```json
[
  {
    "id": 1,
    "paidAt": "2024-10-06T19:18:28.891855",
    "userId": 1,
    "tickets": [
      {
        "id": 1,
        "barcode": "1728231508859",
        "usedAt": null,
        "price": 0.0,
        "ticketTypeId": 1,
        "saleId": 1
      },
      {
        "id": 2,
        "barcode": "1728231508863",
        "usedAt": null,
        "price": 0.0,
        "ticketTypeId": 2,
        "saleId": 1
      }
    ]
  },
  {
    "id": 2,
    "paidAt": "2024-10-06T19:18:28.896336",
    "userId": 2,
    "tickets": [
      {
        "id": 3,
        "barcode": "1728231508866",
        "usedAt": null,
        "price": 0.0,
        "ticketTypeId": 2,
        "saleId": 2
      }
    ]
  }
]
```

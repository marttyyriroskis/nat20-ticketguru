# Show Accessible Tickets

Show all Tickets the active User can access and with what permission level.

**URL** : `/api/tickets`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

**Data constraints** : `{}`

## Example Request

```json
GET /tickets
Content-Type: application/json
```

## Success Responses

**Condition** : User can not see any Tickets.

**Code** : `200 OK`

**Content** : `{[]}`

### OR

**Condition** : User can see one or more Tickets.

**Code** : `200 OK`

**Content** : In this example, the User can see four Tickets:

```json
[
  {
    "id": 1,
    "barcode": "1728287847109",
    "usedAt": null,
    "price": 0.0,
    "ticketType": null,
    "saleId": 1
  },
  {
    "id": 2,
    "barcode": "1728287847226",
    "usedAt": null,
    "price": 0.0,
    "ticketType": null,
    "saleId": 1
  },
  {
    "id": 3,
    "barcode": "1728287847228",
    "usedAt": null,
    "price": 0.0,
    "ticketType": null,
    "saleId": 2
  },
  {
    "id": 4,
    "barcode": "1728287847230",
    "usedAt": null,
    "price": 0.0,
    "ticketType": null,
    "saleId": null
  }
]
```

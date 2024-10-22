# Show Accessible Tickets

Show all `Tickets`.

**URL** : `/api/tickets`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

## Example Request

```json
GET /tickets
```

## Success Responses

**Condition** : The request is successful and there are no `Ticket` objects in the system.

**Code** : `200 OK`

**Content** :

```json
[]
```

### OR

**Condition** : The request is successful and there are `Ticket` objects in the system.

**Code** : `200 OK`

**Content** : In this example, the User can see four `Ticket` objects:

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

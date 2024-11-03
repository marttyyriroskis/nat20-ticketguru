# Get Ticket

Allow getting `Ticket` details of the given `barcode`.

**URL** : `/api/tickets/barcode/{barcode}`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_TICKETS`

**Path Parameters** :

| Parameter | Type | Description                  |
| --------- | ---- | ---------------------------- |
| `barcode` | Long | Unique barcode of the ticket |

#### Example Request

```json
GET /api/tickets/barcode/1728287847109
```

## Success Responses

**Condition** : Provided ticket `barcode` is valid.

**Code** : `200 OK`

**Content example** : Returns the `Ticket` object of the given `barcode`.

```json
{
  "id": 1,
  "barcode": "1728287847109",
  "usedAt": null,
  "price": 0.0,
  "ticketType": null,
  "saleId": 1
}
```

## Error Response

**Condition**: If the ticket with the specified `barcode` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Ticket not found"
}
```

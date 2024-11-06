# Get Ticket

Allow getting `Ticket` details of the given `id`.

**URL** : `/api/tickets/{id}`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_TICKETS`

**Path Parameters** :

| Parameter | Type | Description                             |
| --------- | ---- | --------------------------------------- |
| `id`      | Long | Unique identifier for the ticket to get |

#### Example Request

```json
GET /api/tickets/1
```

## Success Responses

**Condition** : Provided ticket `id` is valid.

**Code** : `200 OK`

**Content example** : Returns the `Ticket` object of the given `id`.

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

**Condition**: If the ticket with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Ticket not found"
}
```

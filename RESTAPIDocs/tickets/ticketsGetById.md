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
GET /api/tickets/2
```

## Success Responses

**Condition** : Provided ticket `id` is valid.

**Code** : `200 OK`

**Content example** : Returns the `Ticket` object of the given `id`.

```json
{
  "id": 2,
  "barcode": "4d0032ce-4eb7-4626-b5c9-f7b18eaa4cda1732962836327",
  "usedAt": null,
  "price": 20,
  "deletedAt": null,
  "ticketTypeId": 2,
  "saleId": 1,
  "ticketType": {
    "id": 2,
    "name": "student",
    "retailPrice": 14.99,
    "totalAvailable": null,
    "eventId": 1
  },
  "event": {
    "id": 1,
    "name": "Death metal karaoke",
    "description": "Öriöriöriöriörirprir!!!!!",
    "totalTickets": 10,
    "beginsAt": "2055-10-12T12:00:00",
    "endsAt": "2055-10-12T12:00:00",
    "ticketSaleBegins": "2055-10-12T12:00:00",
    "venueId": 1
  },
  "venue": {
    "id": 1,
    "name": "Bunkkeri",
    "address": "Bunkkeritie 1",
    "zipcode": "00100"
  }
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

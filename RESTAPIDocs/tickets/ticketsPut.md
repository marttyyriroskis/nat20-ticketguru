# Update Ticket

Allow updating `Ticket` details of the given `id`.

**URL** : `/api/tickets/{id}`

**Method** : `PUT`

**Auth required** : YES

**Permissions required** : `EDIT_TICKETS`

**Path Parameters** :

| Parameter | Type | Description                                |
| --------- | ---- | ------------------------------------------ |
| `id`      | Long | Unique identifier for the ticket to update |

**Data constraints** :

Provide all required parameters in the response body for the `Ticket` to be updated.

| Field            | Type                     | Required | Description                                 |
| ---------------- | ------------------------ | -------- | ------------------------------------------- |
| `usedAt`         | String (ISO 8601 format) | No       | LocalDateTime for when the ticket was used. |
| `price`          | Double                   | Yes      | The selling price of the ticket.            |
| `ticket_type_id` | Long                     | Yes      | The ticket type id.                         |
| `sale_id`        | Long                     | Yes      | The sale id.                                |

#### Example Request

```json
PUT /api/tickets/5
```

All required fields must be sent. `price` must not be null.

```json
{
  "usedAt": null,
  "price": 300.5,
  "ticketTypeId": 1,
  "saleId": 1
}
```

## Success Responses

**Condition** : Data provided is valid.

**Code** : `200 OK`

**Content example** : Returns the updated `Ticket` object.

```json
{
  "id": 5,
  "barcode": "e2caab28-c442-467f-9b56-1e2514a6174c1732968365929",
  "usedAt": null,
  "price": 300.5,
  "deletedAt": null,
  "ticketTypeId": 1,
  "saleId": 1,
  "ticketType": {
    "id": 1,
    "name": "adult",
    "retailPrice": 29.99,
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

**Condition** : If the ticket with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Ticket not found"
}
```

**Condition** : If the provided `TicketType` or `Sale` do not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not found",
  "message": "Ticket Type not found"
}
```

**OR** :

```json
{
  "status": 404,
  "error": "Not found",
  "message": "Sale not found"
}
```

**Condition** : If required fields are missing.

**Code** : `400 BAD REQUEST`

**Content example**

```json
{
  "price": "Price must be positive or zero",
  "ticketTypeId": "TicketType ID must not be null"
}
```

**Condition**: If any of the given fields do not meet the validation constraints.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "price": "Price must be positive or zero"
}
```

#### Notes

- Ensure that all date and time fields are in ISO 8601 format, and set into the future or present.

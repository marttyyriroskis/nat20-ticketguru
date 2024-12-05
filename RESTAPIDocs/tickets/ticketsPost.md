# Add a new Ticket

Create a new `Ticket` entity.

**URL** : `/api/tickets`

**Method** : `POST`

**Auth required** : YES

**Permissions required** : `CREATE_TICKETS`

**Data constraints** :

Provide all required parameters for the `Ticket` to be created.

| Field            | Type                     | Required | Description                                    |
| ---------------- | ------------------------ | -------- | ---------------------------------------------- |
| `usedAt`         | String (ISO 8601 format) | No       | LocalDateTime for when the ticket was used.    |
| `price`          | Double                   | Yes      | The selling price of the ticket.               |
| `deletedAt`      | String (ISO 8601 format) | No       | LocalDateTime for when the ticket was deleted. |
| `ticket_type_id` | Long                     | Yes      | The ticket type id.                            |
| `sale_id`        | Long                     | Yes      | The sale id.                                   |

#### Example Request

All required fields must be sent. `price` must not be null.

```json
POST /tickets

{
    "usedAt": null,
    "price": 20.5,
    "ticketTypeId": 1,
    "saleId": 1
}
```

## Success Responses

**Condition** : Data provided is valid. `ticket_type_id` and `sale_id` are valid.

**Code** : `201 CREATED`

**Content example**

```json
{
  "id": 5,
  "barcode": "c7a9f563-0e88-4775-8f28-4f51d614a09c1732967649688",
  "usedAt": null,
  "price": 20.5,
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

- Ensure that all date and time fields are in ISO 8601 format.

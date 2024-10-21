# Add a new TIcket Type

Create a new `TicketType` entity

**URL** : `/api/tickettype`

**Method** : `POST`

**Auth required** : NO

**Permissions required** : None

**Data constraints** :

Provide all required parameters for the `TicketType`to be created.

| Field            | Type            | Required | Description                                                                    |
| ---------------- | --------------- | -------- | ------------------------------------------------------------------------------ |
| `name`           | String          | Yes      | The name of the ticket type (1-100 char).                                      |
| `retailPrice`    | Double          | Yes      | The price of the ticket type.                                                  |
| `totalAvailable` | Integer OR null | Yes      | The total amount of tickets available of this ticket type OR null if unlimited |
| `eventId`        | Object          | Yes      | An object representing the event. Must contain the event `id` (Long).          |

**Data example** All required fields must be sent. All fields except `totalAvailable` must not be null.

```json
{
  "name": "pensioner",
  "retailPrice": 14.99,
  "totalAvailable": null,
  "event": { "id": 1 }
}
```

## Success Responses

**Condition** : Data provided is valid and all fields except `totalAvailable` must not be null. `Event` `id` is valid.

**Code** : `200 OK`

**Content example**

```json
{
  "id": 4,
  "name": "pensioner",
  "retailPrice": 14.99,
  "totalAvailable": null,
  "event": {
    "id": 1,
    "name": "Death metal karaoke",
    "total_tickets": 10,
    "begins_at": "2055-10-12T12:00:00",
    "ends_at": "2055-10-12T12:00:00",
    "ticket_sale_begins": "2055-10-12T12:00:00",
    "description": "Öriöriöriöriörirprir!!!!!",
    "venue": {
      "id": 1,
      "name": "Bunkkeri",
      "address": "Bunkkeritie 1",
      "zipcode": {
        "zipcode": "00100",
        "city": "Helsinki"
      }
    }
  }
}
```

## Error Response

**Condition** : If the provided `Event` does not exist.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Event does not exist!"
}
```

**Condition** : If required fields are missing or null.

**Code** : `400 BAD REQUEST`

**Content example**

```json
{
  "name": "Name must not be empty",
  "price": "Price must not be null",
  "event": "Event must not be null"
}
```

**Condition**: If any of the given fields do not meet the validation constraints.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "name": "Name must be between 1 and 100 characters long",
  "retailPrice": "Price must be positive",
  "totalAvailable": "Total available must be positive or null"
}
```

#### Notes

- To set the amount of available tickets to unlimited, set the `ticketsAvailable` field to `null`.

# Update Ticket Type

Allow updating `TIcket type` details of the given `id`.

**URL** : `/api/tickettypes/{id}`

**Method** : `PUT`

**Auth required** : NO

**Permissions required** : None

**Path Parameters** :

| Parameter | Type | Description                               |
| --------- | ---- | ----------------------------------------- |
| `id`      | Long | Unique identifier for the event to update |

**Data constraints** :

The request body should be a JSON object representing the `TicketType`. It may include the following fields:

| Field            | Type            | Required | Description                                                                    |
| ---------------- | --------------- | -------- | ------------------------------------------------------------------------------ |
| `name`           | String          | Yes      | The name of the ticket type (1-100 char).                                      |
| `retailPrice`    | Double          | Yes      | The price of the ticket type.                                                  |
| `totalAvailable` | Integer OR null | Yes      | The total amount of tickets available of this ticket type OR null if unlimited |
| `eventId`        | Object          | Yes      | An object representing the event. Must contain the event `id` (Long).          |

#### Example Request

```json
PUT api/tickettypes/1
Content-Type: application/json

{
  "name": "adult",
  "retailPrice": 39.99,
  "totalAvailable": null,
  "event": { "id": 1}
}
```

## Success Responses

**Condition** : Data provided is valid.

**Code** : `200 OK`

**Content example** : Returns the updated `TicketType` object, with the `Event` and `Venue` details.

```json
{
  "id": 1,
  "name": "adult",
  "retailPrice": 39.99,
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

**Condition** : If the provided `TicketType` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Ticket type not found"
}
```

**Condition**: If the event with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Event not found"
}
```

**Condition** : If required fields are missing or null.

**Code** : `400 BAD REQUEST`

**Content example**

```json
{
  "name": "Name must not be empty",
  "retailPrice": "Price must not be null",
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

- This endpoint does not allow the creation of a new venue. Only existing venues can be assigned to the event.

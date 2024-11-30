# Add a new Ticket Type

Create a new `TicketType` entity.

**URL** : `/api/tickettypes`

**Method** : `POST`

**Auth required** : YES

**Permissions required** : `CREATE_TICKET_TYPES`

**Data constraints** :

Provide all required parameters for the `TicketType` to be created.

| Field            | Type            | Required | Description                                                                    |
| ---------------- | --------------- | -------- | ------------------------------------------------------------------------------ |
| `name`           | String          | Yes      | The name of the ticket type (1-100 char).                                      |
| `retailPrice`    | Double          | Yes      | The price of the ticket type.                                                  |
| `totalAvailable` | Integer OR null | Yes      | The total amount of tickets available of this ticket type OR null if unlimited |
| `eventId`        | Long            | Yes      | A long representing the event. Must contain the event `id` (Long).             |

#### Example Request

```json
POST /api/tickettypes
```

All required fields must be sent. `name`, `retailPrice` and `eventId`must not be null.

```json
{
  "name": "adult",
  "retailPrice": 29.99,
  "totalTickets": null,
  "availableTickets": 9996,
  "eventId": 1
}
```

## Success Responses

**Condition** : Data provided is valid. `eventId` is valid.

**Code** : `201 CREATED`

**Content example**

```json
{
  "id": 5,
  "name": "adult",
  "retailPrice": 29.99,
  "totalAvailable": null,
  "eventId": 1
}
```

## Error Response

**Condition** : If the provided `Event` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Event does not exist!"
}
```

**Condition** : If required fields are missing or null.

**Code** : `400 BAD REQUEST`

**Content example**

```json
{
  "event": "Event must not be null"
}
```

**Condition**: If any of the given fields do not meet the validation constraints.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "name": "Ticket type name must be between 1 and 100 characters long",
  "retailPrice": "Ticket type price must be positive",
  "totalAvailable": "Total available must be positive or null"
}
```

#### Notes

- To set the amount of available tickets to unlimited, set the `totalAvailable` field to `null`.

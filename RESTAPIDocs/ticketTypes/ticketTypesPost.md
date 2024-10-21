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
| `eventId`        | Long        | Yes      | A long representing the event. Must contain the event `id` (Long).          |

**Data example** All required fields must be sent. All fields except `totalAvailable` must not be null.

```json
{
  "name": "pensioner",
  "retailPrice": 14.99,
  "totalAvailable": null,
  "eventId": 1
}
```

## Success Responses

**Condition** : Data provided is valid. `EventId` is valid.

**Code** : `201 CREATED`

**Content example**

```json
{
  "id": 5,
  "name": "pensioner",
  "retailPrice": 14.99,
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

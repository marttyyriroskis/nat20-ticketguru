# Update Event

Allow updating `Event` details of the given `id`.

**URL** : `/api/events/{id}`

**Method** : `PUT`

**Auth required** : YES

**Permissions required** : `EDIT_EVENTS`

**Path Parameters** :

| Parameter | Type | Description                               |
| --------- | ---- | ----------------------------------------- |
| `id`      | Long | Unique identifier for the event to update |

**Data constraints** :

Provide all required parameters in the response body for the `Event` to be updated.

| Field              | Type                     | Required | Description                                                      |
| ------------------ | ------------------------ | -------- | ---------------------------------------------------------------- |
| `name`             | String                   | Yes      | The name of the event (1-100 char).                              |
| `description`      | String                   | Yes      | A description of the event (1-500 char).                         |
| `totalTickets`     | Integer                  | Yes      | The total number of tickets available for the event.             |
| `beginsAt`         | String (ISO 8601 format) | Yes      | The start date and time of the event.                            |
| `endsAt`           | String (ISO 8601 format) | Yes      | The end date and time of the event.                              |
| `ticketSaleBegins` | String (ISO 8601 format) | No       | The date and time when ticket sales begin.                       |
| `venueId`          | Long                     | Yes      | A long representing the venue. Must contain the `venueId` (Long) |

#### Example Request

All required fields must be sent. `name`, `description`, `totalTickets`, `beginsAt` and `endsAt` must not be null.

```json
PUT /api/events/4

{
  "name": "Disney On Meth-Ice",
  "description": "Mikki-hiiret jäällä. Suih suih vaan!",
  "totalTickets": 10000,
  "beginsAt": "2025-09-29T09:18:26.535823",
  "endsAt": "2025-09-29T09:18:26.535823",
  "ticketSaleBegins": "2025-09-29T09:18:26.535823",
  "venueId": 1
}
```

## Success Responses

**Condition** : Data provided is valid.

**Code** : `200 OK`

**Content example** : Returns the updated `Event` object.

```json
{
  "id": 4,
  "name": "Disney On Meth-Ice",
  "description": "Mikki-hiiret jäällä. Suih suih vaan!",
  "totalTickets": 10000,
  "beginsAt": "2025-09-29T09:18:26.535823",
  "endsAt": "2025-09-29T09:18:26.535823",
  "ticketSaleBegins": "2025-09-29T09:18:26.535823",
  "venueId": 1
}
```

## Error Response

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

**Condition** : If the provided `Venue` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Venue not found"
}
```

**Condition** : If required fields are missing or null.

**Code** : `400 BAD REQUEST`

**Content example**

```json
{
  "name": "Event name cannot be null",
  "description": "Event description cannot be null",
  "beginsAt": "Event start date cannot be null",
  "endsAt": "Event end date cannot be null"
}
```

**Condition**: If any of the given fields do not meet the validation constraints.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "name": "Event name cannot be empty and must be between 1 and 100 characters long",
  "description": "Event escription cannot be empty and must be between 1 and 500 characters long",
  "totalTickets": "Amount of tickets must be positive",
  "beginsAt": "The event start date must be in the future",
  "endsAt": "The event end date must be in the future"
}
```

#### Notes

- To remove the association with a venue, set the `venueId` field to `null`.
- This endpoint does not allow the creation of a new venue. Only existing venues can be assigned to the event.
- Ensure that all date and time fields are in ISO 8601 format, and set into the future or present.

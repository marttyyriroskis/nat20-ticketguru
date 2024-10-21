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
| `eventId`        | Long            | Yes      | A long representing the event. Must contain the event `id` (Long).             |

#### Example Request

```json
PUT api/tickettypes/1
Content-Type: application/json

{
  "name": "adult",
  "retailPrice": 39.99,
  "totalAvailable": null,
  "eventId": 1
}
```

## Success Responses

**Condition** : Data provided is valid.

**Code** : `200 OK`

**Content example** : Returns the updated `TicketType` object.

```json
{
  "id": 5,
  "name": "adult",
  "retailPrice": 39.99,
  "totalAvailable": null,
  "eventId": 1
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
  "message": "Ticket type not found!"
}
```

**Condition**: If the event with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Event not found!"
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
  "name": "Name must be between 1 and 100 characters long",
  "retailPrice": "Price must be positive",
  "totalAvailable": "Total available must be positive or null"
}
```

#### Notes

- This endpoint does not allow the creation of a new venue. Only existing venues can be assigned to the event.

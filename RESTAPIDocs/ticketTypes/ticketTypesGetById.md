# Get Ticket Type

Allow getting `TicketType` details of the given `id`.

**URL** : `/api/tickettypes/{id}`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

**Path Parameters** :

| Parameter | Type | Description                            |
| --------- | ---- | -------------------------------------- |
| `id`      | Long | Unique identifier for the ticket type to get |

#### Example Request

```json
GET /api/tickettypes/1

```

## Success Responses

**Condition** : Provided event `id` is valid.

**Code** : `200 OK`

**Content example** : Returns the `TicketType` object of the given `id`.

```json
{
  "id": 1,
  "name": "adult",
  "retailPrice": 29.99,
  "totalAvailable": null,
  "eventId": 1
}
```

## Error Response

**Condition**: If the `TicketType` with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Ticket type not found"
}
```

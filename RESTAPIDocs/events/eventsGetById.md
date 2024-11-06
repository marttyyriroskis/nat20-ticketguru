# Get Event

Allow getting `Event` details of the given `id`.

**URL** : `/api/events/{id}`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_EVENTS`

**Path Parameters** :

| Parameter | Type | Description                            |
| --------- | ---- | -------------------------------------- |
| `id`      | Long | Unique identifier for the event to get |

#### Example Request

```json
GET /api/events/3
```

## Success Responses

**Condition** : Provided event `id` is valid.

**Code** : `200 OK`

**Content example** : Returns the `Event` object of the given `id`.

```json
{
  "id": 3,
  "name": "A Night at the Museum",
  "description": "Night-show at the National Museum",
  "total_tickets": 500,
  "begins_at": "2055-10-12T12:00:00",
  "ends_at": "2055-10-12T12:00:00",
  "ticket_sale_begins": "2055-10-12T12:00:00",
  "venueId": 1
}
```

## Error Response

**Condition**: If the `Event` with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Event not found"
}
```

# Delete Event

Allow deleting `Event` details of the given `id`.

**URL** : `/api/events/{id}`

**Method** : `DELETE`

**Auth required** : YES

**Permissions required** : `DELETE_EVENTS`

**Path Parameters** : `{id}`

| Parameter | Type | Description                               |
| --------- | ---- | ----------------------------------------- |
| `id`      | Long | Unique identifier for the event to delete |

## Example Request

```json
DELETE /api/events/2
```

## Success Responses

**Condition** : `id` provided belongs to an `Event` in the database.

**Code** : `204 NO CONTENT`

## Error Response

**Condition** : If the provided `Event` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not found",
  "message": "Event not found"
}
```

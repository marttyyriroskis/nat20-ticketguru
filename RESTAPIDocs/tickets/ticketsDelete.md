# Delete Ticket

Allow deleting `Ticket` details of the given `id`.

**URL** : `/api/tickets/{id}`

**Method** : `DELETE`

**Auth required** : YES

**Permissions required** : `DELETE_TICKETS`

**Path Parameters** : `{id}`

| Parameter | Type | Description                                |
| --------- | ---- | ------------------------------------------ |
| `id`      | Long | Unique identifier for the ticket to delete |

## Example Request

```json
DELETE /api/tickets/5
```

## Success Responses

**Condition** : `id` provided belongs to a `Ticket` in the database.

**Code** : `204 NO CONTENT`

## Error Response

**Condition** : If the provided `Ticket` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not found",
  "message": "Ticket not found"
}
```

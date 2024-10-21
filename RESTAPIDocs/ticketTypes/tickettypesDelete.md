# Delete Ticket Type

Allow deleting `TicketType` details of the given `id`.

**URL** : `/api/tickettypes/{id}`

**Method** : `DELETE`

**Auth required** : NO

**Permissions required** : None

**Path Parameters** : `{id}`

| Parameter | Type | Description                                     |
| --------- | ---- | ----------------------------------------------- |
| `id`      | Long | Unique identifier for the ticket type to delete |

## Example Request

```json
DELETE /api/tickettypes/2
```

## Success Responses

**Condition** : `id` provided belongs to a `TicketType` in the database

**Code** : `204 NO CONTENT`

## Error Response

**Condition** : If the provided `TicketType` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not found",
  "message": "Ticket type not found"
}
```

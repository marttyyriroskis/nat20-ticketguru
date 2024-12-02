# Delete Venue

Allow deleting `Venue` details of the given `id`.

**URL** : `/api/venues/{id}`

**Method** : `DELETE`

**Auth required** : YES

**Permissions required** : `DELETE_VENUES`

**Path Parameters** : `{id}`

| Parameter | Type | Description                               |
| --------- | ---- | ----------------------------------------- |
| `id`      | Long | Unique identifier for the venue to update |

## Example Request

```json
DELETE api/venues/4
```

## Success Responses

**Condition** : `id` provided belongs to a `Venue` in the database

**Code** : `204 NO CONTENT`

## Error Response

**Condition** : If the provided `Venue` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not found",
  "message": "Venue not found"
}
```

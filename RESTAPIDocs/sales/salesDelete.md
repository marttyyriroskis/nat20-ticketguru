# Delete Sale

Allow deleting `Sale` details of the given `id`. When a Sale is deleted, it's deletedAt field is set to the time of deletion. The entity stays in the database, but is not visible or available to REST requests.

**URL** : `/api/sales/{id}`

**Method** : `DELETE`

**Auth required** : YES

**Permissions required** : `DELETE_SALES`

**Path Parameters** : `{id}`

| Parameter | Type | Description                              |
| --------- | ---- | ---------------------------------------- |
| `id`      | Long | Unique identifier for the Sale to delete |

**Data constraints** : `{}`

## Example Request

```json
DELETE api/sales/3
Content-Type: application/json
```

## Success Response

**Condition** : `Id` provided belongs to a Sale in the database

**Code** : `204 NO CONTENT`

## Error Response

**Condition** : If the provided `Sale` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not found",
  "message": "Sale not found"
}
```

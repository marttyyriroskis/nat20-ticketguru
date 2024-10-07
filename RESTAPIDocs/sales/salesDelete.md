# Delete Sale

Allow deleting `Sale` details of the given `id`. When a Sale is deleted, the association between the sale and its tickets is also removed, but the ticket entities themselves remain in the database.

**URL** : `/api/sales/{id}`

**Method** : `DELETE`

**Auth required** : NO

**Permissions required** : None

**Path Parameters** : `{id}`

| Parameter | Type | Description                              |
| --------- | ---- | ---------------------------------------- |
| `id`      | Long | Unique identifier for the Sale to delete |

**Data constraints** : `{}`

## Example Request

```json
DELETE api/sales/2
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

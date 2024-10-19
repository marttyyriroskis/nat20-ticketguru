# Delete a Permission

Allow deleting a permission from the system.

**URL** : `/api/permissions/{id}`

**Method** : `DELETE`

**Auth required** : NO

**Permissions required** : None

### Path Parameters:

| Parameter | Type | Description                                    |
| --------- | ---- | ---------------------------------------------- |
| `id`      | Long | Unique identifier for the permission to delete |

### Example Request

```json
DELETE /api/permissions/1
```

## Success Responses

**Condition** : Permission is deleted successfully.

**Code** : `204 No Content`

## Error Response

**Condition** : If the permission with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Permission not found"
}
```

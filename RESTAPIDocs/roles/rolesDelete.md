# Delete a Role

Allow deleting a role from the system.

**URL** : `/api/roles/{id}`

**Method** : `DELETE`

**Auth required** : Yes

**Permissions required** : `DELETE_ROLES`

### Path Parameters:

| Parameter | Type | Description                              |
| --------- | ---- | ---------------------------------------- |
| `id`      | Long | Unique identifier for the role to delete |

### Example Request

```json
DELETE /api/roles/1
```

## Success Responses

**Condition** : Role is deleted successfully.

**Code** : `204 No Content`

## Error Response

**Condition** : If the role with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Role not found"
}
```

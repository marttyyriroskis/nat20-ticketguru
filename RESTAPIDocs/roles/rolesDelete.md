# Delete a Role

Allow deleting a role from the system.

**URL** : `/api/roles/{id}`

**Method** : `DELETE`

**Auth required** : YES

**Permissions required** : `DELETE_ROLES`

**Path Parameters** : `{id}`

| Parameter | Type | Description                              |
| --------- | ---- | ---------------------------------------- |
| `id`      | Long | Unique identifier for the role to delete |

### Example Request

```json
DELETE /api/roles/5
```

## Success Responses

**Condition** : Role is deleted successfully.

**Code** : `204 NO CONTENT`

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

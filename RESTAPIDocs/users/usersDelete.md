# Delete a User

Allow deleting a user from the system.

**URL** : `/api/users/{id}`

**Method** : `DELETE`

**Auth required** : YES

**Permissions required** : `DELETE_USERS`

### Path Parameters:

| Parameter | Type | Description                              |
| --------- | ---- | ---------------------------------------- |
| `id`      | Long | Unique identifier for the user to delete |

#### Example Request

```json
DELETE /api/users/1
```

## Success Responses

**Condition** : User is deleted successfully.

**Code** : `204 NO CONTENT`

---

## Error Response

**Condition** : If the user with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "User not found"
}
```

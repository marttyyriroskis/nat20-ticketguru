# Get Role by ID

Allow retrieving a role by its unique identifier.

**URL** : `/api/roles/{id}`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : `VIEW_ROLES`

### Path Parameters:

| Parameter | Type | Description                           |
| --------- | ---- | ------------------------------------- |
| `id`      | Long | Unique identifier for the role to get |

### Example Request

```json
GET /api/roles/1
Accept: application/json
```

## Success Responses

**Condition** : Provided role `id` is valid.

**Code** : `200 OK`

**Content example** : Returns the `Role` object of the given `id`.

```json
{
  "title": "TICKET_INSPECTOR",
  "permissions": ["USE_TICKETS", "VIEW_TICKETS"],
  "userIds": []
}
```

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

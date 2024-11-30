# Add a New Role

Allow adding a new role to the system.

**URL** : `/api/roles`

**Method** : `POST`

**Auth required** : Yes

**Permissions required** : `CREATE_ROLES`

### Request Body:

| Field   | Type   | Description           |
| ------- | ------ | --------------------- |
| `title` | String | The title of the role |

### Example Request

```json
POST /api/roles
Content-Type: application/json

{
  "title": "admin"
}
```

## Success Responses

**Condition** : Role is created successfully.

**Code** : `201 Created`

**Content example** : Returns the created `Role` object.

```json
{
  "title": "admin",
  "permissions": [],
  "userIds": []
}
```

## Error Response

**Condition** : If the role title is already in the database

**Code** : `409 CONFLICT`

**Content example** :

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Role already exists"
}
```

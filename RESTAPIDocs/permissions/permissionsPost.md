# Add a New Permission

Allow adding a new permission to the system.

**URL** : `/api/permissions`

**Method** : `POST`

**Auth required** : NO

**Permissions required** : None

### Request Body:

| Field   | Type   | Description                 |
| ------- | ------ | --------------------------- |
| `title` | String | The title of the permission |

### Example Request

```json
POST /api/permissions
Content-Type: application/json

{
  "title": "execute"
}
```

## Success Responses

**Condition** : Permission is created successfully.

**Code** : `201 Created`

**Content example** : Returns the created `Permission` object.

```json
{
  "title": "execute",
  "roleIds": []
}
```

## Error Responses

**Condition** : If the permission title already exists.

**Code** : `409 CONFLICT`

**Content example** :

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Permission already exists"
}
```

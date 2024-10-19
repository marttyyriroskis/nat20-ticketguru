# Get All Permissions

Allow retrieving a list of all permissions.

**URL** : `/api/permissions`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

### Example Request

```json
GET /api/permissions
Accept: application/json
```

## Success Responses

**Condition** : The request is successful, and there are permissions in the system.

**Code** : `200 OK`

**Content example** : Returns a list of `Permission` objects.

```json
[
  {
    "title": "read",
    "roleIds": []
  },
  {
    "title": "write",
    "roleIds": []
  }
]
```

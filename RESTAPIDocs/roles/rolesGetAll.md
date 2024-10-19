# Get All Roles

Allow retrieving a list of all roles.

**URL** : `/api/roles`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

### Example Request

```json
GET /api/roles
Accept: application/json
```

## Success Responses

**Condition** : The request is successful, and there are roles in the system.

**Code** : `200 OK`

**Content example** : Returns a list of `Role` objects.

```json
[
  {
    "title": "cashier",
    "permissionIds": [],
    "userIds": [1]
  },
  {
    "title": "event organizer",
    "permissionIds": [],
    "userIds": [2]
  }
]
```

## Error Responses

**Condition** : If there are no roles available.

**Code** : `204 NO CONTENT`

**Content example** :

```json
{
  "status": 204,
  "error": "No content",
  "message": "No roles available"
}
```

# Get All Roles

Allow retrieving a list of all roles.

**URL** : `/api/roles`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_ROLES`

### Example Request

```json
GET /api/roles
```

## Success Responses

**Condition** : The request is successful and there are no `Event` objects in the system.

**Code** : `200 OK`

**Content** :

```json
[]
```

### OR

**Condition** : The request is successful, and there are roles in the system.

**Code** : `200 OK`

**Content example** : Returns a list of `Role` objects.

```json
[
  {
    "title": "TICKET_INSPECTOR",
    "permissions": ["USE_TICKETS", "VIEW_TICKETS"],
    "userIds": []
  },
  {
    "title": "SALESPERSON",
    "permissions": [
      "VIEW_VENUES",
      "USE_TICKETS",
      "VIEW_EVENTS",
      "VIEW_TICKET_TYPES",
      "VIEW_SALES",
      "CREATE_SALES",
      "VIEW_TICKETS",
      "EDIT_SALES",
      "CONFIRM_SALES",
      "DELETE_TICKETS"
    ],
    "userIds": [1]
  },
  {
    "title": "COORDINATOR",
    "permissions": [
      "VIEW_VENUES",
      "EDIT_VENUES",
      "VIEW_EVENTS",
      "VIEW_TICKET_TYPES",
      "DELETE_TICKET_TYPES",
      "CREATE_EVENTS",
      "EDIT_TICKET_TYPES",
      "CREATE_VENUES",
      "CREATE_TICKET_TYPES",
      "EDIT_EVENTS",
      "DELETE_VENUES"
    ],
    "userIds": [3]
  },
  {
    "title": "ADMIN",
    "permissions": [
      "VIEW_VENUES",
      "USE_TICKETS",
      "CREATE_VENUES",
      "VIEW_ROLES",
      "VIEW_TICKETS",
      "CREATE_USERS",
      "DELETE_ROLES",
      "EDIT_TICKET_TYPES",
      "CREATE_TICKETS",
      "CREATE_TICKET_TYPES",
      "REVOKE_PERMISSIONS",
      "DELETE_TICKETS",
      "DELETE_VENUES",
      "DELETE_EVENTS",
      "EDIT_VENUES",
      "GRANT_PERMISSIONS",
      "DELETE_TICKET_TYPES",
      "DELETE_SALES",
      "EDIT_USERS",
      "EDIT_EVENTS",
      "CONFIRM_SALES",
      "DELETE_USERS",
      "VIEW_EVENTS",
      "VIEW_TICKET_TYPES",
      "CREATE_ROLES",
      "CREATE_EVENTS",
      "VIEW_USERS",
      "VIEW_SALES",
      "EDIT_TICKETS",
      "CREATE_SALES",
      "EDIT_SALES",
      "EDIT_ROLES"
    ],
    "userIds": [2]
  }
]
```

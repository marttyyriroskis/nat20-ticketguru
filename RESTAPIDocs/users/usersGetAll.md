# Get Users

Allow retrieving a list of all users.

**URL** : `/api/users`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_USERS`

#### Example Request

```json
GET /api/users
Accept: application/json
```

## Success Responses

**Condition** : The request is successful, and there are users in the system.

**Code** : `200 OK`

**Content example** : Returns a list of `User` objects, each containing the user's details, including the associated `Role`.

```json
[
  {
    "id": 1,
    "email": "salesperson@test.com",
    "firstName": "salesperson1",
    "lastName": "Cashier",
    "role": {
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
    }
  },
  {
    "id": 2,
    "email": "admin@test.com",
    "firstName": "Admin1",
    "lastName": "Site Admin",
    "role": {
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
  },
  {
    "id": 3,
    "email": "coordinator@test.com",
    "firstName": "Coordinator1",
    "lastName": "Event Coordinator",
    "role": {
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
    }
  }
]
```

## Error Responses

**Condition** : If there are no users available.

**Code** : `204 NO CONTENT`

**Content example** :

```json
{
  "status": 204,
  "error": "No content",
  "message": "No users available"
}
```

# Add a New User

Allow adding a new user to the system.

**URL** : `/api/users`

**Method** : `POST`

**Auth required** : YES

**Permissions required** : `CREATE_USERS`

### Request Body:

| Field            | Type   | Description                                |
| ---------------- | ------ | ------------------------------------------ |
| `email`          | String | The user's email address (must be unique)  |
| `firstName`      | String | The user's first name                      |
| `lastName`       | String | The user's last name                       |
| `hashedPassword` | String | The user's password (will be hashed later) |
| `roleId`         | Long   | The role id to be assigned for the user    |

#### Example Request

```json
POST /api/users

{
  "email": "tes11t13311@test.com",
  "firstName": "User1",
  "lastName": "Cashier",
  "hashedPassword": "password",
  "roleId" : 1
}
```

## Success Responses

**Condition** : User is created successfully.

**Code** : `201 CREATED`

**Content example** : Returns the created `User` object.

```json
{
  "id": 4,
  "email": "tes11t13311@test.com",
  "firstName": "User1",
  "lastName": "Cashier",
  "role": {
    "title": "user",
    "permissions": ["USE_TICKETS", "VIEW_TICKETS"],
    "userIds": [4]
  }
}
```

## Error Response

**Condition** : If the email is already in the database

**Code** : `409 CONFLICT`

**Content example** :

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Email is already in use."
}
```

# Add a New User

Allow adding a new user to the system.

**URL** : `/api/users`

**Method** : `POST`

**Auth required** : NO

**Permissions required** : None

### Request Body:

| Field            | Type   | Description                                |
| ---------------- | ------ | ------------------------------------------ |
| `email`          | String | The user's email address (must be unique)  |
| `firstName`      | String | The user's first name                      |
| `lastName`       | String | The user's last name                       |
| `hashedPassword` | String | The user's password (will be hashed later) |

#### Example Request

```json
POST /api/users
Content-Type: application/json

{
  "email": "newuser@test.com",
  "firstName": "New",
  "lastName": "User",
  "hashedPassword": "password",
  "role" : {
    "id": 1
  }
}
```

## Success Responses

**Condition** : User is created successfully.

**Code** : `201 CREATED`

**Content example** : Returns the created `User` object.

```json
{
  "id": 3,
  "email": "newuser@test.com",
  "firstName": "New",
  "lastName": "User",
  "role": {
    "id": 1,
    "title": "cashier"
  }
}
```

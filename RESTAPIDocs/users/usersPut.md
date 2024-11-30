## Update a User

Allow updating the details of an existing user.

**URL** : `/api/users/{id}`

**Method** : `PUT`

**Auth required** : YES

**Permissions required** : `EDIT_USERS`

### Path Parameters:

| Parameter | Type | Description                              |
| --------- | ---- | ---------------------------------------- |
| `id`      | Long | Unique identifier for the user to update |

### Request Body:

| Field       | Type   | Description                                                    |
| ----------- | ------ | -------------------------------------------------------------- |
| `id`        | Long   | The unique identifier of the user (to be included for clarity) |
| `email`     | String | The user's email address (must be unique)                      |
| `firstName` | String | The user's first name                                          |
| `lastName`  | String | The user's last name                                           |
| `role`      | Object | An object representing the user's role, containing `id`.       |

#### Example Request

```json
PUT /api/users/4
Content-Type: application/json

{
  "email": "updated_user@test.com",
  "firstName": "UpdatedUser1",
  "lastName": "Cashier",
  "role": null
}
```

## Success Responses

**Condition** : User is updated successfully.

**Code** : `200 OK`

**Content example** : Returns the updated `User` object, including the associated `Role`.

```json
{
  "id": 4,
  "email": "updated_user@test.com",
  "firstName": "UpdatedUser1",
  "lastName": "Cashier",
  "role": {
    "title": "user",
    "permissions": ["USE_TICKETS", "VIEW_TICKETS"],
    "userIds": [4]
  }
}
```

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

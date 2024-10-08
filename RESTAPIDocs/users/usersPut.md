## Update a User

Allow updating the details of an existing user.

**URL** : `/api/users/{id}`

**Method** : `PUT`

**Auth required** : YES

**Permissions required** : None

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
PUT /api/users/1
Content-Type: application/json

{
  "id": 1,
  "email": "updated_user@test.com",
  "firstName": "UpdatedUser1",
  "lastName": "Cashier",
  "role": {
    "id": 1
  }
}
```

## Success Responses

**Condition** : User is updated successfully.

**Code** : `200 OK`

**Content example** : Returns the updated `User` object, including the associated `Role`.

```json
{
  "id": 1,
  "email": "updated_user@test.com",
  "firstName": "UpdatedUser1",
  "lastName": "Cashier",
  "role": {
    "id": 1,
    "title": "cashier"
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

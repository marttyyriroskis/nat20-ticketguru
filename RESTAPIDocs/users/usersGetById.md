# Get User by ID

Allow retrieving a user's details by their unique identifier.

**URL** : `/api/users/{id}`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : Admin (Unless authenticated user equals the requested user)

### Path Parameters:

| Parameter | Type | Description                           |
| --------- | ---- | ------------------------------------- |
| `id`      | Long | Unique identifier for the user to get |

#### Example Request

```json
GET /api/users/1
Accept: application/json
```

## Success Responses

**Condition** : Provided user `id` is valid.

**Code** : `200 OK`

**Content example** : Returns the `User` object of the given `id`.

```json
{
  "id": 1,
  "email": "test1@test.com",
  "firstName": "User1",
  "lastName": "Cashier",
  "role": {
    "title": "cashier",
    "permissionIds": [1, 2],
    "userIds": [1]
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

**Condition**: If the user is not an admin, nor the requested user.

**Code** : `403 FORBIDDEN`

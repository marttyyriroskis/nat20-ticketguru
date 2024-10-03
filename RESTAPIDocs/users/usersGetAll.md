# Get Users

Allow retrieving a list of all users.

**URL** : `/api/users`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : Admin

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
    "email": "test1@test.com",
    "firstName": "User1",
    "lastName": "Cashier",
    "role": {
      "id": 1,
      "title": "cashier"
    }
  },
  {
    "id": 2,
    "email": "test2@test.com",
    "firstName": "User2",
    "lastName": "Event Organizer",
    "role": {
      "id": 2,
      "title": "event organizer"
    }
  }
]
```

## Error Responses

**Condition** : If there are no users available or the server cannot process the request.

**Code** : `500 INTERNAL SERVER ERROR`

**Content example** :

```json
{
  "status": 500,
  "error": "Internal Server Error",
  "message": "An error occurred while retrieving users."
}
```

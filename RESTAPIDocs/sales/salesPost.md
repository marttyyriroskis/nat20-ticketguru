# Add a new Sale

Create a new `Sale` entity

**URL** : `/api/sales`

**Method** : `POST`

**Auth required** : YES

**Permissions required** : `CREATE_SALES`

**Data constraints** :

Provide all required parameters for the `Sale`to be created.

| Field     | Type                     | Required | Description                                                                                 |
| --------- | ------------------------ | -------- | ------------------------------------------------------------------------------------------- |
| `paidAt`  | String (ISO 8601 format) | No       | The date and time when sale event happened. If null, set to current time.                   |
| `user`    | Object                   | Yes      | An object representing the user. It must contain the user `id` (Long)                       |
| `tickets` | List of objects          | Yes      | A list containing the sold ticket objects. Each object must contain the ticket `id` (Long). |

**Data example** All required fields must be sent. `user` and `tickets` must not be null. `Tickets` must contain at least one valid `id`.

```json
{
  "paidAt": "2024-10-04T12:58:29.580761",
  "userId": 1,
  "ticketIds": [4]
}
```

## Success Response

**Condition** : Data provided is valid and `user` and `tickets` fields are not null, and `tickets` contains at least one valid `id`.

**Code** : `200 OK`

**Content example**

```json
POST api/sales
Content-Type: application/json

{
  "id": 3,
  "paidAt": "2024-10-04T12:58:29.580761",
  "userId": 1,
  "ticketIds": [
    4
  ]
}
```

## Error Responses

**Condition** : If no user object in request body.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "No user in request body"
}
```

**Condition** : User not found with given `id`.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "User not found"
}
```

**Condition** : If no valid tickets in request body.

**Code** : `400 BAD REQUEST`

**Content example**

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "No valid tickets in Sale"
}
```

#### Notes

- Ensure that all date and time fields are in ISO 8601 format.

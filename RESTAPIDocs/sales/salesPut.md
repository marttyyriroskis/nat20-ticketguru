# Update Sale

Allow updating `Sale` details of the given `id`.

**URL** : `/api/sales/{id}`

**Method** : `PUT`

**Auth required** : YES

**Permissions required** : `EDIT_SALES`

**Path Parameters** :

| Parameter | Type | Description                              |
| --------- | ---- | ---------------------------------------- |
| `id`      | Long | Unique identifier for the Sale to update |

**Data constraints** :

Provide all required fields for the `Sale`to be edited.

| Field     | Type                     | Required | Description                                                                                 |
| --------- | ------------------------ | -------- | ------------------------------------------------------------------------------------------- |
| `paidAt`  | String (ISO 8601 format) | No       | The date and time when sale event happened. If null, set to current time.                   |
| `user`    | Object                   | Yes      | An object representing the user. It must contain the user `id` (Long)                       |
| `tickets` | List of objects          | Yes      | A list containing the sold ticket objects. Each object must contain the ticket `id` (Long). |

**Data example** All required fields must be sent. `Tickets` must not be null and must contain at least one valid `id`.

```json
PUT api/sales/3
Content-Type: application/json

{
    "paidAt": "2024-11-30T11:03:32.862709",
    "userId": 1,
    "ticketIds": [
        1,
        2,
        3
    ]
}
```

## Success Response

**Condition** : Data provided is valid. `Tickets` is not null, and contains at least one valid `id`.

**Code** : `200 OK`

**Content example**

```json
{
  "id": 3,
  "paidAt": "2024-11-30T11:03:32.862709",
  "userId": 1,
  "ticketIds": [2, 3]
}
```

## Error Responses

**Condition** : If no `userId` in request body.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "userId": "userId cannot be null"
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
  "message": "Invalid ticket"
}
```

#### Notes

- Ensure that all date and time fields are in ISO 8601 format.

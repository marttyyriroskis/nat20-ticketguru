# Update Sale

Allow updating `Sale` details of the given `id`.

**URL** : `/api/sales/{id}?userId={id}`

**Method** : `PUT`

**Auth required** : NO

**Permissions required** : None

**Query Parameters**

| Parameter | Type | Description                    | Required | Default |
| --------- | ---- | ------------------------------ | -------- | ------- |
| `userId`  | Long | Unique identifier for the User | Yes      | None    |

**Data constraints** :

Provide all required fields for the `Sale`to be edited.

| Field     | Type                     | Required | Description                                                                                 |
| --------- | ------------------------ | -------- | ------------------------------------------------------------------------------------------- |
| `paidAt`  | String (ISO 8601 format) | No       | The date and time when sale event happened. If null, set to current time.                   |
| `tickets` | List of objects          | Yes      | A list containing the sold ticket objects. Each object must contain the ticket `id` (Long). |

**Data example** All required fields must be sent. Query parameter for `user` must be set to an existing User.`Tickets` must not be null and must contain at least one valid `id`.

```json
PUT api/sales/1?userId=2
Content-Type: application/json

{
  "paidAt": "2055-10-06T18:38:31.578603",
  "tickets": [
    {
      "id": 4
    }
  ]
}
```

## Success Response

**Condition** : Data provided is valid and `userId` set in query parameter exists. `Tickets` is not null, and contains at least one valid `id`.

**Code** : `200 OK`

**Content example**

```json
{
  "id": 1,
  "paidAt": "2055-10-06T18:38:31.578603",
  "userId": 2,
  "tickets": [
    {
      "id": 4,
      "barcode": "1728306040347",
      "usedAt": null,
      "price": 0.0,
      "ticketTypeId": null,
      "saleId": 1
    }
  ]
}
```

## Error Responses

**Condition** : If no `userId` in query parameter.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Required parameter 'userId' is not present."
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

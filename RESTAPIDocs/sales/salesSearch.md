# Search Sales

Search sales by start/end dates or datetimes, or by `userId`.

**URL** : `/api/sales/search`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_SALES`

**Query Parameters** :

| Parameter | Type   | Description                                                                     |
| --------- | ------ | ------------------------------------------------------------------------------- |
| `start`   | String | Starting Date or DateTime (ISO 8601) for when the sale was made                 |
| `end`     | String | Ending Date or DateTime (ISO 8601) for when the sale was made (NOTE: exclusive) |
| `userId`  | Long   | Unique identifier for the user who made the sale                                |

**Data constraints** : `{}`

## Example Request with UserId

```json
GET api/sales/search?userId=1
Content-Type: application/json
```

## Success Responses

**Condition** : User sees an empty list. There are no Sales to show with the given parameters.

**Code** : `200 OK`

**Content** :

```json
{[]}
```

### OR

**Condition** : There are Sales to show with the given parameters.

**Code** : `200 OK`

**Content** : In this example, the User can see one Sale:

```json
[
  {
    "id": 1,
    "paidAt": "2024-11-03T20:38:35.310675",
    "userId": 1,
    "tickets": [
      {
        "id": 2,
        "barcode": "a3aa29b4-93c6-44dd-8505-73ca44638ef41730659115363",
        "usedAt": null,
        "price": 20.0,
        "deletedAt": null
      },
      {
        "id": 1,
        "barcode": "60e04c3a-84fc-4b38-8109-65fd40407ad81730659115347",
        "usedAt": null,
        "price": 10.0,
        "deletedAt": "2024-03-12T09:00:00"
      }
    ],
    "deletedAt": null
  }
]
```

## Example Request with Start Date

```json
GET api/sales/search?start=2024-11-05
Content-Type: application/json
```

## Success Responses

**Condition** : There are sales to show with the given parameters.

**Code** : `200 OK`

**Content** :

```json
[
  {
    "id": 5,
    "paidAt": "2024-11-05T11:37:36.691676",
    "userId": 1,
    "tickets": [
      {
        "id": 7,
        "barcode": "3283224f-55e8-4ac9-a9ec-772237f98f651730799456791",
        "usedAt": null,
        "price": 29.99,
        "deletedAt": null
      },
      {
        "id": 8,
        "barcode": "1a0489ae-57ca-4076-8b3e-9fbd7a94dd681730799456791",
        "usedAt": null,
        "price": 29.99,
        "deletedAt": null
      }
    ],
    "deletedAt": null
  },
  {
    "id": 6,
    "paidAt": "2024-11-05T11:37:38.642117",
    "userId": 1,
    "tickets": [
      {
        "id": 9,
        "barcode": "d70e94b6-74bc-4920-b6c4-ee97e055de9d1730799458654",
        "usedAt": null,
        "price": 29.99,
        "deletedAt": null
      },
      {
        "id": 10,
        "barcode": "c11e9776-82f2-4f03-9dd7-1b6d9414f0621730799458654",
        "usedAt": null,
        "price": 29.99,
        "deletedAt": null
      },
      {
        "id": 11,
        "barcode": "fcb76759-f0ab-46ee-8e51-5e2ce6a70d431730799458654",
        "usedAt": null,
        "price": 29.99,
        "deletedAt": null
      }
    ],
    "deletedAt": null
  }
]
```

## Example Request with Start DateTime

```json
GET api/sales/search?start=2024-11-05T11:37:36.691676
Content-Type: application/json
```

## Success Responses

**Condition** : There are sales to show with the given parameters.

**Code** : `200 OK`

**Content** :

```json
[
  {
    "id": 6,
    "paidAt": "2024-11-05T11:37:38.642117",
    "userId": 1,
    "tickets": [
      {
        "id": 9,
        "barcode": "d70e94b6-74bc-4920-b6c4-ee97e055de9d1730799458654",
        "usedAt": null,
        "price": 29.99,
        "deletedAt": null
      },
      {
        "id": 10,
        "barcode": "c11e9776-82f2-4f03-9dd7-1b6d9414f0621730799458654",
        "usedAt": null,
        "price": 29.99,
        "deletedAt": null
      },
      {
        "id": 11,
        "barcode": "fcb76759-f0ab-46ee-8e51-5e2ce6a70d431730799458654",
        "usedAt": null,
        "price": 29.99,
        "deletedAt": null
      }
    ],
    "deletedAt": null
  }
]
```

## Example Request with Start Date, End Date, and UserId

```json
GET api/sales/search?start=2024-11-03&end=2024-11-05&userId=1
Content-Type: application/json
```

## Success Responses

**Condition** : There are sales to show with the given parameters.

**Code** : `200 OK`

**Content** :

```json
[
  {
    "id": 1,
    "paidAt": "2024-11-03T20:38:35.310675",
    "userId": 1,
    "tickets": [
      {
        "id": 2,
        "barcode": "a3aa29b4-93c6-44dd-8505-73ca44638ef41730659115363",
        "usedAt": null,
        "price": 20.0,
        "deletedAt": null
      },
      {
        "id": 1,
        "barcode": "60e04c3a-84fc-4b38-8109-65fd40407ad81730659115347",
        "usedAt": null,
        "price": 10.0,
        "deletedAt": "2024-03-12T09:00:00"
      }
    ],
    "deletedAt": null
  }
]
```

## Error Responses

**Condition** : If no parameters are provided or none are valid.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "message": "At least one search parameter must be provided."
}
```

**Condition** : User not found with given `id`.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "User with ID {id} does not exist."
}
```

# Show Accessible Sales

Show all Sales the active User can access and with what permission level.

**URL** : `/api/sales`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_SALES`

**Data constraints** : `{}`

## Example Request

```json
GET api/sales
Content-Type: application/json
```

## Success Responses

**Condition** : User sees an empty list. There are no Sales to show with user's permissions.

**Code** : `200 OK`

**Content** :

```json
{[]}
```

### OR

**Condition** : User can see one or more Sales.

**Code** : `200 OK`

**Content** : In this example, the User can see two Sales:

```json
[
  {
    "id": 1,
    "paidAt": "2024-11-30T12:33:56.262198",
    "userId": 1,
    "ticketIds": [2, 4, 1]
  },
  {
    "id": 2,
    "paidAt": "2024-11-30T12:33:56.267737",
    "userId": 2,
    "ticketIds": [3]
  }
]
```

# Show Accessible Sales

Show all Sales the active User can access and with what permission level.

**URL** : `/api/sales`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

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
    "paidAt": "2024-10-23T11:01:13.116922",
    "userId": 1,
    "ticketIds": [1, 2, 4]
  },
  {
    "id": 2,
    "paidAt": "2024-10-23T11:01:13.119915",
    "userId": 2,
    "ticketIds": [3]
  }
]
```

# Show All Ticket Types

Show all ticket types

**URL** : `/api/tickettypes`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

## Success Responses

**Condition** : The request is successful and there are no ticket types in the system

**Code** : `200 OK`

**Content** :

```json
[]
```

### OR

**Condition** : The request is successful and there are ticket types in the system

**Code** : `200 OK`

**Content** : In this example, the user can see four different ticket types

```json
[
  {
    "id": 1,
    "name": "adult",
    "retailPrice": 29.99,
    "totalAvailable": null,
    "eventId": 1
  },
  {
    "id": 2,
    "name": "student",
    "retailPrice": 14.99,
    "totalAvailable": null,
    "eventId": 1
  },
  {
    "id": 3,
    "name": "pensioner",
    "retailPrice": 14.99,
    "totalAvailable": null,
    "eventId": 1
  },
  {
    "id": 4,
    "name": "vip",
    "retailPrice": 79.99,
    "totalAvailable": 20,
    "eventId": 1
  }
]
```

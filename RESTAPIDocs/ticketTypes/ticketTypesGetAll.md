# Show All Ticket Types

Show all `TicketTypes`.

**URL** : `/api/tickettypes`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_TICKET_TYPES`

### Example Request

```json
GET /api/tickettypes
```

## Success Responses

**Condition** : The request is successful and there are no `TicketType` objects in the system.

**Code** : `200 OK`

**Content** :

```json
[]
```

### OR

**Condition** : The request is successful and there are `TicketTypes` objects in the system.

**Code** : `200 OK`

**Content** : Returns a list of `TicketType` objects.

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

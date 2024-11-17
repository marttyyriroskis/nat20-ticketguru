# Search Ticket Types

Search ticket types by `evendId`.

**URL** : `/api/tickettypes/search`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_TICKET_TYPES`

**Query Parameters** :

| Parameter | Type | Description                                   |
| --------- | ---- | --------------------------------------------- |
| `eventId` | Long | Unique identifier for the event to search for |

**Data constraints** : `{}`

## Example Request with EventId

```json
GET api/tickettypes/search?eventId=1
Content-Type: application/json
```

## Success Responses

**Condition** : User sees an empty list. There are no Ticket Types to show with the given parameters.

**Code** : `200 OK`

**Content** :

```json
{[]}
```

### OR

**Condition** : There are Ticket Types to show with the given parameters.

**Code** : `200 OK`

**Content** : In this example, the User can see four Ticket Types:

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

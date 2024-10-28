# Show All Events

Show all `Events`.

**URL** : `/api/events`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_EVENTS`

### Example Request

```json
GET /api/events
```

## Success Responses

**Condition** : The request is successful and there are no `Event` objects in the system.

**Code** : `200 OK`

**Content** :

```json
[]
```

### OR

**Condition** : The request is successful and there are `Event` objects in the system.

**Code** : `200 OK`

**Content** : In this example, the `User` can see three `Event` objects:

```json
[
  {
    "id": 1,
    "name": "Death metal karaoke",
    "description": "Öriöriöriöriörirprir!!!!!",
    "total_tickets": 10,
    "begins_at": "2055-10-12T12:00:00",
    "ends_at": "2055-10-12T12:00:00",
    "ticket_sale_begins": "2055-10-12T12:00:00",
    "venueId": 1
  },
  {
    "id": 2,
    "name": "Disney On Ice",
    "description": "Mikki-hiiret jäällä. Suih suih vaan!",
    "total_tickets": 10000,
    "begins_at": "2055-10-12T12:00:00",
    "ends_at": "2055-10-12T12:00:00",
    "ticket_sale_begins": "2055-10-12T12:00:00",
    "venueId": 2
  },
  {
    "id": 3,
    "name": "A Night at the Museum",
    "description": "Night-show at the National Museum",
    "total_tickets": 500,
    "begins_at": "2055-10-12T12:00:00",
    "ends_at": "2055-10-12T12:00:00",
    "ticket_sale_begins": "2055-10-12T12:00:00",
    "venueId": 1
  }
]
```

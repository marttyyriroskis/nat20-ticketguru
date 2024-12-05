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

**Content** : Returns a list of `Event` objects.

```json
[
  {
    "id": 1,
    "name": "Death metal karaoke",
    "description": "Öriöriöriöriörirprir!!!!!",
    "totalTickets": 10,
    "beginsAt": "2055-10-12T12:00:00",
    "endsAt": "2055-10-12T12:00:00",
    "ticketSaleBegins": "2055-10-12T12:00:00",
    "venueId": 1
  },
  {
    "id": 2,
    "name": "Disney On Ice",
    "description": "Mikki-hiiret jäällä. Suih suih vaan!",
    "totalTickets": 10000,
    "beginsAt": "2055-10-12T12:00:00",
    "endsAt": "2055-10-12T12:00:00",
    "ticketSaleBegins": "2055-10-12T12:00:00",
    "venueId": 2
  },
  {
    "id": 3,
    "name": "A Night at the Museum",
    "description": "Night-show at the National Museum",
    "totalTickets": 500,
    "beginsAt": "2055-10-12T12:00:00",
    "endsAt": "2055-10-12T12:00:00",
    "ticketSaleBegins": "2055-10-12T12:00:00",
    "venueId": 1
  }
]
```

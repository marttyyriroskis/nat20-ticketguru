# Show Accessible Events

Show all Events the active User can access and with what permission level.

**URL** : `/api/events`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

**Data constraints** : `{}`

## Success Responses

**Condition** : User can not see any Events.

**Code** : `200 OK`

**Content** : `{[]}`

### OR

**Condition** : User can see one or more Events.

**Code** : `200 OK`

**Content** : In this example, the User can see three Events:

```json
[
  {
    "id": 1,
    "name": "Death metal karaoke",
    "total_tickets": 10,
    "begins_at": "2024-09-29T12:32:38.038188",
    "ends_at": "2024-09-29T12:32:38.038188",
    "ticket_sale_begins": "2024-09-29T12:32:38.038188",
    "description": "Öriöriöriöriörirprir!!!!!",
    "venue": {
      "id": 1,
      "name": "Bunkkeri",
      "address": "Bunkkeritie 1",
      "zipcode": {
        "zipcode": "00100",
        "city": "Helsinki"
      }
    }
  },
  {
    "id": 2,
    "name": "Disney On Ice",
    "total_tickets": 10000,
    "begins_at": "2024-09-29T12:32:38.084463",
    "ends_at": "2024-09-29T12:32:38.084463",
    "ticket_sale_begins": "2024-09-29T12:32:38.084463",
    "description": "Mikki-hiiret jäällä. Suih suih vaan!",
    "venue": {
      "id": 2,
      "name": "Helsingin jäähalli",
      "address": "Nordenskiöldinkatu 11-13",
      "zipcode": {
        "zipcode": "00250",
        "city": "Helsinki"
      }
    }
  },
  {
    "id": 3,
    "name": "A Night at the Museum",
    "total_tickets": 500,
    "begins_at": "2024-09-29T12:32:38.096779",
    "ends_at": "2025-10-12T12:00:00",
    "ticket_sale_begins": "2024-09-29T12:32:38.096779",
    "description": "Night-show at the National Museum",
    "venue": {
      "id": 1,
      "name": "Bunkkeri",
      "address": "Bunkkeritie 1",
      "zipcode": {
        "zipcode": "00100",
        "city": "Helsinki"
      }
    }
  }
]
```

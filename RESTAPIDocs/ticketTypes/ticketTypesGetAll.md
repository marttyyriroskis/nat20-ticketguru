# Show All Ticket Types

Show all ticket types

**URL** : `/api/tickettypes`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

**Data constraints** : `{}`

## Success Responses

**Condition** : The request is successful and there are no ticket types in the system

**Code** : `200 OK`

**Content** : `{[]}`

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
    "event": {
      "id": 1,
      "name": "Death metal karaoke",
      "total_tickets": 10,
      "begins_at": "2055-10-12T12:00:00",
      "ends_at": "2055-10-12T12:00:00",
      "ticket_sale_begins": "2055-10-12T12:00:00",
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
    }
  },
  {
    "id": 2,
    "name": "student",
    "retailPrice": 14.99,
    "totalAvailable": null,
    "event": {
      "id": 1,
      "name": "Death metal karaoke",
      "total_tickets": 10,
      "begins_at": "2055-10-12T12:00:00",
      "ends_at": "2055-10-12T12:00:00",
      "ticket_sale_begins": "2055-10-12T12:00:00",
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
    }
  },
  {
    "id": 3,
    "name": "pensioner",
    "retailPrice": 14.99,
    "totalAvailable": null,
    "event": {
      "id": 1,
      "name": "Death metal karaoke",
      "total_tickets": 10,
      "begins_at": "2055-10-12T12:00:00",
      "ends_at": "2055-10-12T12:00:00",
      "ticket_sale_begins": "2055-10-12T12:00:00",
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
    }
  },
  {
    "id": 4,
    "name": "vip",
    "retail_price": 79.99,
    "total_available": 20,
    "event": {
      "id": 1,
      "name": "Death metal karaoke",
      "total_tickets": 10,
      "begins_at": "2055-10-12T12:00:00",
      "ends_at": "2055-10-12T12:00:00",
      "ticket_sale_begins": "2055-10-12T12:00:00",
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
    }
  }
]
```

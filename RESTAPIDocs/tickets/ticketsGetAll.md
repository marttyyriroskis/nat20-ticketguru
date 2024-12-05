# Show Accessible Tickets

Show all `Tickets`.

**URL** : `/api/tickets`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_TICKETS`

**Query Parameters (optional)** :

| Parameter | Type | Required | Description                      |
| --------- | ---- | -------- | -------------------------------- |
| `ids`     | List | No       | List of ticket ids (long) to get |

## Example Request

```json
GET /tickets
```

## Success Responses

**Condition** : The request is successful and there are no `Ticket` objects in the system.

**Code** : `200 OK`

**Content** :

```json
[]
```

### OR

**Condition** : The request is successful and there are `Ticket` objects in the system.

**Code** : `200 OK`

**Content** : Returns a list of `Ticket` objects.

```json
[
  {
    "id": 4,
    "barcode": "d3e82202-a885-47d0-ae61-fa71d7bc06451732962836371",
    "usedAt": null,
    "price": 40,
    "deletedAt": null,
    "ticketTypeId": 4,
    "saleId": 1,
    "ticketType": {
      "id": 4,
      "name": "vip",
      "retailPrice": 79.99,
      "totalAvailable": 20,
      "eventId": 1
    },
    "event": {
      "id": 1,
      "name": "Death metal karaoke",
      "description": "Öriöriöriöriörirprir!!!!!",
      "totalTickets": 10,
      "beginsAt": "2055-10-12T12:00:00",
      "endsAt": "2055-10-12T12:00:00",
      "ticketSaleBegins": "2055-10-12T12:00:00",
      "venueId": 1
    },
    "venue": {
      "id": 1,
      "name": "Bunkkeri",
      "address": "Bunkkeritie 1",
      "zipcode": "00100"
    }
  },
  {
    "id": 2,
    "barcode": "4d0032ce-4eb7-4626-b5c9-f7b18eaa4cda1732962836327",
    "usedAt": null,
    "price": 20,
    "deletedAt": null,
    "ticketTypeId": 2,
    "saleId": 1,
    "ticketType": {
      "id": 2,
      "name": "student",
      "retailPrice": 14.99,
      "totalAvailable": null,
      "eventId": 1
    },
    "event": {
      "id": 1,
      "name": "Death metal karaoke",
      "description": "Öriöriöriöriörirprir!!!!!",
      "totalTickets": 10,
      "beginsAt": "2055-10-12T12:00:00",
      "endsAt": "2055-10-12T12:00:00",
      "ticketSaleBegins": "2055-10-12T12:00:00",
      "venueId": 1
    },
    "venue": {
      "id": 1,
      "name": "Bunkkeri",
      "address": "Bunkkeritie 1",
      "zipcode": "00100"
    }
  },
  {
    "id": 3,
    "barcode": "0ae9cfc8-c163-42d3-9ad8-60ca90bac1331732962836354",
    "usedAt": null,
    "price": 30,
    "deletedAt": null,
    "ticketTypeId": 2,
    "saleId": 1,
    "ticketType": {
      "id": 2,
      "name": "student",
      "retailPrice": 14.99,
      "totalAvailable": null,
      "eventId": 1
    },
    "event": {
      "id": 1,
      "name": "Death metal karaoke",
      "description": "Öriöriöriöriörirprir!!!!!",
      "totalTickets": 10,
      "beginsAt": "2055-10-12T12:00:00",
      "endsAt": "2055-10-12T12:00:00",
      "ticketSaleBegins": "2055-10-12T12:00:00",
      "venueId": 1
    },
    "venue": {
      "id": 1,
      "name": "Bunkkeri",
      "address": "Bunkkeritie 1",
      "zipcode": "00100"
    }
  }
]
```

## Example Request with List of Ids

```json
GET api/tickets/?ids=5,10
Content-Type: application/json
```

**Condition** : The request is successful and there are `Ticket` objects with the given ids in the system.

**Code** : `200 OK`

**Content** : In this example, the User can see two `Ticket` objects:

```json
[
  {
    "id": 5,
    "barcode": "d0361f8e-868c-480c-8b9c-0c31566dd8491730738479695",
    "usedAt": "2024-11-08T05:35:12.34765",
    "price": 29.99,
    "deletedAt": null,
    "ticketTypeId": 1,
    "saleId": 4,
    "ticketType": {
      "id": 1,
      "name": "adult",
      "retailPrice": 29.99,
      "totalAvailable": null,
      "eventId": 1
    },
    "event": {
      "id": 1,
      "name": "Death metal karaoke",
      "description": "Öriöriöriöriörirprir!!!!!",
      "totalTickets": 10,
      "beginsAt": "2055-10-12T12:00:00",
      "endsAt": "2055-10-12T12:00:00",
      "ticketSaleBegins": "2055-10-12T12:00:00",
      "venueId": 1
    },
    "venue": {
      "id": 1,
      "name": "Bunkkeri",
      "address": "Bunkkeritie 1",
      "zipcode": "00100"
    }
  },
  {
    "id": 10,
    "barcode": "f19bcc4f-3dba-42f0-bf42-e4416811d0631730902420196",
    "usedAt": "2024-11-08T17:20:07.474996",
    "price": 29.99,
    "deletedAt": null,
    "ticketTypeId": 1,
    "saleId": 7,
    "ticketType": {
      "id": 1,
      "name": "adult",
      "retailPrice": 29.99,
      "totalAvailable": null,
      "eventId": 1
    },
    "event": {
      "id": 1,
      "name": "Death metal karaoke",
      "description": "Öriöriöriöriörirprir!!!!!",
      "totalTickets": 10,
      "beginsAt": "2055-10-12T12:00:00",
      "endsAt": "2055-10-12T12:00:00",
      "ticketSaleBegins": "2055-10-12T12:00:00",
      "venueId": 1
    },
    "venue": {
      "id": 1,
      "name": "Bunkkeri",
      "address": "Bunkkeritie 1",
      "zipcode": "00100"
    }
  }
]
```

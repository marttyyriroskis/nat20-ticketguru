# Delete Ticket Type

Allow deleting `TicketType` details of the given `id`.

**URL** : `/api/tickettypes/{id}`

**Method** : `DELETE`

**Auth required** : NO

**Permissions required** : None

**Path Parameters** : `{id}`

| Parameter | Type | Description                                     |
| --------- | ---- | ----------------------------------------------- |
| `id`      | Long | Unique identifier for the ticket type to delete |

**Data constraints** : `{}`

## Example Request

```json
DELETE api/tickettypes/2
Content-Type: application/json
```

## Success Responses

**Condition** : Id provided belongs to a ticket type in the database

**Code** : `200 OK`

**Content example** : Returns remaining `TicketType` objects in the database

```json
[
  {
    "id": 1,
    "name": "adult",
    "retail_price": 29.99,
    "total_available": null,
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
    "retail_price": 14.99,
    "total_available": null,
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

## Error Response

**Condition** : If the provided `TicketType` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not found",
  "message": "Ticket type not found"
}
```

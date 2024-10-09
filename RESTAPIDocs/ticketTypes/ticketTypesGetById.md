# Get Ticket Type

Allow getting `ticket type` details of the given `id`.

**URL** : `/api/tickettypes/{id}`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

**Path Parameters** :

| Parameter | Type | Description                            |
| --------- | ---- | -------------------------------------- |
| `id`      | Long | Unique identifier for the ticket type to get |

#### Example Request

```json
GET api/tickettypes/1
Accept: application/json
```

## Success Responses

**Condition** : Provided event `id` is valid.

**Code** : `200 OK`

**Content example** : Returns the `TicketType` object of the given `id`, with the `Event` and `Venue` details.

```json
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
}
```

## Error Response

**Condition**: If the ticket type with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Ticket type not found"
}
```

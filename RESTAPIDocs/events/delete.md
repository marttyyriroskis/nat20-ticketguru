# Delete Event

Allow deleting `Event` details of the given `id`.

**URL** : `/api/events/{id}`

**Method** : `DELETE`

**Auth required** : NO

**Permissions required** : None

**Path Parameters** : `{id}`

| Parameter | Type | Description                               |
| --------- | ---- | ----------------------------------------- |
| `id`      | Long | Unique identifier for the event to update |

**Data constraints** : `{}`

## Example Request

```json
DELETE /events/2
Content-Type: application/json
```

## Success Responses

**Condition** : Id provided belongs to an event in the database

**Code** : `200 OK`

**Content example** : Returns  remaining `Event` objects in the database

```json
[
  {
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
  },
  {
    "id": 3,
    "name": "A Night at the Museum",
    "total_tickets": 500,
    "begins_at": "2055-10-12T12:00:00",
    "ends_at": "2055-10-12T12:00:00",
    "ticket_sale_begins": "2055-10-12T12:00:00",
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

## Error Response

**Condition** : If the provided `Event` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not found",
  "message": "Event not found"
}
```

# Delete Venue

Allow deleting `Venue` details of the given `id`.

**URL** : `/api/venues/{id}`

**Method** : `DELETE`

**Auth required** : NO

**Permissions required** : `DELETE_VENUES`

**Path Parameters** : `{id}`

| Parameter | Type | Description                               |
| --------- | ---- | ----------------------------------------- |
| `id`      | Long | Unique identifier for the venue to update |

**Data constraints** : `{}`

## Example Request

```json
DELETE api/venues/2
Content-Type: application/json
```

## Success Responses

**Condition** : Id provided belongs to an venue in the database

**Code** : `200 OK`

**Content example** : Returns  remaining `Venue` objects in the database

```json
[
    {
        "id": 1,
        "name": "Bunkkeri",
        "address": "Bunkkeritie 1",
        "zipcode": {
            "zipcode": "00100",
            "city": "Helsinki"
        }
    },
    {
        "id": 3,
        "name": "National Museum",
        "address": "Museokatu 1",
        "zipcode": {
            "zipcode": "00100",
            "city": "Helsinki"
        }
    }
]
```

## Error Response

**Condition** : If the provided `Venue` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not found",
  "message": "Venue not found"
}
```

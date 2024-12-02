# Get Venue

Allow getting `Venue` details of the given `id`.

**URL** : `/api/venues/{id}`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_VENUES`

**Path Parameters** :

| Parameter | Type | Description                            |
| --------- | ---- | -------------------------------------- |
| `id`      | Long | Unique identifier for the venue to get |

#### Example Request

```json
GET /venues/1
```

## Success Responses

**Condition** : Provided venue `id` is valid.

**Code** : `200 OK`

**Content example** : Returns the `Venue` object of the given `id`, with the `Venue` details.

```json
{
  "id": 1,
  "name": "Bunkkeri",
  "address": "Bunkkeritie 1",
  "zipcode": "00100"
}
```

## Error Response

**Condition**: If the venue with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Venue not found"
}
```

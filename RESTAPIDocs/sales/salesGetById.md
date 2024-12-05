# Get Sale

Allow getting `Sale` details of the given `id`.

**URL** : `/api/sales/{id}`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_SALES`

**Path Parameters** :

| Parameter | Type | Description                           |
| --------- | ---- | ------------------------------------- |
| `id`      | Long | Unique identifier for the Sale to get |

#### Example Request

```json
GET /sales/1
```

## Success Response

**Condition** : Provided Sale `id` is valid.

**Code** : `200 OK`

**Content example** : Returns the `Sale` object of the given `id` with `Ticket` details.

```json
{
  "id": 1,
  "paidAt": "2024-11-30T12:33:56.262198",
  "userId": 1,
  "ticketIds": [2, 4, 1]
}
```

## Error Response

**Condition**: If the Sale with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Sale not found"
}
```

# Get Sale

Allow getting `Sale` details of the given `id`.

**URL** : `/api/sales/{id}`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

**Path Parameters** :

| Parameter | Type | Description                           |
| --------- | ---- | ------------------------------------- |
| `id`      | Long | Unique identifier for the Sale to get |

#### Example Request

```json
GET /sales/2
Accept: application/json
```

## Success Responses

**Condition** : Provided Sale `id` is valid.

**Code** : `200 OK`

**Content example** : Returns the `Sale` object of the given `id` with `Ticket` details.

```json
{
  "id": 2,
  "paidAt": "2024-10-06T19:18:28.896336",
  "userId": 2,
  "tickets": [
    {
      "id": 3,
      "barcode": "1728231508866",
      "usedAt": null,
      "price": 0.0,
      "ticketTypeId": 2,
      "saleId": 2
    }
  ]
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

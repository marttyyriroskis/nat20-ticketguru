# Delete Ticket

Allow deleting `Ticket` details of the given `id`.

**URL** : `/api/tickets/{id}`

**Method** : `DELETE`

**Auth required** : NO

**Permissions required** : None

**Path Parameters** : `{id}`

| Parameter | Type | Description                                |
| --------- | ---- | ------------------------------------------ |
| `id`      | Long | Unique identifier for the ticket to delete |

**Data constraints** : `{}`

## Example Request

```json
DELETE api/tickets/4
Content-Type: application/json
```

## Success Responses

**Condition** : Provided ticket `id` is valid.

**Code** : `200 OK`

**Content example** : Returns remaining `Ticket` objects.

```json
[
  {
    "id": 1,
    "barcode": "1728296528349",
    "usedAt": null,
    "price": 200.5,
    "ticketTypeId": 1,
    "saleId": 2
  },
  {
    "id": 2,
    "barcode": "1728296528351",
    "usedAt": null,
    "price": 0.0,
    "ticketTypeId": 2,
    "saleId": 1
  },
  {
    "id": 3,
    "barcode": "1728296528353",
    "usedAt": null,
    "price": 0.0,
    "ticketTypeId": 2,
    "saleId": 2
  }
]
```

## Error Response

**Condition** : If the provided `Ticket` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not found",
  "message": "Ticket not found"
}
```

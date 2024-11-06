## Use Ticket

Marks a ticket as used for the specified `barcode`, provided it has not been used already.

**URL** : `/api/tickets/use/{barcode}`

**Method** : `PUT`

**Auth required** : YES

**Permissions required** : `USE_TICKETS`

### Path Parameters

| Parameter | Type | Description                  |
| --------- | ---- | ---------------------------- |
| `barcode` | Long | Unique barcode of the ticket |

#### Example Request

```http
PUT /api/tickets/use/1728287847109
```

### Success Response

**Condition** : Provided ticket `barcode` is valid and the ticket has not been used.

**Code** : `200 OK`

**Content example** : Returns the `Ticket` object after being marked as used.

```json
{
  "id": 1,
  "barcode": "1728287847109",
  "usedAt": "2023-10-15T14:30:00Z",
  "price": 0.0,
  "ticketType": null,
  "saleId": 1
}
```

### Error Responses

1. **Condition**: If the ticket with the specified `barcode` does not exist.

   **Code** : `404 NOT FOUND`

   **Content example** :

   ```json
   {
     "status": 404,
     "error": "Not Found",
     "message": "Ticket not found"
   }
   ```

2. **Condition**: If the ticket has already been used.

   **Code** : `400 BAD REQUEST`

   **Content example** :

   ```json
   {
     "status": 400,
     "error": "Bad Request",
     "message": "Ticket already used"
   }
   ```

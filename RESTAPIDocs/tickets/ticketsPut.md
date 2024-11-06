# Update Ticket

Allow updating `Ticket` details of the given `id`.

**URL** : `/api/tickets/{id}`

**Method** : `PUT`

**Auth required** : YES

**Permissions required** : `EDIT_TICKETS`

**Path Parameters** :

| Parameter | Type | Description                                |
| --------- | ---- | ------------------------------------------ |
| `id`      | Long | Unique identifier for the ticket to update |

**Data constraints** :

Provide all required parameters in the response body for the `Ticket` to be updated.

| Field            | Type                     | Required | Description                                 |
| ---------------- | ------------------------ | -------- | ------------------------------------------- |
| `usedAt`         | String (ISO 8601 format) | No       | LocalDateTime for when the ticket was used. |
| `price`          | Double                   | Yes      | The selling price of the ticket.            |
| `ticket_type_id` | Long                     | Yes      | The ticket type id.                         |
| `sale_id`        | Long                     | Yes      | The sale id.                                |

#### Example Request

```json
PUT /api/tickets/1
```

All required fields must be sent. `price` must not be null.

```json
{
  "usedAt": null,
  "price": 200.5,
  "ticketTypeId": 1,
  "saleId": 1
}
```

## Success Responses

**Condition** : Data provided is valid.

**Code** : `200 OK`

**Content example** : Returns the updated `Ticket` object.

```json
{
  "barcode": "1729253182164",
  "usedAt": null,
  "price": 200.5,
  "deletedAt": null,
  "ticketTypeId": 1,
  "saleId": 1
}
```

## Error Response

**Condition** : If the ticket with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Ticket not found"
}
```

**Condition** : If the provided `TicketType` or `Sale` do not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not found",
  "message": "Ticket Type not found"
}
```

**OR** :

```json
{
  "status": 404,
  "error": "Not found",
  "message": "Sale not found"
}
```

**Condition** : If required fields are missing.

**Code** : `400 BAD REQUEST`

**Content example**

```json
{
  "price": "Price must be positive or zero",
  "ticketTypeId": "TicketType ID must not be null"
}
```

**Condition**: If any of the given fields do not meet the validation constraints.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "price": "Price must be positive or zero"
}
```

#### Notes

- Ensure that all date and time fields are in ISO 8601 format, and set into the future or present.

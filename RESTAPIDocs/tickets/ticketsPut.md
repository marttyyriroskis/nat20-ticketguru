# Update Ticket

Allow updating `Ticket` details of the given `id`.

**URL** : `/api/tickets/{id}`

**Method** : `PUT`

**Auth required** : NO

**Permissions required** : None

**Path Parameters** :

| Parameter | Type | Description                                |
| --------- | ---- | ------------------------------------------ |
| `id`      | Long | Unique identifier for the ticket to update |

**Data constraints** :

The request body should be a JSON object representing the `Ticket`. It may include the following fields:

| Field            | Type                     | Required | Description                                 |
| ---------------- | ------------------------ | -------- | ------------------------------------------- |
| `barcode`        | String                   | No       | Autogenerated barcode for the ticket.       |
| `usedAt`         | String (ISO 8601 format) | No       | LocalDateTime for when the ticket was used. |
| `price`          | Double                   | Yes      | The selling price of the ticket.            |
| `ticket_type_id` | Long                     | Yes      | The ticket type id.                         |
| `sale_id`        | Long                     | Yes      | The sale id.                                |

#### Example Request

```json
PUT /tickets/1?ticketTypeId=1&saleId=2
Content-Type: application/json

{
  "price": 200.5

}
```

## Success Responses

**Condition** : Data provided is valid.

**Code** : `200 OK`

**Content example** : Returns the updated `Ticket` object, with the `ticket_type_id` and `sale_id` provided.

```json
{
  "id": 1,
  "barcode": "1728296528349",
  "usedAt": null,
  "price": 200.5,
  "ticketTypeId": 1,
  "saleId": 2
}
```

## Error Response

**Condition** : If the required parameters are missing from the query.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Required parameter 'ticketTypeId' is not present.",
  "message": "Required parameter 'saleId' is not present."
}
```

**Condition** : If the id provided in the query does not exist.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "TicketType not found.",
  "message": "Sale not found."
}
```

**Condition** : If required fields are missing.

**Code** : `400 BAD REQUEST`

**Content example**

```json
{
  "price": "Price must not be empty"
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
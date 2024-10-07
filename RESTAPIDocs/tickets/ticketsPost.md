# Add a new Ticket

Create a new `Ticket` entity

**URL** : `/api/tickets`

**Method** : `POST`

**Auth required** : NO

**Permissions required** : None

**Data constraints** :

Provide all required parameters for the `Ticket` to be created.

| Field            | Type                     | Required | Description                                 |
| ---------------- | ------------------------ | -------- | ------------------------------------------- |
| `barcode`        | String                   | No       | Autogenerated barcode for the ticket.       |
| `usedAt`         | String (ISO 8601 format) | No       | LocalDateTime for when the ticket was used. |
| `price`          | Double                   | Yes      | The selling price of the ticket.            |
| `ticket_type_id` | Long                     | Yes      | The ticket type id.                         |
| `sale_id`        | Long                     | Yes      | The sale id.                                |

#### Example Request

````json
POST /tickets?ticketTypeId=1&saleId=1
Content-Type: application/json

All required fields must be sent. `ticket_type_id` and `sale_id` must be specified in the query, not the request body. `price` must not be null.

```json
{
  "price": 20.5
}
````

## Success Responses

**Condition** : Data provided is valid and `price` must not be null. `ticket_type_id` and `sale_id` are specified in the query.

**Code** : `200 OK`

**Content example**

```json
{
  "id": 5,
  "barcode": "1728297078315",
  "usedAt": null,
  "price": 20.5,
  "ticketTypeId": 1,
  "saleId": 1
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

- Ensure that all date and time fields are in ISO 8601 format.
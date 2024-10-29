# Add a new Ticket

Create a new `Ticket` entity.

**URL** : `/api/tickets`

**Method** : `POST`

**Auth required** : NO

**Permissions required** : None

**Data constraints** :

Provide all required parameters for the `Ticket` to be created.

| Field            | Type                     | Required | Description                                    |
| ---------------- | ------------------------ | -------- | ---------------------------------------------- |
| `usedAt`         | String (ISO 8601 format) | No       | LocalDateTime for when the ticket was used.    |
| `price`          | Double                   | Yes      | The selling price of the ticket.               |
| `deletedAt`      | String (ISO 8601 format) | No       | LocalDateTime for when the ticket was deleted. |
| `ticket_type_id` | Long                     | Yes      | The ticket type id.                            |
| `sale_id`        | Long                     | Yes      | The sale id.                                   |

#### Example Request

````json
POST /tickets

All required fields must be sent. `price` must not be null.

```json
{
    "usedAt": null,
    "price": 20.5,
    "ticketTypeId": 1,
    "saleId": 1
}
````

## Success Responses

**Condition** : Data provided is valid. `ticket_type_id` and `sale_id` are valid.

**Code** : `201 CREATED`

**Content example**

```json
{
  "barcode": "1728287847109",
  "usedAt": null,
  "price": 20.5,
  "deletedAt": null,
  "ticketTypeId": 1,
  "saleId": 1
}
```

## Error Response

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

- Ensure that all date and time fields are in ISO 8601 format.

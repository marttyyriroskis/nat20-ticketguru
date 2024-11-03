# Add a new Sale

Confirm a basket of ticket types to create a new `Sale` entity.

**URL** : `/api/sales/confirm`

**Method** : `POST`

**Auth required** : YES

**Permissions required** : `CREATE_SALES`

**Data constraints** :

Provide all required parameters for the `Sale`to be created.

| Field         | Type            | Required | Description                                   |
| ------------- | --------------- | -------- | --------------------------------------------- |
| `ticketItems` | List of objects | Yes      | A list containing the ticket objects to sell. |

Each `ticketItem` object must contain the following fields:

| Field          | Type    | Required | Description                                                          |
| -------------- | ------- | -------- | -------------------------------------------------------------------- |
| `ticketTypeId` | Long    | Yes      | Unique identifier for the ticket type.                               |
| `quantity`     | Integer | Yes      | Amount of tickets of the given ticket type to add to the sale event. |
| `price`        | Double  | Yes      | The price of each ticket.                                            |

**Data example** All required fields must be sent. `ticketItems` cannot be empty. `ticketTypeId` must not be null. `price` must be positive or zero. `Quantity` must be positive.

```json
POST api/sales/confirm
Content-Type: application/json

{
  "ticketItems": [
    {
      "ticketTypeId": 1,
      "quantity": 3,
      "price": 29.99
    },
    {
      "ticketTypeId": 2,
      "quantity": 2,
      "price": 14.99
    }
  ]
}
```

## Success Response

**Condition** : Data provided is valid. Given `ticketTypeId`s exist. User is authenticated and has permission to `CREATE_SALES`.

**Code** : `201 CREATED`

**Content example**

```json
{
  "id": 11,
  "paidAt": "2024-11-02T12:41:16.633473",
  "userId": 1,
  "ticketIds": [21, 22, 23]
}
```

## Error Responses

**Condition** : If no ticketTypeId

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "ticketItems[0].ticketTypeId": "TicketTypeId cannot be null"
}
```

**Condition** : Ticket type not found with given `id`.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "TicketType with ID 10000 does not exist."
}
```
# Add a new Event

Create a new `Event` entity

**URL** : `/api/events/`

**Method** : `POST`

**Auth required** : NO

**Permissions required** : None

**Data constraints** :

Provide all required parameters for the `Event`to be created.

| Field                | Type                     | Required | Description                                                                       |
| -------------------- | ------------------------ | -------- | --------------------------------------------------------------------------------- |
| `name`               | String                   | Yes      | The name of the event (1-100 char).                                               |
| `description`        | String                   | No       | A description of the event (1-500 char).                                          |
| `total_tickets`      | Integer                  | Yes      | The total number of tickets available for the event.                              |
| `begins_at`          | String (ISO 8601 format) | No       | The start date and time of the event.                                             |
| `ends_at`            | String (ISO 8601 format) | No       | The end date and time of the event.                                               |
| `ticket_sale_begins` | String (ISO 8601 format) | No       | The date and time when ticket sales begin.                                        |
| `venue`              | Object                   | No       | An object representing the venue. It may be null or contain the venue `id` (Long) |

**Data example** All required fields must be sent. `name` and `total_tickets` must not be null.

```json
{
  "name": "Vuoden harmain päivä",
  "description": "Vedetään märkää niin että ikenet turpoo.",
  "total_tickets": 10000,
  "begins_at": "2025-11-12T09:18:26.535823",
  "ends_at": "2025-11-12T21:18:26.535823",
  "ticket_sale_begins": "2025-09-29T09:18:26.535823",
  "venue": {
    "id": 2
  }
}
```

## Success Responses

**Condition** : Data provided is valid and `name` and `total_tickets` must not be null. `Venue` `id` is valid.

**Code** : `200 OK`

**Content example**

```json
{
  "id": 2,
  "name": "Vuoden harmain päivä",
  "description": "Vedetään märkää niin että ikenet turpoo.",
  "total_tickets": 10000,
  "begins_at": "2024-11-12T09:18:26.535823",
  "ends_at": "2024-11-12T21:18:26.535823",
  "ticket_sale_begins": "2024-09-29T09:18:26.535823",
  "venue": {
    "id": 2,
    "name": "Helsingin jäähalli",
    "address": "Nordenskiöldinkatu 11-13",
    "zipcode": {
      "zipcode": "00250",
      "city": "Helsinki"
    }
  }
}
```

## Error Response

**Condition** : If the provided `Venue` does not exist.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Venue does not exist!"
}
```

**Condition** : If required fields are missing.

**Code** : `400 BAD REQUEST`

**Content example**

```json
{
  "total_tickets": "Total tickets must not be empty",
  "name": "Name must not be empty"
}
```

**Condition**: If any of the given fields do not meet the validation constraints.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "total_tickets": "Total tickets must be positive",
  "begins_at": "Begin date must be future or present",
  "name": "Name must not be empty",
  "description": "Description must be between 1 and 500 characters long",
  "ends_at": "End date must be future or present",
  "ticket_sale_begins": "Ticket sale begin date must be future or present"
}
```

#### Notes

- To remove the association with a venue, set the `venue` field to `null`.
- Ensure that all date and time fields are in ISO 8601 format, and set into the future or present.

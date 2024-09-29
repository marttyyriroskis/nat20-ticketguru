# Get Event

Allow getting `Event` details of the given `id`.

**URL** : `/api/events/{id}`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

**Path Parameters** :

| Parameter | Type | Description                            |
| --------- | ---- | -------------------------------------- |
| `id`      | Long | Unique identifier for the event to get |

**Data constraints** :

The request body should be a JSON object representing the `Event`. It may include the following fields:

| Field                | Type                     | Required | Description                                                                       |
| -------------------- | ------------------------ | -------- | --------------------------------------------------------------------------------- |
| `name`               | String                   | Yes      | The name of the event (1-100 char).                                               |
| `description`        | String                   | Yes      | A description of the event (1-500 char).                                          |
| `total_tickets`      | Integer                  | Yes      | The total number of tickets available for the event.                              |
| `begins_at`          | String (ISO 8601 format) | Yes      | The start date and time of the event.                                             |
| `ends_at`            | String (ISO 8601 format) | Yes      | The end date and time of the event.                                               |
| `ticket_sale_begins` | String (ISO 8601 format) | Yes      | The date and time when ticket sales begin.                                        |
| `venue`              | Object                   | No       | An object representing the venue. It may be null or contain the venue `id` (Long) |

#### Example Request

```json
GET /events/2
Accept: application/json
```

## Success Responses

**Condition** : Provided event `id` is valid.

**Code** : `200 OK`

**Content example** : Returns the `Event` object of the given `id`, with the `Venue` details.

```json
{
  "id": 2,
  "name": "Disney On Meth-Ice",
  "description": "Mikki-hiiret jäällä. Suih suih vaan!",
  "total_tickets": 10000,
  "begins_at": "2024-09-29T09:18:26.535823",
  "ends_at": "2024-09-29T09:18:26.535823",
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

**Condition**: If the event with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Event not found"
}
```

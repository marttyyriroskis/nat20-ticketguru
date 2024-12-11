# Get Sale Report

Allow getting a sales report summarizing ticket sales.

**URL** : `/api/sales/report`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_SALES`

#### Example Request

```json
GET /sales/report
```

## Success Response

**Condition** : There are events with ticket types in the database.

**Code** : `200 OK`

**Content example** : Returns a list of ticket summaries by `ticketTypeId`.

```json
[
  {
    "ticketTypeId": 3,
    "eventId": 1,
    "ticketsSold": 0,
    "ticketsTotal": 0,
    "totalRevenue": 0.0
  },
  {
    "ticketTypeId": 4,
    "eventId": 1,
    "ticketsSold": 1,
    "ticketsTotal": 1,
    "totalRevenue": 40.0
  }
]
```

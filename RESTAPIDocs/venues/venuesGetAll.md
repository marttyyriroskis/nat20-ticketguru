# Show Accessible Venues

Show all Venues the active User can access and with what permission level.

**URL** : `/api/venues/`

**Method** : `GET`

**Auth required** : YES

**Permissions required** : `VIEW_VENUES`

## Success Responses

**Condition** : User cannot see any Venues.

**Code** : `200 OK`

**Content** :

```json
[]
```

### OR

**Condition** : User can see one or more Venues.

**Code** : `200 OK`

**Content** : Returns a list of `Venue` objects.

```json
[
  {
    "id": 1,
    "name": "Bunkkeri",
    "address": "Bunkkeritie 1",
    "zipcode": "00100"
  },
  {
    "id": 2,
    "name": "Helsingin jäähalli",
    "address": "Nordenskiöldinkatu 11-13",
    "zipcode": "00250"
  },
  {
    "id": 3,
    "name": "National Museum",
    "address": "Museokatu 1",
    "zipcode": "00100"
  }
]
```

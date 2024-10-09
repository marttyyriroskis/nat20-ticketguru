# Show Accessible Venues

Show all Venues the active User can access and with what permission level.

**URL** : `/api/venues/`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

**Data constraints** : `{}`

## Success Responses

**Condition** : User can not see any Venues.

**Code** : `200 OK`

**Content** : `{[]}`

### OR

**Condition** : User can see one or more Venues.

**Code** : `200 OK`

**Content** : In this example, the User can see three Venues:

```json
[
    {
        "id": 1,
        "name": "Bunkkeri",
        "address": "Bunkkeritie 1",
        "zipcode": {
            "zipcode": "00100",
            "city": "Helsinki"
        }
    },
    {
        "id": 2,
        "name": "Helsingin jäähalli",
        "address": "Nordenskiöldinkatu 11-13",
        "zipcode": {
            "zipcode": "00250",
            "city": "Helsinki"
        }
    },
    {
        "id": 3,
        "name": "National Museum",
        "address": "Museokatu 1",
        "zipcode": {
            "zipcode": "00100",
            "city": "Helsinki"
        }
    }
]
```

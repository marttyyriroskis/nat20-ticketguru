# Update Venue

Allow updating `Venue` details of the given `id`.

**URL** : `/api/venues/{id}`

**Method** : `PUT`

**Auth required** : NO

**Permissions required** : None

**Path Parameters** :

| Parameter | Type | Description                               |
| --------- | ---- | ----------------------------------------- |
| `id`      | Long | Unique identifier for the venue to update |

**Data constraints** :

The request body should be a JSON object representing the `Venue`. It may include the following fields:

| Field                | Type                     | Required | Description                                                     |
| -------------------- | ------------------------ | -------- | --------------------------------------------------------------- |
| `name`               | String                   | Yes      | The name of the venue (1-100 char).                             |
| `address`            | String                   | Yes      | The address of the venue (1-100 char).                          |
| `zipcode`            | Object                   | Yes      | An object representing the zipcode. Contains the zipcode `id`.  |

#### Example Request

```json
PUT /venues/2
Content-Type: application/json

{
    "name": "Keimon kisahalli",
    "address": "Kukkakatu 12",
    "zipcode": {
            "zipcode": "00100"
            
        }  
    
}
```

## Success Responses

**Condition** : Data provided is valid.

**Code** : `200 OK`

**Content example** : Returns the updated `Venue` object, with the `id` field and `Zipcode` details.

```json
{
    "id": 2,
    "name": "Keimon kisahalli",
    "address": "Kukkakatu 12",
    "zipcode": {
        "zipcode": "00100",
        "city": "Helsinki"
    }
}
```

## Error Response

**Condition** : If the provided `Venue` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Venue not found"
}
```
**Condition** : If the provided `Zipcode` does not exist.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid zipcode"
}
```

**Condition** : If the provided `Zipcode` is null.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Zipcode cannot be null!"
}
```

**Condition**: If any of the given fields do not meet the validation constraints.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "name": "Name must not be empty",
  "address": "Address must not be empty",  
}
```

#### Notes

- This endpoint does not allow the creation of a new zipcode. Only existing zipcodes can be assigned to the venue.

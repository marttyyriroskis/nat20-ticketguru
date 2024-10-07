# Add a new Venue

Create a new `Venue` entity

**URL** : `/api/venues/`

**Method** : `POST`

**Auth required** : NO

**Permissions required** : None

**Data constraints** :

Provide all required parameters for the `Venue`to be created.

| Field                | Type                     | Required | Description                                                     |
| -------------------- | ------------------------ | -------- | --------------------------------------------------------------- |
| `name`               | String                   | Yes      | The name of the venue (1-100 char).                             |
| `address`            | String                   | Yes      | The address of the venue (1-100 char).                          |
| `zipcode`            | Object                   | Yes      | An object representing the zipcode. Contains the zipcode `id`.  |


**Data example** All required fields must be sent. `name`, `address` and `zipcode` must not be null.

```json
{
    "name": "Keimon kisahalli",
    "address": "Kukkakatu 12",
    "zipcode": {
            "zipcode": "70200"          
        }     
}
```

## Success Responses

**Condition** : Data provided is valid and `name` and `address` must not be null. `zipcode` `zipcode` is valid.

**Code** : `200 OK`

**Content example**

```json
{
    "id": 4,
    "name": "Keimon kisahalli",
    "address": "Kukkakatu 12",
    "zipcode": {
        "zipcode": "70200",
        "city": "Kuopio"
    }
}
```

## Error Response

**Condition** : If the provided `Zipcode` does not exist.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Zipcode does not exist!"
}
```

**Condition** : If required fields are missing.

**Code** : `400 BAD REQUEST`

**Content example**

```json
{
  "name": "Name must not be empty",
  "address": "Address must not be empty",
  "zipcode": "Zipcode cannot be null!"
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

- The zipcode "id" is `Zipcode`
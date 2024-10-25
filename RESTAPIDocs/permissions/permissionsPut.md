# Update a Permission

Allow updating the details of an existing permission.

**URL** : `/api/permissions/{id}`

**Method** : `PUT`

**Auth required** : Yes

**Permissions required** : Admin

### Path Parameters:

| Parameter | Type | Description                                    |
| --------- | ---- | ---------------------------------------------- |
| `id`      | Long | Unique identifier for the permission to update |

### Request Body:

| Field   | Type   | Description                                                          |
| ------- | ------ | -------------------------------------------------------------------- |
| `id`    | Long   | The unique identifier of the permission (to be included for clarity) |
| `title` | String | The title of the permission                                          |

### Example Request

```json
PUT /api/permissions/1
Content-Type: application/json

{
  "title": "updated read permission",
  "roleIds": []
}
```

## Success Responses

**Condition** : Permission is updated successfully.

**Code** : `200 OK`

**Content example** : Returns the updated `Permission` object.

```json
{
  "title": "updated read permission",
  "roleIds": []
}
```

## Error Responses

**Condition** : If the permission with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Permission not found"
}
```

**Condition** : If the permission title already exists.

**Code** : `409 CONFLICT`

**Content example** :

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Permission already exists"
}
```

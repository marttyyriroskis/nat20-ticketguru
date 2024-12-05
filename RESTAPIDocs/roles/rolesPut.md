# Update a Role

Allow updating the details of an existing role.

**URL** : `/api/roles/{id}`

**Method** : `PUT`

**Auth required** : YES

**Permissions required** : `EDIT_ROLES`

**Path Parameters** :

| Parameter | Type | Description                              |
| --------- | ---- | ---------------------------------------- |
| `id`      | Long | Unique identifier for the role to update |

**Data constraints** :

Provide all required parameters in the response body for the `Role` to be updated.

| Field   | Type   | Description                                                    |
| ------- | ------ | -------------------------------------------------------------- |
| `id`    | Long   | The unique identifier of the role (to be included for clarity) |
| `title` | String | The title of the role                                          |

### Example Request

```json
PUT /api/roles/5

{
  "title": "user"
}
```

## Success Responses

**Condition** : Role is updated successfully.

**Code** : `200 OK`

**Content example** : Returns the updated `Role` object.

```json
{
  "title": "user",
  "permissions": [],
  "userIds": []
}
```

## Error Response

**Condition** : If the role title is already in the database

**Code** : `409 CONFLICT`

**Content example** :

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Role already exists "
}
```

**Condition** : If the role with the specified `id` does not exist.

**Code** : `404 NOT FOUND`

**Content example** :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Role not found"
}
```

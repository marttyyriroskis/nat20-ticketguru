# Get Permission by ID

Allow retrieving a permission by its unique identifier.

**URL** : `/api/permissions/{id}`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

### Path Parameters:

| Parameter | Type | Description                                 |
| --------- | ---- | ------------------------------------------- |
| `id`      | Long | Unique identifier for the permission to get |

### Example Request

```json
GET /api/permissions/1
Accept: application/json
```

## Success Responses

**Condition** : Provided permission `id` is valid.

**Code** : `200 OK`

**Content example** : Returns the `Permission` object of the given `id`.

```json
{
  "title": "read",
  "roleIds": []
}
```

## Error Response

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

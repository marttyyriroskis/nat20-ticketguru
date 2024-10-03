# Permission API

Allow managing permissions in the system.

## Get All Permissions

Allow retrieving a list of all permissions.

**URL** : `/api/permissions`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

### Example Request

```json
GET /api/permissions
Accept: application/json
```

## Success Responses

**Condition** : The request is successful, and there are permissions in the system.

**Code** : `200 OK`

**Content example** : Returns a list of `Permission` objects.

```json
[
  {
    "id": 1,
    "title": "read"
  },
  {
    "id": 2,
    "title": "write"
  }
]
```

## Get Permission by ID

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
  "id": 1,
  "title": "read"
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

## Add a New Permission

Allow adding a new permission to the system.

**URL** : `/api/permissions`

**Method** : `POST`

**Auth required** : NO

**Permissions required** : None

### Request Body:

| Field   | Type   | Description                 |
| ------- | ------ | --------------------------- |
| `title` | String | The title of the permission |

### Example Request

```json
POST /api/permissions
Content-Type: application/json

{
  "title": "execute"
}
```

## Success Responses

**Condition** : Permission is created successfully.

**Code** : `201 Created`

**Content example** : Returns the created `Permission` object.

```json
{
  "id": 3,
  "title": "execute"
}
```

## Update a Permission

Allow updating the details of an existing permission.

**URL** : `/api/permissions/{id}`

**Method** : `PUT`

**Auth required** : NO

**Permissions required** : None

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
  "title": "updated read permission"
}
```

## Success Responses

**Condition** : Permission is updated successfully.

**Code** : `200 OK`

**Content example** : Returns the updated `Permission` object.

```json
{
  "id": 1,
  "title": "updated read permission"
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

## Delete a Permission

Allow deleting a permission from the system.

**URL** : `/api/permissions/{id}`

**Method** : `DELETE`

**Auth required** : NO

**Permissions required** : None

### Path Parameters:

| Parameter | Type | Description                                    |
| --------- | ---- | ---------------------------------------------- |
| `id`      | Long | Unique identifier for the permission to delete |

### Example Request

```json
DELETE /api/permissions/1
```

## Success Responses

**Condition** : Permission is deleted successfully.

**Code** : `204 No Content`

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

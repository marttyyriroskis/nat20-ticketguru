# Role API

Allow managing roles in the system.

## Get All Roles

Allow retrieving a list of all roles.

**URL** : `/api/roles`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

### Example Request

```json
GET /api/roles
Accept: application/json
```

## Success Responses

**Condition** : The request is successful, and there are roles in the system.

**Code** : `200 OK`

**Content example** : Returns a list of `Role` objects.

```json
[
  {
    "id": 1,
    "title": "cashier"
  },
  {
    "id": 2,
    "title": "event organizer"
  }
]
```

## Get Role by ID

Allow retrieving a role by its unique identifier.

**URL** : `/api/roles/{id}`

**Method** : `GET`

**Auth required** : NO

**Permissions required** : None

### Path Parameters:

| Parameter | Type | Description                           |
| --------- | ---- | ------------------------------------- |
| `id`      | Long | Unique identifier for the role to get |

### Example Request

```json
GET /api/roles/1
Accept: application/json
```

## Success Responses

**Condition** : Provided role `id` is valid.

**Code** : `200 OK`

**Content example** : Returns the `Role` object of the given `id`.

```json
{
  "id": 1,
  "title": "cashier"
}
```

## Error Response

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

## Add a New Role

Allow adding a new role to the system.

**URL** : `/api/roles`

**Method** : `POST`

**Auth required** : NO

**Permissions required** : None

### Request Body:

| Field   | Type   | Description           |
| ------- | ------ | --------------------- |
| `title` | String | The title of the role |

### Example Request

```json
POST /api/roles
Content-Type: application/json

{
  "title": "new role"
}
```

## Success Responses

**Condition** : Role is created successfully.

**Code** : `201 Created`

**Content example** : Returns the created `Role` object.

```json
{
  "id": 3,
  "title": "new role"
}
```

## Update a Role

Allow updating the details of an existing role.

**URL** : `/api/roles/{id}`

**Method** : `PUT`

**Auth required** : NO

**Permissions required** : None

### Path Parameters:

| Parameter | Type | Description                              |
| --------- | ---- | ---------------------------------------- |
| `id`      | Long | Unique identifier for the role to update |

### Request Body:

| Field   | Type   | Description                                                    |
| ------- | ------ | -------------------------------------------------------------- |
| `id`    | Long   | The unique identifier of the role (to be included for clarity) |
| `title` | String | The title of the role                                          |

### Example Request

```json
PUT /api/roles/1
Content-Type: application/json

{
  "id": 1,
  "title": "updated role"
}
```

## Success Responses

**Condition** : Role is updated successfully.

**Code** : `200 OK`

**Content example** : Returns the updated `Role` object.

```json
{
  "id": 1,
  "title": "updated role"
}
```

## Error Response

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

## Delete a Role

Allow deleting a role from the system.

**URL** : `/api/roles/{id}`

**Method** : `DELETE`

**Auth required** : NO

**Permissions required** : None

### Path Parameters:

| Parameter | Type | Description                              |
| --------- | ---- | ---------------------------------------- |
| `id`      | Long | Unique identifier for the role to delete |

### Example Request

```json
DELETE /api/roles/1
```

## Success Responses

**Condition** : Role is deleted successfully.

**Code** : `204 No Content`

## Error Response

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

## Add Permission to a Role

Allow associating a permission with a specific role.

**URL** : `/api/roles/{roleId}/permissions`

**Method** : `POST`

**Auth required** : NO

**Permissions required** : None

### Request Body:

| Field | Type | Description                                   |
| ----- | ---- | --------------------------------------------- |
| `id`  | Long | The permission id to associate with the role. |

### Example Request

```json
POST /api/roles/1/permissions
Content-Type: application/json

{
  "id": "2"
}
```

## Success Responses

**Condition** : The role exists, and the permission is added successfully.

**Code** : `200 OK`

**Content example** : Returns the updated `Role` object with the added permission.

```json
{
  "id": 1,
  "title": "Admin",
  "permissions": [
    {
      "id": 2,
      "title": "write"
    }
  ]
}
```

## Remove Permission from Role

Allow removing a permission (by its ID) from a specific role.

**URL**: `/api/roles/{roleId}/permission/{permissionId}`

**Method**: `DELETE`

**Auth required**: NO

**Permissions required**: None

### URL Parameters

| Parameter      | Type | Description                                                   |
| -------------- | ---- | ------------------------------------------------------------- |
| `roleId`       | Long | The ID of the role from which the permission will be removed. |
| `permissionId` | Long | The ID of the permission to remove from the role.             |

### Example Request

```json
DELETE /api/roles/1/permission/2
```

### Success Responses

**Condition**: The role and permission exist, and the permission is successfully removed from the role.

**Code**: `200 OK`

**Content example**: Returns the updated `Role` object with the remaining permissions (if any).

```json
{
  "id": 1,
  "title": "Admin",
  "permissions": [
    {
      "id": 3,
      "title": "Read"
    }
  ]
}
```

### Error Responses

#### 404 Not Found

**Condition**: If the role with the specified `roleId` or the permission with `permissionId` does not exist.

**Code**: `404 NOT FOUND`

**Content example**:

```json
{
  "message": "Role not found"
}
```

or

```json
{
  "message": "Permission not found"
}
```

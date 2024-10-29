# Add Permission to a Role

Allow associating a permission with a specific role.

**URL** : `/api/roles/{roleId}/permissions`

**Method** : `POST`

**Auth required** : Yes

**Permissions required** : Admin

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
  "title": "cashier",
  "permissionIds": [1, 2],
  "userIds": [1]
}
```

# Remove Permission from Role

Allow removing a permission (by its ID) from a specific role.

**URL**: `/api/roles/{roleId}/permission/{permissionId}`

**Method**: `DELETE`

**Auth required**: Yes

**Permissions required**: Admin

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
  "title": "cashier",
  "permissionIds": [],
  "userIds": [1]
}
```

### Error Responses

#### 404 Not Found

**Condition**: If the role with the specified `roleId` or the permission with `permissionId` does not exist, or the role doesn't have requested permission.

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

or

```json
{
  "message": "Role does not have this permission"
}
```

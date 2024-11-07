# REST API Documentation Templates

## What's in this documentation

This documentation contains endpoints to which you can make REST API requests. If an endpoint or a request is not specified, it cannot be requested through the API.

Below you can find a table of contents to the REST API request calls. Please note that for some tables/entities, there are two possible GET requests:

1. GET request to get all resources in the table (**getAll**)
2. GET request to get a specific resource from the table specified by its id (**getById**)

## Endpoints and their permissions

In order to be able to use APIs, you need to be authenticated and have the correct role with the correct permissions. See roles and permissions in the table below.

### Events (/api/events)

- [eventsDelete](events/eventsDelete.md)
- [eventsGetAll](events/eventsGetAll.md)
- [eventsGetById](events/eventsGetById.md)
- [eventsPost](events/eventsPost.md)
- [eventsPut](events/eventsPut.md)

<details>
<summary>Events permissions</summary>

| Permission    | Request | TICKET INSPECTOR | SALESPERSON | COORDINATOR | ADMIN |
| ------------- | ------- | ---------------- | ----------- | ----------- | ----- |
| VIEW_EVENTS   | GET     | NO               | YES         | YES         | YES   |
| CREATE_EVENTS | POST    | NO               | NO          | YES         | YES   |
| EDIT_EVENTS   | PUT     | NO               | NO          | YES         | YES   |
| DELETE_EVENTS | DELETE  | NO               | NO          | NO          | YES   |

</details>

### Roles (/api/roles)

- [rolesDelete](roles/rolesDelete.md)
- [rolesGetAll](roles/rolesGetAll.md)
- [rolesGetById](roles/rolesGetById.md)
- [rolesPermissions](roles/rolesPermissions.md)
- [rolesPost](roles/rolesPost.md)
- [rolesPut](roles/rolesPut.md)

<details>
<summary>Roles permissions</summary>

| Permission   | Request | TICKET INSPECTOR | SALESPERSON | COORDINATOR | ADMIN |
| ------------ | ------- | ---------------- | ----------- | ----------- | ----- |
| VIEW_ROLES   | GET     | NO               | NO          | NO          | YES   |
| CREATE_ROLES | POST    | NO               | NO          | NO          | YES   |
| EDIT_ROLES   | PUT     | NO               | NO          | NO          | YES   |
| DELETE_ROLES | DELETE  | NO               | NO          | NO          | YES   |

</details>

### Sales (/api/sales)

- [salesConfirm](sales/salesConfirm.md)
- [salesDelete](sales/salesDelete.md)
- [salesGetAll](sales/salesGetAll.md)
- [salesGetById](sales/salesGetById.md)
- [salesPost](sales/salesPost.md)
- [salesPut](sales/salesPut.md)
- [salesSearch](sales/salesSearch.md)

<details>
<summary>Sales permissions</summary>

| Permission    | Request | TICKET INSPECTOR | SALESPERSON | COORDINATOR | ADMIN |
| ------------- | ------- | ---------------- | ----------- | ----------- | ----- |
| VIEW_SALES    | GET     | NO               | YES         | NO          | YES   |
| CREATE_SALES  | POST    | NO               | YES         | NO          | YES   |
| EDIT_SALES    | PUT     | NO               | YES         | NO          | YES   |
| DELETE_SALES  | DELETE  | NO               | NO          | NO          | YES   |
| CONFIRM_SALES | POST    | NO               | YES         | NO          | YES   |

</details>

### Tickets (/api/tickets)

- [ticketsDelete](tickets/ticketsDelete.md)
- [ticketsGetAll](tickets/ticketsGetAll.md)
- [ticketsGetByBarcode](tickets/ticketsGetByBarcode.md)
- [ticketsGetById](tickets/ticketsGetById.md)
- [ticketsPost](tickets/ticketsPost.md)
- [ticketsPut](tickets/ticketsPut.md)
- [ticketsUse](tickets/ticketsUse.md)

<details>
<summary>Tickets permissions</summary>

| Permission     | Request | TICKET INSPECTOR | SALESPERSON | COORDINATOR | ADMIN |
| -------------- | ------- | ---------------- | ----------- | ----------- | ----- |
| VIEW_TICKETS   | GET     | YES              | YES         | NO          | YES   |
| CREATE_TICKETS | POST    | NO               | NO          | NO          | YES   |
| EDIT_TICKETS   | PUT     | NO               | NO          | NO          | YES   |
| DELETE_TICKETS | DELETE  | NO               | YES         | NO          | YES   |

</details>

### Ticket types (/api/tickettypes)

- [ticketTypesDelete](ticketTypes/ticketTypesDelete.md)
- [ticketTypesGetAll](ticketTypes/ticketTypesGetAll.md)
- [ticketTypesGetById](ticketTys/ticketTypesGetById.md)
- [ticketTypesPost](ticketTypes/ticketTypesPost.md)
- [ticketTypesPut](ticketTypes/ticketTypesPut.md)

<details>
<summary>Ticket types permissions</summary>

| Permission          | Request | TICKET INSPECTOR | SALESPERSON | COORDINATOR | ADMIN |
| ------------------- | ------- | ---------------- | ----------- | ----------- | ----- |
| VIEW_TICKET_TYPES   | GET     | NO               | YES         | YES         | YES   |
| CREATE_TICKET_TYPES | POST    | NO               | NO          | YES         | YES   |
| EDIT_TICKET_TYPES   | PUT     | NO               | NO          | YES         | YES   |
| DELETE_TICKET_TYPES | DELETE  | NO               | NO          | YES         | YES   |

</details>

### Users (/api/users)

- [usersDelete](users/usersDelete.md)
- [usersGetAll](users/usersGetAll.md)
- [usersGetById](users/usersGetById.md)
- [usersPost](users/usersPost.md)
- [usersPut](users/usersPut.md)

<details>
<summary>Users permissions</summary>

| Permission   | Request | TICKET INSPECTOR | SALESPERSON | COORDINATOR | ADMIN |
| ------------ | ------- | ---------------- | ----------- | ----------- | ----- |
| VIEW_USERS   | GET     | NO               | NO          | NO          | YES   |
| CREATE_USERS | POST    | NO               | NO          | NO          | YES   |
| EDIT_USERS   | PUT     | NO               | NO          | NO          | YES   |
| DELETE_USERS | DELETE  | NO               | NO          | NO          | YES   |

</details>

### Venues (/api/venues)

- [venuesDelete](venues/venuesDelete.md)
- [venuesGetAll](venues/venuesGetAll.md)
- [venuesGetById](venues/venuesGetById.md)
- [venuesPost](venues/venuesPost.md)
- [venuesPut](venues/venuesPut.md)

<details>
<summary>Venues permissions</summary>

| Permission    | Request | TICKET INSPECTOR | SALESPERSON | COORDINATOR | ADMIN |
| ------------- | ------- | ---------------- | ----------- | ----------- | ----- |
| VIEW_VENUES   | GET     | NO               | YES         | YES         | YES   |
| CREATE_VENUES | POST    | NO               | NO          | YES         | YES   |
| EDIT_VENUES   | PUT     | NO               | NO          | YES         | YES   |
| DELETE_VENUES | DELETE  | NO               | NO          | YES         | YES   |

</details>

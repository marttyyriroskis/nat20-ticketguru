# REST API Documentation Templates

## What's in this documentation

This documentation contains endpoints to which you can make REST API requests. If an endpoint or a request is not specified, it cannot be requested through the API. The documentation also contains [a section on ThunderClient API requests](#thunderclient-api-requests).

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

| Permission    | Request | SALESPERSON | COORDINATOR | ADMIN |
| ------------- | ------- | ----------- | ----------- | ----- |
| VIEW_EVENTS   | GET     | YES         | YES         | YES   |
| CREATE_EVENTS | POST    | NO          | YES         | YES   |
| EDIT_EVENTS   | PUT     | NO          | YES         | YES   |
| DELETE_EVENTS | DELETE  | NO          | NO          | YES   |

</details>

### Roles (/api/roles)

- [roleDelete](role/roleDelete.md)
- [roleGetAll](role/roleGetAll.md)
- [roleGetById](role/roleGetById.md)
- [rolePost](role/rolePost.md)
- [rolePut](role/rolePut.md)

<details>
<summary>Roles permissions</summary>

| Permission   | Request | SALESPERSON | COORDINATOR | ADMIN |
| ------------ | ------- | ----------- | ----------- | ----- |
| VIEW_ROLES   | GET     | NO          | NO          | YES   |
| CREATE_ROLES | POST    | NO          | NO          | YES   |
| EDIT_ROLES   | PUT     | NO          | NO          | YES   |
| DELETE_ROLES | DELETE  | NO          | NO          | YES   |

</details>

### Sales (/api/sales)

- [salesDelete](sales/salesDelete.md)
- [salesGetAll](sales/salesGetAll.md)
- [salesGetById](sales/salesGetById.md)
- [salesPost](sales/salesPost.md)
- [salesPut](sales/salesPut.md)
- [salesSearch](sales/salesSearch.md)
- [salesConfirm](sales/salesConfirm.md)

<details>
<summary>Sales permissions</summary>

| Permission    | Request | SALESPERSON | COORDINATOR | ADMIN |
| ------------- | ------- | ----------- | ----------- | ----- |
| VIEW_SALES    | GET     | YES         | NO          | YES   |
| CREATE_SALES  | POST    | YES         | NO          | YES   |
| EDIT_SALES    | PUT     | YES         | NO          | YES   |
| DELETE_SALES  | DELETE  | NO          | NO          | YES   |
| CONFIRM_SALES | POST    | YES         | NO          | YES   |

</details>

### Tickets (/api/tickets)

- [ticketsDelete](tickets/ticketsDelete.md)
- [ticketsGetAll](tickets/ticketsGetAll.md)
- [ticketsGetById](tickets/ticketsGetById.md)
- [ticketsPost](tickets/ticketsPost.md)
- [ticketsPut](tickets/ticketsPut.md)

<details>
<summary>Tickets permissions</summary>

| Permission     | Request | SALESPERSON | COORDINATOR | ADMIN |
| -------------- | ------- | ----------- | ----------- | ----- |
| VIEW_TICKETS   | GET     | YES         | NO          | YES   |
| CREATE_TICKETS | POST    | NO          | NO          | YES   |
| EDIT_TICKETS   | PUT     | NO          | NO          | YES   |
| DELETE_TICKETS | DELETE  | YES         | NO          | YES   |

</details>

### Ticket types (/api/tickettypes)

- [ticketTypesDelete](ticketTypes/ticketTypesDelete.md)
- [ticketTypesGetAll](ticketTypes/ticketTypesGetAll.md)
- [ticketTypesGetById](ticketTys/ticketTypesGetById.md)
- [ticketTypesPost](ticketTypes/ticketTypesPost.md)
- [ticketTypesPut](ticketTypes/ticketTypesPut.md)

<details>
<summary>Ticket types permissions</summary>

| Permission          | Request | SALESPERSON | COORDINATOR | ADMIN |
| ------------------- | ------- | ----------- | ----------- | ----- |
| VIEW_TICKET_TYPES   | GET     | YES         | YES         | YES   |
| CREATE_TICKET_TYPES | POST    | NO          | YES         | YES   |
| EDIT_TICKET_TYPES   | PUT     | NO          | YES         | YES   |
| DELETE_TICKET_TYPES | DELETE  | NO          | YES         | YES   |

</details>

### Users (/api/users)

- [usersDelete](users/usersDelete.md)
- [usersGetAll](users/usersGetAll.md)
- [usersGetById](users/usersGetById.md)
- [usersPost](users/usersPost.md)
- [usersPut](users/usersPut.md)

<details>
<summary>Users permissions</summary>

| Permission   | Request | SALESPERSON | COORDINATOR | ADMIN |
| ------------ | ------- | ----------- | ----------- | ----- |
| VIEW_USERS   | GET     | NO          | NO          | YES   |
| CREATE_USERS | POST    | NO          | NO          | YES   |
| EDIT_USERS   | PUT     | NO          | NO          | YES   |
| DELETE_USERS | DELETE  | NO          | NO          | YES   |

</details>

### Venues (/api/venues)

- [venuesDelete](venues/venuesDelete.md)
- [venuesGetAll](venues/venuesGetAll.md)
- [venuesGetById](venues/venuesGetById.md)
- [venuesPost](venues/venuesPost.md)
- [venuesPut](venues/venuesPut.md)

<details>
<summary>Venues permissions</summary>

| Permission    | Request | SALESPERSON | COORDINATOR | ADMIN |
| ------------- | ------- | ----------- | ----------- | ----- |
| VIEW_VENUES   | GET     | YES         | YES         | YES   |
| CREATE_VENUES | POST    | NO          | YES         | YES   |
| EDIT_VENUES   | PUT     | NO          | YES         | YES   |
| DELETE_VENUES | DELETE  | NO          | YES         | YES   |

</details>

## ThunderClient API requests

For ThunderClient VS Code extension users, we offer JSON files to import API requests directly into the extension. See the ThunderClient requests folder for the API request collections.

How to use:

1. Download ThunderClient VS Code extension (ExtensionID: rangav.vscode-thunder-client, [or install here](https://marketplace.visualstudio.com/items?itemName=rangav.vscode-thunder-client) )
2. Select "Collections"
3. From menu (three bars), select "Import"
4. Navigate to ThunderClient requests folder
5. Select the API requests collection you want to import
6. Done!

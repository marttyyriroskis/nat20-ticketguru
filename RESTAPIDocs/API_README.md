# REST API Documentation Templates

## What's in this documentation

This documentation contains endpoints to which you can make REST API requests. If an endpoint or a request is not specified, it cannot be requested through the API. The documentation also contains [a section on ThunderClient API requests](#thunderclient-api-requests).

Below you can find a table of contents to the REST API request calls. Please note that for some tables/entities, there are two possible GET requests:

1. GET request to get all resources in the table (**getAll**)
2. GET request to get a specific resource from the table specified by its id (**getById**)

## Endpoints

### Events (/api/events)

- [eventsDelete](events/eventsDelete.md)
- [eventsGetAll](events/eventsGetAll.md)
- [eventsGetById](events/eventsGetById.md)
- [eventsPost](events/eventsPost.md)
- [eventsPut](events/eventsPut.md)

### Permissions (/api/permissions)

- [permissionsDelete](permissions/permissionsDelete.md)
- [permissionsGetAll](permissions/permissionsGetAll.md)
- [permissionsGetById](permissions/permissionsGetById.md)
- [permissionsPost](permissions/permissionsPost.md)
- [permissionsPut](permissions/permissionsPut.md)

### Roles (/api/roles)

- [roleDelete](role/roleDelete.md)
- [roleGetAll](role/roleGetAll.md)
- [roleGetById](role/roleGetById.md)
- [rolePost](role/rolePost.md)
- [rolePut](role/rolePut.md)

### Sales (/api/sales)

- [salesDelete](sales/salesDelete.md)
- [salesGetAll](sales/salesGetAll.md)
- [salesGetById](sales/salesGetById.md)
- [salesPost](sales/salesPost.md)
- [salesPut](sales/salesPut.md)

### Tickets (/api/tickets)

- [ticketsDelete](tickets/ticketsDelete.md)
- [ticketsGetAll](tickets/ticketsGetAll.md)
- [ticketsGetById](tickets/ticketsGetById.md)
- [ticketsPost](tickets/ticketsPost.md)
- [ticketsPut](tickets/ticketsPut.md)

### Ticket types (/api/tickettypes)

- [ticketTypesDelete](ticketTypes/ticketTypesDelete.md)
- [ticketTypesGetAll](ticketTypes/ticketTypesGetAll.md)
- [ticketTypesGetById](ticketTys/ticketTypesGetById.md)
- [ticketTypesPost](ticketTypes/ticketTypesPost.md)
- [ticketTypesPut](ticketTypes/ticketTypesPut.md)

### Users (/api/users)

- [usersDelete](users/usersDelete.md)
- [usersGetAll](users/usersGetAll.md)
- [usersGetById](users/usersGetById.md)
- [usersPost](users/usersPost.md)
- [usersPut](users/usersPut.md)

### Venues (/api/venues)

- [venuesDelete](venues/venuesDelete.md)
- [venuesGetAll](venues/venuesGetAll.md)
- [venuesGetById](venues/venuesGetById.md)
- [venuesPost](venues/venuesPost.md)
- [venuesPut](venues/venuesPut.md)

## ThunderClient API requests

For ThunderClient VS Code extension users, we offer JSON files to import API requests directly into the extension. See the ThunderClient requests folder for the API request collections.

How to use:

1. Download ThunderClient VS Code extension (ExtensionID: rangav.vscode-thunder-client, [or install here](https://marketplace.visualstudio.com/items?itemName=rangav.vscode-thunder-client) )
2. Select "Collections"
3. From menu (three bars), select "Import"
4. Navigate to ThunderClient requests folder
5. Select the API requests collection you want to import
6. Done!

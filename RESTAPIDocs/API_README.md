# REST API Documentation Templates

## What's in this documentation

This documentation contains endpoints to which you can make REST API requests. If an endpoint or a request is not specified, it cannot be requested through the API.

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

### Tickets (/api/tickets)

- [ticketsDelete](tickets/ticketsDelete.md)
- [ticketsGetAll](tickets/ticketsGetAll.md)
- [ticketsGetById](tickets/ticketsGetById.md)
- [ticketsPost](tickets/ticketsPost.md)
- [ticketsPut](tickets/ticketsPut.md)
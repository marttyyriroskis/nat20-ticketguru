# TicketGuru - User Roles and Stories

## User Roles

### Ticket Salesperson

- Can view events and available tickets
- Can sell and print tickets
- Can search for sold tickets and cancel them

### Event Coordinator

- Can view, edit, and add events
- Can access sales reports and ticket types

### Admin

- Can add, edit, and delete users and user roles
- Can see system reports and logs

## User Stories

For wireframe models, see TicketGuruUI.pdf.

### User Story 1

_"As a ticket salesperson, I want to see upcoming events and the tickets available to remain informed."_

__Acceptance criteria:__
- The ticket salesperson sees all upcoming events and relevant information: date, time, and event name
- The ticket salesperson sees the number of available tickets to an event

### User Story 2

_"As a ticket salesperson, I want to choose an event and any number of tickets to serve my customers."_

__Acceptance criteria:__
- The ticket salesperson can choose an event to show additional information (place, description, city, and ticket types)
- The ticket salesperson can choose any number of tickets per type to be sold to the selected event
- Tickets to fully booked events cannot be sold

### User Story 3

_"As a ticket salesperson, I want to print sold tickets to complete the transaction."_

__Acceptance criteria:__
- The ticket salesperson can print sold tickets
- The printed ticket contains all relevant information: sales event, time of purchase, sum, event, ticket type, price per ticket, and unique code per ticket

### User Story 4

_"As a ticket salesperson, I want to be able to search for a sold ticket in case of trouble."_

__Acceptance criteria:__
- The ticket salesperson can search for a sold ticket using the unique code
- The search results in all relevant information: sales event, time of purchase, event, ticket type, and price

### User Story 5

_"As a ticket salesperson, I want to be able to cancel a sold ticket, so that the customer can have their money back."_

__Acceptance criteria:__
- The ticket salesperson can cancel a ticket
- A cancelled ticket can no longer be used at an event

### User Story 6

_"As an event coordinator, I want to edit events in case there is a mistake."_

__Acceptance criteria:__
- The event coordinator can edit certain information in an event, like description, maximum number of tickets, ticket types, or prices

### User Story 7

_"As an event coordinator, I want to create a new event, so that tickets can be sold to the event."_

__Acceptance criteria:__
- The event coordinator can create a new event with all relevant information (event name, time, place, description, city, ticket types, prices, and maximum number of tickets to be sold)
- The event becomes visible to event coordinators and ticket salespersons with an accurate amount of tickets available

### User Story 8

_"As an event coordinator, I want to see sales reports, so that I can monitor sales to a certain event and make changes accordingly."_

__Acceptance criteria:__
- The event coordinator sees sales reports (tickets sold by type, sums, and particular sales)

### User Story 9

_"As an admin, I want to add users, so that people can use the system."_

__Acceptance criteria:__
- The admin can create new users with all relevant information (bare minimum: e-mail address and password)
- New users can log in

### User Story 10

_"As an admin, I want to edit or delete users, so that information about end users is accurate."_

__Acceptance criteria:__
- The admin can edit or delete users
- Information about edited users is accurate
- Information about deleted users is no longer available in the system

### User Story 11

_"As an admin, I want to add and edit user roles, so that only the people with the right to use the system are able to do so."_

__Acceptance criteria:__
- The admin can add, edit or remove roles from users
- Roles change accordingly
- User rights are defined by their roles

### User Story 12

_"As an admin, I want to see system reports and logs in case of trouble."_

__Acceptance criteria:__
- The admin can see system reports
- The admin can see logs

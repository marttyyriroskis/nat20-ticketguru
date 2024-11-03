package com.nat20.ticketguru.domain;

public enum Permission {
    // Sales permissions
    VIEW_SALES,
    EDIT_SALES,
    CREATE_SALES,
    DELETE_SALES,
    // Events permissions
    VIEW_EVENTS,
    EDIT_EVENTS,
    CREATE_EVENTS,
    DELETE_EVENTS,
    // Ticket type permissions
    VIEW_TICKET_TYPES,
    EDIT_TICKET_TYPES,
    CREATE_TICKET_TYPES,
    DELETE_TICKET_TYPES,
    // Ticket permissions
    VIEW_TICKETS,
    EDIT_TICKETS,
    CREATE_TICKETS,
    DELETE_TICKETS,
    USE_TICKETS,
    // Role permissions
    VIEW_ROLES,
    CREATE_ROLES,
    EDIT_ROLES,
    DELETE_ROLES,
    GRANT_PERMISSIONS,
    REVOKE_PERMISSIONS,
    // User permissions
    VIEW_USERS,
    CREATE_USERS,
    EDIT_USERS,
    DELETE_USERS,
    // Venue Permissions
    VIEW_VENUES,
    CREATE_VENUES,
    EDIT_VENUES,
    DELETE_VENUES,

    // add others as needed
}

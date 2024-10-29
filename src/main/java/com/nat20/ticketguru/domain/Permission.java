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
    
    // add others as needed
}

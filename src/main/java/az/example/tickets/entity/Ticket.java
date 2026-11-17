package az.example.tickets.entity;

import az.example.tickets.enums.TicketPriority;
import az.example.tickets.enums.TicketStatus;

import java.time.LocalDateTime;

public final class Ticket {
    private final Long id;
    private String title;
    private String description;
    private TicketStatus ticketStatus;
    private TicketPriority ticketPriority;
    private final LocalDateTime createdAt;


    // Burada Null exceptionlari qeyd edirik ki bosh qalmasinlar null value olanda error vere bilsin
    public Ticket(Long id, String title, String description, TicketPriority ticketPriority){
        if (id == null) {
            throw new IllegalArgumentException("Ticket id must not be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Ticket title must not be empty");
        }
        if (ticketPriority == null) {
            throw new IllegalArgumentException("Ticket priority must not be null");
        }
    this.id = id;
    this.title = title;
    this.description = description;
    this.ticketPriority = ticketPriority;
    this.ticketStatus = TicketStatus.OPEN;
    this.createdAt = LocalDateTime.now();


    }
}

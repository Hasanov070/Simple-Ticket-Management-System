package az.example.tickets;

import az.example.tickets.entity.Ticket;
import az.example.tickets.enums.TicketPriority;
import az.example.tickets.service.TicketService;

public class Main {
    public static void main(String[] args) {
        TicketService ticketService = new TicketService();

        Ticket ticket1 = ticketService.createTicket(
                101l,
                "wifi",
                "internet",
                TicketPriority.HIGH
        );

        Ticket ticket2 = ticketService.createTicket(
                101l,
                "wifi",
                "internet",
                TicketPriority.HIGH
        );
        System.out.println(ticket1);
        System.out.println(ticket2);

        }
    }

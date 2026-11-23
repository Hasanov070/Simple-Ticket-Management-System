package az.example.tickets;

import az.example.tickets.entity.Ticket;
import az.example.tickets.enums.TicketPriority;
import az.example.tickets.enums.TicketStatus;
import az.example.tickets.service.TicketMenu;
import az.example.tickets.service.TicketService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        TicketService ticketService = new TicketService();     // 1. servis
        SampleTickets.loadSampleTickets(ticketService);         // 2. test ticketləri
        TicketMenu menu = new TicketMenu(ticketService);        // 3. menyu
        menu.startMenu();




    }
}





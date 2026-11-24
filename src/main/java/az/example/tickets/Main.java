package az.example.tickets;


import az.example.tickets.service.TicketMenu;
import az.example.tickets.service.TicketService;


public class Main {
    public static void main(String[] args) {

        TicketService ticketService = new TicketService();     // 1. servis
        SampleTickets.loadSampleTickets(ticketService);         // 2. test ticketləri
        TicketMenu menu = new TicketMenu(ticketService);        // 3. menyu
        menu.startMenu();




    }
}





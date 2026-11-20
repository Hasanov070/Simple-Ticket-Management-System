package az.example.tickets;

import az.example.tickets.entity.Ticket;
import az.example.tickets.enums.TicketPriority;
import az.example.tickets.enums.TicketStatus;
import az.example.tickets.service.TicketService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        TicketService ticketService = new TicketService();
        Scanner scanner = new Scanner(System.in);

        SampleTickets.loadSampleTickets(ticketService);


        boolean engine = false;




        }
}





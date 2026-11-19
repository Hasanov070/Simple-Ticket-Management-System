package az.example.tickets;

import az.example.tickets.entity.Ticket;
import az.example.tickets.enums.TicketPriority;
import az.example.tickets.enums.TicketStatus;
import az.example.tickets.service.TicketService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Create scanner and TicketService object

        Scanner scanner = new Scanner(System.in);

        TicketService ticketService = new TicketService();

        // Step 2: Create three tickets
        Ticket ticket1 = ticketService.createTicket(
                101L,
                "Internet Problemi",
                "Internet işləmir!",
                TicketPriority.HIGH
        );

        Ticket ticket2 = ticketService.createTicket(
                102L,
                "Login Problemi",
                "Sisteme giriş edə bilmir",
                TicketPriority.LOW
        );

        Ticket ticket3 = ticketService.createTicket(
                103L,
                "Printer Problemi",
                "Printer işləmir!",
                TicketPriority.MEDIUM
        );


        ticketService.changeTicketStatus(101L, TicketStatus.IN_PROGRESS);

        System.out.println(ticket1);







        }
}


//        boolean engine = false;
//
//        while (engine){
//
//            // ------------------------MENU-------------------------
//            System.out.println("\n=============== BİLET İDARƏETMƏ SİSTEMİ ===============");
//            System.out.println("\n (1) Yeni bilet yarat ");
//            System.out.println("\n (2) Bütün biletləri göstər ");
//            System.out.println("\n (3) İdentifikasiya nömrəsinə görə bilet axtar ");
//            System.out.println("\n (4) Status görə biletləri göstər ");
//            System.out.println("\n (0) Proqramdan çıx");
//
//            System.out.print("\n Seçiminizi daxil edin: ");


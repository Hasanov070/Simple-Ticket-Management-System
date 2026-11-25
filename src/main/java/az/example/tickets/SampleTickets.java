package az.example.tickets;
import az.example.tickets.enums.TicketPriority;
import az.example.tickets.enums.TicketStatus;
import az.example.tickets.service.TicketService;



/*Ayrica sample tickets yaratmaq istedim ki, elimizde test uchun ticketler olsun, bundan elave olaraq
* user de istese bilet servisinden istifade edib deyishiklikler ede biler*/

public class SampleTickets {

    public static void loadSampleTickets(TicketService ticketService)
        {
            ticketService.createTicket(
                    1001L,
                    "Internet Problemi",
                    "İnternet işləmir",
                    TicketPriority.HIGH

            );

            ticketService.changeTicketStatus(
                    1001L,
                    TicketStatus.IN_PROGRESS
            );

            ticketService.createTicket(
                    1002L,
                    "Giriş Problemi",
                    "İstifadəçi hesabına daxil ola bilmir",
                    TicketPriority.HIGH

            );

            ticketService.changeTicketStatus(
                    1002L,
                    TicketStatus.CLOSED
            );

            ticketService.createTicket(
                    1003L,
                    "Printer Problemi",
                    "Printer çap etmir",
                    TicketPriority.HIGH
            );

            ticketService.changeTicketStatus(
                    1003L,
                    TicketStatus.IN_PROGRESS
            );

            ticketService.createTicket(
                    1004L,
                    "Server Problemi",
                    "Server işləmir",
                    TicketPriority.HIGH
            );

            ticketService.changeTicketStatus(
                    1004L,
                    TicketStatus.IN_PROGRESS
            );

            ticketService.createTicket(
                    1005L,
                    "Proqram Təminatı Problemi",
                    "Proqram açılmır",
                    TicketPriority.HIGH
            );
            ticketService.changeTicketStatus(
                    1005L,
                    TicketStatus.IN_PROGRESS
            );

            ticketService.createTicket(
                    1006L,
                    "Şəbəkə Problemi",
                    "Şəbəkə bağlantısı yoxdur",
                    TicketPriority.HIGH
            );
            ticketService.changeTicketStatus(
                    1006L,
                    TicketStatus.IN_PROGRESS
            );


        }
}

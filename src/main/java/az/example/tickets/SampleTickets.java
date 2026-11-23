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
                    TicketStatus.IN_PROGRESS,
                    TicketPriority.HIGH

            );

            ticketService.createTicket(
                    1002L,
                    "Giriş Problemi",
                    "İstifadəçi hesabına daxil ola bilmir",
                    TicketStatus.CLOSED,
                    TicketPriority.HIGH

            );

            ticketService.createTicket(
                    1003L,
                    "Printer Problemi",
                    "Printer çap etmir",
                    TicketStatus.OPEN,
                    TicketPriority.HIGH

            );

            ticketService.createTicket(
                    1004L,
                    "Server Problemi",
                    "Server işləmir",
                    TicketStatus.OPEN
                    TicketPriority.HIGH

            );

            ticketService.createTicket(
                    1005L,
                    "Proqram Təminatı Problemi",
                    "Proqram açılmır",
                    TicketStatus.CLOSED,
                    TicketPriority.HIGH

            );

            ticketService.createTicket(
                    1006L,
                    "Şəbəkə Problemi",
                    "Şəbəkə bağlantısı yoxdur",
                    TicketStatus.IN_PROGRESS,
                    TicketPriority.HIGH



            );


        }
}

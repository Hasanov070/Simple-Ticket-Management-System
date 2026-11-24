package az.example.tickets.service;

import az.example.tickets.entity.Ticket;
import az.example.tickets.enums.TicketStatus;
import az.example.tickets.enums.TicketPriority;

import az.example.tickets.exception.DuplicateTicketException;
import az.example.tickets.exception.TicketNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*Burada ilk defe Junit test yazaraq yazdigimiz metodlari funksiyanliqlari yoxlayaraq oyrenmeye chalishacam. Ilkin etapda biz @Test istifade ediriik
* Bu o demekdirki, @Test JUnite deyir ki, bu method adi method deil, yoxlanilmali olan test methodudur. JUnit de ozu bu methodu tapib ishe salir.Bize main.javani run
* etdirmeye gerek duymur.*/
public class TicketServiceTest {

    /*Burada sade bir shekilde biz methodumuzu chaririq meselen burada ilkin etapda men biletin yaranib yaranmadigini yoxlayiram. Istifadechi bilet yaradan zaman
    * ID, title, description, status, ve priority sechir burdada eyni qayda da edirik.*/
    @Test
    void createTicketTest(){

        TicketService ticketService = new TicketService();

        Ticket ticket = ticketService.createTicket(
                1001L,
                "İnternet işləmir",
                "Router da problem var",
                TicketStatus.OPEN,
                TicketPriority.HIGH
        );

        assertEquals("İnternet işləmir", ticket.getTitle());

        assertEquals("Router da problem var", ticket.getDescription());

        assertEquals(TicketPriority.HIGH, ticket.getPriority());

        assertEquals(TicketStatus.OPEN, ticket.getStatus());

        assertEquals(1001L, ticket.getId());


        /*Bu hissede ise assertEquals methodundan istifade ederek iki cavabinda eyni olub olmadigin yoxlayiriq. Meselen men burda sadece title larida yoxlaya bilerdim,
        * amma istedim ki diger fieldleri yoxlayib gorek her biri duzgun shekilde yaradacaqdirmi*/

    }

    @Test
    void changeTicketStatusTest(){
        TicketService ticketService = new TicketService();

        Ticket ticket = ticketService.createTicket(
                1001L,
                "Wi-Fi Problem",
                "Router problem",
                TicketStatus.OPEN,
                TicketPriority.HIGH
        );

        ticketService.changeTicketStatus(
                1001L,
                TicketStatus.CLOSED
        );

        assertEquals(TicketStatus.CLOSED, ticket.getStatus());
    }

    @Test
    void updateTicketTest(){
        TicketService ticketService = new TicketService();

        Ticket ticket = ticketService.createTicket(
                1001L,
                "Wi-Fi Problem",
                "Router problem",
                TicketStatus.OPEN,
                TicketPriority.HIGH
        );

        ticketService.updateTicket(
                1001L,
                "Computer Problem",
                "Computer is slow",
                TicketPriority.LOW
        );

        assertEquals("Computer Problem", ticket.getTitle());

        assertEquals("Computer is slow", ticket.getDescription());

        assertEquals(TicketPriority.LOW, ticket.getPriority());

        assertEquals(TicketStatus.OPEN, ticket.getStatus());

        assertEquals(1001L, ticket.getId());
    }


    @Test
    void deleteTicketTest(){
        TicketService ticketService = new TicketService();

        Ticket ticket = ticketService.createTicket(
                1001L,
                "İnternet işləmir",
                "Router da problem var",
                TicketStatus.OPEN,
                TicketPriority.HIGH
        );

        ticketService.deleteTicket(1001L);

        assertTrue(ticketService.getAllTickets().isEmpty());

        /*Bu hissede esas anlayishimiz bundan ibaretdir ki, biz assertTrue bize iki valuenin qiymetleri eynidirse bize True value qaytaracaq bununla birlikde biz hemin
        * cavabin real olub olmadigin gore bilirik.*/
    }
/* Burdada biz esas exceptionlari test edeceyik. Sadece olaraq esas ferq burada assertThrows methpdundan istifade ederek
* meqsedimiz, yazdigimiz kodun gozlediyimiz exceptioni atib atmadigini gormekdir. Burdada expetionThrows komek edir
* assertThrows(expectedException, executable); bununla birlikde biz gore bilirik.
*
* TicketNotFoundException.class bes bu nedir buda o demekdirki assertThrows gozleyirki TicketNotFoundException bele bir exception gelecek
* Eger gelse demeli testi pass eliiyr gelmees demeli elemir.
* */

    @Test
    void TicketNotFoundTest(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(
                1001L,
                "İnternet işləmir",
                "Router da problem var",
                TicketStatus.OPEN,
                TicketPriority.HIGH

        );

        assertThrows(
                TicketNotFoundException.class, () -> ticketService.getTicketById(99999L)
        );
    }

    @Test
    void DuplicateTicketTest(){
        TicketService ticketService = new TicketService();

        ticketService.createTicket(
                1001L,
                "İnternet işləmir",
                "Router da problem var",
                TicketStatus.OPEN,
                TicketPriority.HIGH
        );

        assertThrows(
                DuplicateTicketException.class, () -> ticketService.createTicket(

                        1001L,
                        "İnternet işləmir",
                        "Router da problem var",
                        TicketStatus.OPEN,
                        TicketPriority.HIGH
                )
        );

    }

}

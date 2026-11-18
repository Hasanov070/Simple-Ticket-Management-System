package az.example.tickets.service;

import az.example.tickets.entity.Ticket;
import az.example.tickets.enums.TicketPriority;
import az.example.tickets.enums.TicketStatus;
import az.example.tickets.exception.DuplicateTicketException;
import az.example.tickets.exception.TicketNotFoundException;

import java.util.*;

public class TicketService {

    /*Biz Overall lahiyeye baxsaq gorerik ki, bizden istenilen funksiyalar uchun ID onemlidi
    * createTicket() - > yeni ticket yaradir
    * getAllTickets() - > butun ticketleri bize gosterir geri qaytarir
    * getTicketByID() - > ID uzre Ticket axtarir
    * getTicketsByStatus() - > Status gore filter tetbiq edir
    * getHighPriorityTickets() - > HIGH prioritetli ticketleri tapir ve gosterir
    * changeTicketStatus() - > Ticketin statusunu deyishir
    * deleteTicket() - > Ticketi silir
    *
    *
    * Burdan gormush olariq ki, bize emeliyatlar uchun ne tezlik onemlidi. Bu sebebden anlayiriqki
    * HashMap O(1) vaxt teleb edir ortalama amma Digerleri O(n) daha yavash. Elave olaraq bizde
    * biz geleceye yonumlu bir applikasiya yaziriqsa bizde TicketService mumkun qeder istediyimiz soruglara tez bir shekilde cavab vermeyi vacibdir
    * Bu sebebden HashMap da sira uygunlugu olmadigindan LinkedHashMap istifade elemek daha uygun olarki gelecek de funksiyanliglara meselen istifadechi
    * terefinden yaranmish son iki bilet uzre emeliyyatlari tapmaq ve uzerinde ishlemek uchun daha elverishli olacaqdir. Bu hissede men ozumde try ederek
    * oyreneceyem*/


    private final Map<Long, Ticket> tickets = new LinkedHashMap<>(); // private final adinini vermekde esas sebebimiz odurki, IDler deyishilmesin
    // diger classlar terefinden mudaxile olunmasin

// Biz yeni ticket yaradanda esas meqsedimiz yaranan ticketin idsinin sistemde olub olmadigini yoxlamaqdirki,
    // bu sebebden customer exception vermishik ki, bunu anlaya bilsin.
    public createTicket(
            Long id,
            String title,
            String description,
            TicketPriority ticketPriority
    )
    {
     if (tickets.containsKey(id)){
         throw new DuplicateTicketException(id);
     }

     Ticket ticket = new Ticket(id, title, description, ticketPriority);
// Ashagida qeyd olunan map.put(key, value) hansiki biz id ve ticketin valuesin birlikde qeyd edirikki ticketle
        //bagli olan ishleri rahat gore bilek LinkedHashMap da istifade olunan bir tooldu.
     tickets.put(id, ticket);

     // Return ticketin sebebi odurki bu yaratdigimiz funksiya sonda bize bir bilet qaytarmalidi ve biz bileti
        // istifade ede bilmeliyik ona gore sonda return ticket edirik.

        /*Sual yarana bilerki, bes biz LinkedHashMap istifade edirik onsuz bunu saxlaycaqda niye return edirik
        * axi?  "tickets.put(id, ticket); funksiyasi sadece obyekti linkedhashmapda saxlayir. Bize lazimdirki
        * Main.java faylinda istifade ede bilek ona gore return etmeliyik.*/

        /*Elave bir sual da yarana bilerki eger biz return ticket yazmasaydiq ne olardi?
        * Hemin momement bize error qaytayacaqdi. Chunki metodun evvelinde ticket yazmishiq sonra hechne yazmamishiq
        * Java bizden sonda nese gozleyir gondermekle bagli. Elave de demek olarki gozlmemesi uchun void yaza bilerik.
        * Dogrudu elemek olar. Ama ele oldugu halda void bize obyektin ozun qaytarmir. Buda bizim ishimizi uzadir.*/
     return ticket;
    }



}

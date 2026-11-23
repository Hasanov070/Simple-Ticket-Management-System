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
    public Ticket createTicket(
            Long id,
            String title,
            String description,
            TicketStatus ticketStatus,
            TicketPriority ticketPriority
    )
    {
     if (tickets.containsKey(id)){
         throw new DuplicateTicketException(id);
     }



     Ticket ticket = new Ticket(id, title, description, ticketStatus,ticketPriority);
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
// GetAllTickets funksiyasi.Oyrenildiyi qeder biz goruruk ki, elimizde 3 cur listing funksiyasi var
    /* Bunlar ArrayList, LinkedList ve Hashsetdir. LinkedHashMapping istifade eledik ki, ticketlerimizi saxlasin
    Amma getAllTicket funksiyasin biz ishledende goruruk ki bize sira uygunlugu lazimdi. Bu sebebden bize en uygun arraylist
    olacaqdir. Linkedlist de esas ustunluk qonshulari ile elaqede oldugu uchun yeni bir data (node) elave etdiyi hallda
    O(1) derecede ustunluk verir. Sadece bize butun listi gostermek lazim gelende ise sadece ArrayList kifayet edecekdir.
    */
    public List<Ticket> getAllTickets(){
        return new ArrayList<>(tickets.values());
    }

    public Ticket getTicketById(Long id){

        Ticket ticket = tickets.get(id);

        if (ticket == null){

            throw new TicketNotFoundException(id);
        }

        return ticket;
    }

    // getTicketByStatus funksiyasi

    public  List<Ticket> getTicketByStatus(TicketStatus ticketStatus){

/*Biz oyrendiyimiz kimi goruruk ki, biz melumatlari mapde 0, 1, 2 kimi indekslere goture bilmerik. Chunki
* LinkedHashMapda key ve value kimi saxlanaliyir deye bize bashqa bir usul lazimdir. Bu sebebden biz yeni
* ArrayList yaradiriqki getter methodu istifade ederek valuelari oxuya bilek bundan elave olaraqda siralaya bilek
* */
        if(ticketStatus == null){

            throw new IllegalArgumentException("Status boş ola bilməz!");

        }

        List<Ticket> allTickets = new ArrayList<>(tickets.values());

        List<Ticket> result = new ArrayList<>();

        for (int i = 0; i < allTickets.size(); i++){

            // Ashagida verilen kodda gore bilirik ki, Ticket deyishenin data type i ticket deyishenin adi
            //allTickets.get(i) hemin indeksi elementi qaytarir

            Ticket ticket = allTickets.get(i);

            if(ticket.getStatus() == ticketStatus){
                result.add(ticket);
            }
        }
        return result;
    }


    // getHighPriorityTickets
    // Burda da eyni qayda da oldugu kimi yeni array yaradib ozun uzerinde ishleyirk. Yeni parametr
    // Olaraq result yazib neticeni resultla qeyd edirik


    public List<Ticket> getTicketsByPriority(TicketPriority ticketPriority) {

        if (ticketPriority == null) {
            throw new IllegalArgumentException("Priority boş ola bilməz!");
        }

        List<Ticket> result = new ArrayList<>();

        List<Ticket> allTickets = new ArrayList<>(tickets.values());

        for (int i = 0; i < allTickets.size(); i++) {

            Ticket ticket = allTickets.get(i);

            if (ticket.getPriority() == ticketPriority) {
                result.add(ticket);
            }
        }

        return result;
    }

    /*Burada sual yarana bilerki diger methodlarda biz void istifade elemedik amma burda ishledirik
    * sebeb nedir? Sebeb ondan ibaretdirki void olmayanda biz valueni saxlaya bilirik. Indi burda saxlamaq
    * lazim deil sadece statusun deyishirik. Buna gorede bize void lazimdir. Biz voidsiz de yaza bilerdik uygundurda
    * sadece geriye bize nese qaytarmali idi business logicde bizden ele bir shey teleb olunmadigindan biz bele edirik.*/
    public void changeTicketStatus(Long id, TicketStatus newticketStatus){

        Ticket ticket = getTicketById(id);
        ticket.changeTicketStatus(newticketStatus);

    }
// Evvelki methoddan oyrenidyimiz kimi eyni mentiqle istifade edeceyik, changeStatus eledikde void istifade etmishdik
    // bize geri nese qaytarmagin istemirdik. Burda da biz sadece delete ediirik ticketi geri value qaytarmamaldi.
    public void deleteTicket(Long id){

        getTicketById(id);

        tickets.remove(id);
    }



}

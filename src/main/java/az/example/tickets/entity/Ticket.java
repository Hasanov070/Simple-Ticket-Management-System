package az.example.tickets.entity;

import az.example.tickets.enums.TicketPriority;
import az.example.tickets.enums.TicketStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Ticket {


    private final Long id; // Biletin IDsi
    /*Sual yarana bilerki neye gore (private final) istifade eledik chunki
    Mentiqe esasen bilete ID verildikden sonra deyishmemek lazimdir. Private final bunu edir.*/
    private String title; // Biletin bashligi

    private String description; // Bilet haqqda melumat

    private TicketStatus ticketStatus; // Biletin Statusu

    private TicketPriority ticketPriority; //Biletin vacibliyi

    private final LocalDateTime createdAt; // LocalDateTime java-da tarix ve saati saxlamaq uhcun
    // Istifade olunur. Burdada eyni qayda ile ticketin zamani yaradildiqdan sonra onu deyishmek olmaz

    //private olmasi sayesinde bashqa classlar bu fieldleri bir basha deyishe bilmez



// Constructor, men burda bele bir yanlishiq eledim ki constructor ichine bir method yazmishdim. Bunu etmek olmaz
    // Biz oyrenmishdikki constructor yeni obyekt yaradilarken onun ilkin melumatlarini teyin edir.

    public Ticket(Long id, String title, String description, TicketStatus ticketStatus, TicketPriority ticketPriority){
        this.id = id; // istifadechi terefinden melumat verilir
        this.title = title; // istifadechi terefinden melumat verilir
        this.description = description; // istifadechi terefinden melumat verilir
        this.ticketPriority = ticketPriority; // istifadechi terefinden melumat verilir

        // Burda ise eger istifadechi value vermese onda TicketStatus OPEN OLARAQ QALACAQ
        if (ticketStatus != null) {
            this.ticketStatus = ticketStatus;
        } else {
            this.ticketStatus = TicketStatus.OPEN;
        }

        this.createdAt = LocalDateTime.now();


        // Burada Null exceptionlari qeyd edirik ki bosh qalmasinlar null value olanda error vere bilsin

        if (id == null) {
            throw new IllegalArgumentException("Ticket id must not be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Ticket title must not be empty");
        }
        if (ticketPriority == null) {
            throw new IllegalArgumentException("Ticket priority must not be null");
        }


// Burada adindan da gorunduyu kimi ticketin ilkin OPEN olan adini process e gore deyishmek metodun yaziriq
        // burada bir exception yazib deyirkki ticketin valuesi null ola bilmez
        /* Bele bir sual yarana bilerki bes biz getter ve setter oyrenmishik onun yerine setter de istifade ede bilerdik. Niye istifade elemirik?
        Mesele orasindadirki, kodu oxuyan adam uchun ve gelecekde lazimi deyishikler etmek uchun changeTicketStatus methodun yazmishiqki hem diger
        developer uchun oxuyan zaman rahat olsun rahat anlaya bilsin hemde metoda elave deyishikler ede bilerik. Sadece bir funksiya uchun xidmet edir*/

        }

    // Yuxarida behs olunan hemin method, indi tam duzgun yerindedir.
    // Bu methodun esas ishi yaranmish ticketin statusunu deyishdirmekdir.
    public void changeTicketStatus(TicketStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException(
                    "Biletin statusu boş ola bilməz!"
            );
        }
        this.ticketStatus = newStatus;
    }



        //Burada getter metodundan istifade ederek private valuelarimizi oxuya bilceyik
        public Long getId() { return id; } // Ticketin IDsin qaytarir
        public String getTitle() { return title; } // Ticketin adin qaytarir
        public String getDescription() { return description; } // Ticketin haqqda yazilmish melumatlari qaytarir
        public TicketStatus getStatus() { return ticketStatus; } //Ticketin hazirki statusunu qaytarir
        public TicketPriority getPriority() { return ticketPriority; } // Ticketin prioritetini qaytarir
        public LocalDateTime getCreatedAt() { return createdAt; }   // Ticketin hansi saatda ve tarixde yarandigini qaytarir

        /* Sual yarana bilerki biz neye gore return istifade edirik, bunun yerine sout da yaza bilerdik ki bize value ni versin.
        * Amma bele bir mesele varki biz biletin IDsi ile yoxlanishlar edeceyik ona gore return qaytarmalidi ki biz hemin value ile test ede bilek*/



// Burda esas meqsedimiz String librarysinden istifade etdiyimiz valuelari bize oxuna bileceyimiz shekilde geri qaytarmasini istyeirik.
    @Override
                public String toString(){
            return "Ticket{id = " + id
                    + ", title = '" + title + '\''
                    + ", status = " + ticketStatus
                    + ", priority = " + ticketPriority
                    + ", createdAt = " + createdAt.format(
                                            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                    + '}';
        }


}



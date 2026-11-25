package az.example.tickets.service;

import az.example.tickets.entity.Ticket;
import az.example.tickets.enums.TicketPriority;
import az.example.tickets.enums.TicketStatus;
import az.example.tickets.exception.DuplicateTicketException;
import az.example.tickets.exception.TicketNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TicketMenu {

    private final TicketService ticketService;
    /* TicketServicesin daxilinde metodlarimiz var, hansiki ticketlerle bagli esas emeliyyatlari yerine yetirir.
  ticketService ise TicketService obyektine muraciet etmek uhcun istifade etdidyimiz fielddir.
  Neye gore final istifade eledik, bu field bir defe hansi ticketeService obyektine baglanibsa sonradan bashqa
  TicketService obyektine baglana bilmez. Amma bu o demek deilki ichinde melumatlari deyishmek olmaz,
  ancaq fieldin bashqa obyektine yoneldirilmesini qadagan edir.
  * */
    private Scanner scanner;
// Scanner - java hazir class in adidir
// scanner - biz verdiyimiz fieldin adidir.

    /* Biz burda scanner obyekti yaratmiriq, sadece TicketMenu classina deyirk ki, istifadechilerin yazdiqlarini oxumaq
    uchun scanner fieldimiz olacaq.
    * */


    public TicketMenu(TicketService ticketService) {
        this.ticketService = ticketService;
        this.scanner = new Scanner(System.in);
    }


    public void startMenu() {
        boolean engine = true;
        // burda biz boolean valuesinden istifade edirk ki, program false olana kimi while ile ishlesin

        while (engine) {
            printMenu();
            try {
                /*Istifadechinin sechimlerini oxuyur, trim ise istifadechinin yazanda yazdigi melumatin yanindaki
                 * boshluqlari silir. meselen (   1) yazirsa "1" kimi oxunur.*/
                String choice = scanner.nextLine().trim();

                // Hansi secim edilibse, uygun methodu cagiririq
                switch (choice) {
                    case "1" -> createTicket();
                    case "2" -> showAllTickets();
                    case "3" -> findTicketById();
                    case "4" -> filterByStatus();
                    case "5" -> filterByPriority();
                    case "6" -> changeStatus();
                    case "7" -> deleteTicket();
                    case "8" -> sortByCreatedTime();
                    case "9" -> updateTicket();
                    case "0" -> {

                        System.out.println("Proqramdan çıxıldı!");
                        engine = false;
                    }
                    // Eger istifadechi ferqli bir value yazarsa o zaman default ishleyecek
                    default -> System.out.println("Yanlış seçim! 0-9 arasi rəqəm daxil edin!");

                }
            }
            // Burada error xetanin obyetktini saxlayan valuedi
            catch (TicketNotFoundException error) {
                System.out.println("XƏTA: " + error.getMessage());
            } catch (DuplicateTicketException error) {
                System.out.println("XƏTA: " + error.getMessage());
            } catch (IllegalArgumentException error) {
                System.out.println(
                        "DÜZGUN OLMAYAN MƏLUMAT: " + error.getMessage()
                );
            } catch (NoSuchElementException error) {
                // Giris axini bitibse (meselen Ctrl+D) proqrami sondur
                System.out.println("Giriş bitdi. Proqram dayandırıldı.");
                engine = false;
            }
        }
    }


    //--------------------------------MENU-------------------------------------------------
    // Consoleda menuynun soutlari
    private void printMenu() {
        System.out.println();
        System.out.println("===== Bilet Sistem Menyusu =====");
        System.out.println("1. Bilet yarat");
        System.out.println("2. Bütün biletləri göstər");
        System.out.println("3. Biletin identifikasiya nömrəsinə üzrə axtar");
        System.out.println("4. Bileti status-a görə filter et");
        System.out.println("5. Bileti priority-ə görə filter et");
        System.out.println("6. Biletin statusun dəyis");
        System.out.println("7. Bileti sil");
        System.out.println("8. Biletləri zamana görə sırala");
        System.out.println("9. Biletlərin başlığın, açıqlamasın, vacibliyini dəyişdir");
        System.out.println("0. Əməliyyatı sonlandır");
        System.out.print("Seçim edin!: ");
    }


    //------------------------------------------SERVICE-------------------------
// Bu hisseden etibaren TicketService de yaratdigimiz valuelari chagiririq
    private void createTicket() {
        Long id = readLong("İdentifikasiya nömrəsi: ");

        System.out.print("Başlıq: ");
        String title = scanner.nextLine().trim();

        System.out.print("Açıqlama: ");
        String description = scanner.nextLine().trim();

        TicketPriority priority = readPriority();

        // Yeni ticket-in ilkin statusu TicketService daxilinde OPEN olaraq teyin edilir.
        Ticket ticket = ticketService.createTicket(id, title, description, priority);
        System.out.println("Bilet yaradıldı: " + ticket);
    }

    // Butun biletleri gosterir
    private void showAllTickets() {
        printTickets(ticketService.getAllTickets());
    }

    // Biletleri IDsine gore tapir
    private void findTicketById() {
        Long id = readLong("Axtarılan İdentifikasıya nömrəsi: ");
        System.out.println(ticketService.getTicketById(id));
    }

    // Statusa gore filterleyir
    private void filterByStatus() {
        TicketStatus ticketStatus = readStatus();
        printTickets(ticketService.getTicketByStatus(ticketStatus));
    }

    // Prioritiye gore filterleyir
    private void filterByPriority() {
        TicketPriority priority = readPriority();
        printTickets(ticketService.getTicketsByPriority(priority));
    }

    // Biletin statusunu deyishdiryir
    private void changeStatus() {
        Long id = readLong("Statusu dəyişiləcək biletin İdentifikasıya nömrəsi: ");
        Ticket ticket = ticketService.getTicketById(id);
        System.out.println("Hazırkı status: " + ticket.getStatus());


        TicketStatus newStatus = readStatus();
        ticketService.changeTicketStatus(id, newStatus);
        System.out.println("Biletin statusu dəyişdirildi: " + ticketService.getTicketById(id));

    }


    private void deleteTicket() {
        Long id = readLong("Silinəcək İdentifikasıya nömrəsi: ");
        ticketService.deleteTicket(id);
        System.out.println("Bilet silindi. İdentifikasıya nömrəsi: " + id);

    }

    private void sortByCreatedTime() {


        System.out.println("1. Ən birinci yaradılan");
        System.out.println("2. Ən sonuncu yaradılan");


        String choice = scanner.nextLine();

        List<Ticket> list = ticketService.getTicketsSortedByCreatedAt();

        if (choice.equals("1")) {

            printTickets(list);

        } else if (choice.equals("2")) {

            Collections.reverse(list);

            printTickets(list);

        } else {

            System.out.println("Yanlış seçim! 1 və yaxud 2 arasi rəqəm daxil edin!");


        }

    }

/*Burada Updating ticket yaratdim, hansiki istifadechi istese biletin biletin bashligin, achiqlamasin ve prioitisin deyishe biler
* bununla birlikde elchatandir.*/

    private  void  updateTicket(){
        Long id = readLong("Yenilənəcək biletin İdentifikasiya nömrəsi: ");

        Ticket ticket = ticketService.getTicketById(id);

        System.out.println("Hazırki bilet: " + ticket);

        System.out.print("Yeni başlıq ");
        String newTitle = scanner.nextLine().trim();

        System.out.print("Yeni açıqlama ");
        String newDescription = scanner.nextLine().trim();

        TicketPriority newTicketPriority = readPriority();

        ticketService.updateTicket(
                id,
                newTitle,
                newDescription,
                newTicketPriority
        );

        System.out.println("Bilet yeniləndi: " + ticket);
    }


    //--------------------------------------------- HELPER METHODS ---------------------------------------------

    /*TicketService classinin ichinde ticketService.getAllTicket(); methodumuz vardi. Amma bize lazimdirki, hemin siyahini
     * ekranda gosterek ona gore de biz printTickets metodunu yaratmishiq*/
    private void printTickets(List<Ticket> tickets) {

        if (tickets.isEmpty()) {
            System.out.println("Bilet tapılmadı.");
        } else {
            int i = 0;
            /*Eger siyahi bosh deilse, ichersiindeki butun ticketleri bir bir gostermek isteyirik
             * int i = 0, birinci ticketden bashlayir
             * tickets.size neche ticket oldugunu gosterir
             * tickets.get(i) siyahdindan hemin nomreli ticketi goturur*/
            while (i < tickets.size()) {
                System.out.println(tickets.get(i));

                i++;
            }
        }
    }

    // Esas meqsedimiz userden duzgun bir cavab almaqdir.
    private Long readLong(String message) {

        // Sual yarana bilerki, neye while true yazmisiq, chunki ilkin etapda biz ID teleb edirik, her shey ID ile bashlayir
        // bu sebebden istifadechiden duzgun reqem alana kimi etmeliyik
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                return Long.parseLong(input); // String formatinda yazilmish ededi long tipine chevirmekdir.

            } catch (
                    NumberFormatException error) { // adindan da belli oldugu kimi yanliz eded formatina chevrilmesi uchun xetadi
                System.out.println("Zəhmət olmasa yalnız rəqəm daxil edin!");
            }
        }
    }


    private TicketStatus readStatus() {

        while (true) {
            System.out.println("Status seçin:");
            System.out.println("1. OPEN");
            System.out.println("2. IN_PROGRESS");
            System.out.println("3. CLOSED");

            String statusChoice = scanner.nextLine();

            if (statusChoice.equals("1")) {
                return TicketStatus.OPEN;
            } else if (statusChoice.equals("2")) {
                return TicketStatus.IN_PROGRESS;
            } else if (statusChoice.equals("3")) {
                return TicketStatus.CLOSED;
            } else {
                System.out.println("Yanlış seçim! Yenidən cəhd edin. 1 və 3 arası verilmiş statuslardan seçin");
            }
        }
    }

    private TicketPriority readPriority() {

        while (true) {
            System.out.println("Priority secin:");
            System.out.println("1. LOW");
            System.out.println("2. MEDIUM");
            System.out.println("3. HIGH");

            String priorityChoice = scanner.nextLine();

            if (priorityChoice.equals("1")) {
                return TicketPriority.LOW;
            } else if (priorityChoice.equals("2")) {
                return TicketPriority.MEDIUM;
            } else if (priorityChoice.equals("3")) {
                return TicketPriority.HIGH;
            } else {
                System.out.println("Yanlış seçim! Yenidən cəhd edin. 1 və 3 arası verilmiş statuslardan seçin");
            }
        }
    }

}

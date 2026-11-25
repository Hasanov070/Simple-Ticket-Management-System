# Java Core — Öyrəndiklərim  25.11.26

  

**Layihə:** Simple Ticket Management System

**Tarix:** 25.11.2026

**Repository:** [GitHub](https://github.com/Hasanov070/Simple-Ticket-Management-System)

  

Bu sənəddə Ticket Management System layihəsi üzərində öyrəndiyim mövzuları, etdiyim dəyişikliklərin səbəblərini və növbəti yoxlamaları qeyd edirəm. Kod nümunələri son lokal dəyişikliklərə və repository-dəki mövcud metodlara əsaslanır.

  

## 1. Build & Compilation

  

### Method signature

  

Öyrəndim ki, Java-da metodun imzası onun adı, parametrlərinin tipləri və ardıcıllığı ilə müəyyən edilir. Return type metod imzasına daxil deyil.

  

Əvvəl `createTicket()` beş parametr qəbul edirdi. Biznes qaydasını Service daxilində tətbiq etmək üçün status parametrini sildim:

  

```java

public  Ticket  createTicket(Long id, String title, String description,

TicketPriority ticketPriority)

```

  

Metodun imzası dəyişdikdə onu çağıran `TicketMenu`, `SampleTickets` və `TicketServiceTest` class-larındakı argumentləri də uyğunlaşdırmaq lazımdır. Əks halda compilation error yaranır.

  

### Maven və build

  

Maven layihənin dependency-lərini və build prosesini idarə edir. `pom.xml` faylında Java versiyası və JUnit, Gson kimi dependency-lər göstərilir.

  

```bash

mvn  clean  package

```

  

-  `clean`: əvvəlki build nəticələrini silir.

-  `compile`: əsas Java kodunu bytecode-a çevirir.

-  `test-compile`: test kodunu compile edir.

-  `test`: konfiqurasiya edilmiş testləri işə salır.

-  `package`: layihəni paketləyir.

  

İlkin build zamanı aldığım real nəticə:

  

```text

[INFO] Building jar: .../target/ticket-management-system-1.0-SNAPSHOT.jar

[INFO] BUILD SUCCESS

[INFO] Total time: 8.831 s

[INFO] Finished at: 2026-11-25T17:01:47+04:00

```

  

Bu nəticə əvvəlki build-in uğurlu olduğunu göstərir. Son dəyişikliklərdən sonra build və testləri təkrar icra etməliyəm. BUILD SUCCESS bütün biznes qaydalarının avtomatik olaraq düzgün olması demək deyil.

  

## 2. Business Logic

  

### Yeni ticket-in ilkin statusu

  

Əsas biznes qaydamız yeni ticket-in `OPEN` statusunda yaradılmasıdır. Əvvəl statusu metodu çağıran class göndərə bilirdi. Bu səbəbdən console menu-dan kənarda yeni ticket birbaşa `CLOSED` statusunda yaradıla bilərdi.

  

Problemi Service səviyyəsində həll etmək üçün status parametrini sildim və `OPEN` dəyərini obyekt yaranarkən Service daxilində təyin etdim:

  

```java

public Ticket createTicket(Long id, String title, String description,

TicketPriority ticketPriority) {

if (tickets.containsKey(id)) {

throw new DuplicateTicketException(id);

}

Ticket ticket = new Ticket(id, title, description,

TicketStatus.OPEN, ticketPriority);

tickets.put(id, ticket);

return ticket;

}

```

  

Bu yanaşmada `TicketMenu` yalnız ID, title, description və priority göndərir:

  

```java

Ticket ticket = ticketService.createTicket(id, title, description, priority);

```

  

Nümunə ticket-in fərqli son statusu olmalıdırsa, əvvəl ticket yaradılır, sonra statusu dəyişdirilir:

  

```java

ticketService.createTicket(1001L, "Internet Problemi",

"İnternet işləmir", TicketPriority.HIGH);

ticketService.changeTicketStatus(1001L, TicketStatus.IN_PROGRESS);

```

  

Öyrəndim ki, ticket yaratmaq və mövcud ticket-in statusunu dəyişmək ayrı əməliyyatlardır. Bu qayda Service-dən istifadə edən bütün class-lar üçün eyni olmalıdır. `Ticket` constructor-u ayrıca status qəbul edirsə, birbaşa `new Ticket(...)` çağırışı Service qaydasından kənarda qalır; hazırkı tapşırıq Service çağırışlarına aiddir.

  

`createTicketTest()` daxilində `assertEquals(TicketStatus.OPEN, ticket.getStatus())` yazmaqla console menu olmadan bu qaydanı yoxlayıram. Testin uğurlu icrası hələ təsdiqlənməlidir.

  

## 3. Collection, filtering və object reference

  

`TicketService` daxilində ticket-ləri ID-yə görə `LinkedHashMap`-də saxlayıram. Bu struktur əlavə olunma ardıcıllığını qoruyur və ID-yə görə axtarış, əlavə etmə və silmə əməliyyatları orta hesabla O(1) vaxt tələb edir.

  

```java

private final Map<Long, Ticket> tickets = new LinkedHashMap<>();

```

  

`Map.values()` xəritədəki dəyərlərə baxmaq üçün collection view qaytarır. Bütün ticket-ləri ayrıca müvəqqəti siyahıya köçürmədən enhanced for-loop ilə yoxlaya bilirəm:

  

```java

List<Ticket> result = new ArrayList<>();

for (Ticket ticket : tickets.values()) {

if (ticket.getStatus() == ticketStatus) {

result.add(ticket);

}

}

return result;

```

  

Filtering bütün n ticket-i yoxladığı üçün O(n) vaxt tələb edir. Nəticə üçün k uyğun ticket varsa, O(k) əlavə siyahı yaddaşı istifadə olunur. Yeni Ticket obyektləri yaradılmır; mövcud obyektlərə istinadlar saxlanılır.

  

`getAllTickets()` isə `new ArrayList<>(tickets.values())` vasitəsilə yeni siyahı yaradır. Siyahının strukturu ayrıdır, amma daxilindəki Ticket obyektləri eynidir. Bu fərqi anlamaq object reference mövzusu üçün vacibdir.

  

## 4. JUnit 5 və Unit Testing

  

JUnit 5 Java kodunun avtomatik test edilməsi üçün framework-dür. JUnit Platform testlərin işə salınması üçün infrastruktur, Jupiter test yazmaq üçün API və icra mexanizmi, Vintage isə köhnə JUnit testləri üçün dəstək təmin edir. Layihəmdə JUnit Jupiter istifadə olunur.

  

`@Test` annotasiyası metodun test olduğunu bildirir. `Main.java` işə salınmadan testlər icra edilə bilər. Hər testdə yeni `TicketService` obyekti yaratmaq testlərin bir-birindən asılılığını azaldır (Service-in xəritəsi hər obyekt üçün ayrıdır).

  

### AAA prinsipi

  

- Arrange — test üçün lazım olan məlumatları və obyektləri hazırlamaq.

- Act — yoxlanılacaq metodu çağırmaq.

- Assert — gözlənilən nəticəni real nəticə ilə yoxlamaq.

  

```java

@Test

void createTicketTest() {

TicketService ticketService = new TicketService(); // Arrange

Ticket ticket = ticketService.createTicket(1001L, "Wi-Fi Problem",

"Router problem", TicketPriority.HIGH); // Act

assertEquals(TicketStatus.OPEN, ticket.getStatus()); // Assert

}

```

  

### Assertion metodları

  

| Metod | İzahı |

|---|---|

| `assertEquals(expected, actual)` | Gözlənilən və faktiki dəyərlərin bərabərliyini yoxlayır. |

| `assertTrue(condition)` | Şərtin true olduğunu yoxlayır; özü boolean qaytarmır. |

| `assertFalse(condition)` | Şərtin false olduğunu yoxlayır. |

| `assertThrows(type, executable)` | Gözlənilən exception tipinin (və ya alt tipinin) atılmasını yoxlayır. |

  

Məsələn, mövcud olmayan ticket üçün:

  

```java

assertThrows(TicketNotFoundException.class,

() -> ticketService.getTicketById(99999L));

```

  

Burada lambda `() -> ...` əməliyyatı JUnit-ə ötürür ki, JUnit onu icra edib exception-u yoxlaya bilsin.

  

### Hazırladığım 8 test

  

| # | Test | Gözlənilən davranış |

|---|---|---|

| 1 | `createTicketTest()` | ID, title, description, priority və ilkin OPEN statusu düzgündür. |

| 2 | `changeTicketStatusTest()` | Status CLOSED olur. |

| 3 | `updateTicketTest()` | Title, description və priority dəyişir; ID və status qorunur. |

| 4 | `deleteTicketTest()` | Ticket silinir. |

| 5 | `TicketNotFoundTest()` | Mövcud olmayan ID üçün exception atılır. |

| 6 | `DuplicateTicketTest()` | Təkrar ID üçün exception atılır. |

| 7 | `getTicketByStatusTest()` | Yalnız OPEN ticket-lər qaytarılır. |

| 8 | Priority filtering testi | Yalnız HIGH priority ticket-lər qaytarılır. |

  

İlk altı testdən dördü uğurlu, ikisi uğursuz biznes ssenarilərini yoxlayır. Uğursuz biznes ssenarisi testin uğursuz olması demək deyil: gözlənilən exception atılırsa, test keçmiş sayılır.

  

7-ci testdə üç ticket yaradıram, 1002-nin statusunu CLOSED edirəm və nəticəni yoxlayıram:

  

```java

List<Ticket> result = ticketService.getTicketByStatus(TicketStatus.OPEN);

assertEquals(2, result.size());

for (Ticket ticket : result) {

assertEquals(TicketStatus.OPEN, ticket.getStatus());

}

```

  

Təkcə sayın düzgün olması kifayət deyil; hər ticket-in statusunu da yoxlamaq lazımdır. 8-ci testdə oxşar məntiqlə priority yoxlanılır. GitHub-dakı real metod adı `getTicketsByPriority` olduğuna görə çağırış belə olmalıdır:

  

```java

List<Ticket> result = ticketService.getTicketsByPriority(TicketPriority.HIGH);

assertEquals(2, result.size());

for (Ticket ticket : result) {

assertEquals(TicketPriority.HIGH, ticket.getPriority());

}

```

  

Hər iki testdə 1001 və 1003 ID-lərinin nəticədə olmasını ayrıca yoxlamaq testi daha güclü edə bilər.

  

```bash

mvn test

```

  

Gözlənilən nəticə: 8 test, 0 failure, 0 error, 0 skipped. Bu, hələ faktiki nəticə deyil. Maven həqiqətən 8 testi tapıb icra etməlidir; yalnız BUILD SUCCESS yazılması kifayət etmir.

  

## 5. Code Review — təkrar baxılacaq anlayışlar

  

Aşağıdakı mövzular müəllimin siyahısında hələ tamamlanmamışdır. Bunları növbəti review üçün qeyd edirəm.

  

### Comparator və sorting algorithm

  

```java

List<Ticket> list = new ArrayList<>(tickets.values());

list.sort(Comparator.comparing(ticket -> ticket.getCreatedAt()));

```

  

Comparator müqayisə qaydasını müəyyən edir: bu nümunədə ticket-lər yaradılma vaxtına görə müqayisə olunur. Sorting algorithm isə müqayisə nəticələrindən istifadə edərək siyahını sıralayır. Comparator özü Bubble Sort deyil. Java-nın standart ArrayList sıralaması stabil, adaptiv müqayisə əsaslı sıralamadan istifadə edir (OpenJDK-da TimSort); `Comparator.comparing` alqoritm seçmir.

  

`Collections.reverse(list)` mövcud siyahının ardıcıllığını tərsinə çevirir. Map-in əlavə olunma sırası dəyişmir, çünki sıraladığımız obyekt ayrıca siyahıdır.

  

### final

  

`private final Map<Long, Ticket> tickets` sahəsində `final` xəritənin başqa xəritə ilə əvəz edilməsinə mane olur; `put`, `remove` və Ticket obyektlərinin dəyişdirilməsinə mane olmur. `private` isə sahəyə digər class-lardan birbaşa girişə məhdudiyyət qoyur.

  

`private final Long id` ticket-in ID sahəsinin yenidən mənimsədilməməsini təmin edir. `private final LocalDateTime createdAt` yaradılma vaxtı sahəsi üçün eyni qaydanı tətbiq edir. `public final class Ticket` isə bu class-dan inheritance etməyə imkan vermir.

  

### void

  

`void` metodun dəyər qaytarmadığını bildirir. Bu metod obyektin vəziyyətini dəyişə, məlumatı silə və ya exception ata bilər.

  

```java

public void changeTicketStatus(Long id, TicketStatus newStatus) {

Ticket ticket = getTicketById(id);

ticket.changeTicketStatus(newStatus);

}

```

  

Burada status dəyişir, amma metod çağıran tərəfə dəyər qaytarmır. `void` məlumatın yaddaşda saxlanılmasına mane olmur.

  

### Object reference

  

```java

Ticket ticket = ticketService.createTicket(1001L, "Wi-Fi Problem",

"Router problem", TicketPriority.HIGH);

ticketService.changeTicketStatus(1001L, TicketStatus.CLOSED);

assertEquals(TicketStatus.CLOSED, ticket.getStatus());

```

  

`ticket` dəyişəni və Map daxilindəki dəyər eyni Ticket obyektinə istinad edir. Buna görə Service həmin obyektin statusunu dəyişdikdə testdəki `ticket` vasitəsilə yeni statusu oxuya bilirəm. Bu, obyektin ikinci nüsxəsinin yaranması demək deyil.

  
.

  


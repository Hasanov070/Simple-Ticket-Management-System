## 1. `Map.values()` nə qaytarır?

Mənim yazdığım ifadə:

```
tickets.values()
```

Bu ifadə `tickets` Map-indəki **bütün value-lara**, yəni `Ticket` obyektlərinə baxmaq üçün `Collection<Ticket>` tipli bir görünüş (**view**) qaytarır.

Məsələn:

```
Collection<Ticket> allValues = tickets.values();
```

Bu zaman:

-   `101`, `102`, `103` kimi **key-lər** ayrıca qaytarılmır.
-   Həmin key-lərlə əlaqəli **Ticket obyektləri** əldə olunur.
-   `values()` **yeni `ArrayList` yaratmır**; mövcud Map ilə əlaqəli görünüş qaytarır.
-   `LinkedHashMap` istifadə etdiyim üçün `values()` üzərində gəzəndə adətən biletləri **əlavə edilmə ardıcıllığında** görürəm.
-   Bu, tarixə görə sorting deyil: əlavə edilmə ardıcıllığı ilə `createdAt` üzrə sıralama eyni anlayış deyil.

**Vacib:** `Collection` üzərində birbaşa `get(0)` kimi indeks əməliyyatı yoxdur. Amma elementləri `for-each` ilə bir-bir yoxlamaq mümkündür.

## 2. Enhanced for-loop nədir və mənim kodumda necə işləyir?

**Enhanced for-loop** (və ya **for-each**) — kolleksiya və array elementlərini **indeks (`i`) istifadə etmədən**, bir-bir götürmək üsuludur.

### Əvvəl yazdığım adi `for`

```
List<Ticket> allTickets = new ArrayList<>(tickets.values());

for (int i = 0; i < allTickets.size(); i++) {
    Ticket ticket = allTickets.get(i);
    System.out.println(ticket);
}
```

Burada:

1.  Bütün biletləri əlavə `ArrayList`-ə qoyuram.
2.  `i = 0` ilə başlayıram.
3.  `allTickets.get(i)` ilə həmin indeksdəki bileti götürürəm.
4.  `i++` ilə növbəti elementə keçirəm.

### İndi öyrəndiyim `for-each`

```
for (Ticket ticket : tickets.values()) {
    System.out.println(ticket);
}
```

Bu sətri belə oxuyuram: **“Map-dəki bütün Ticket obyektlərini bir-bir götür və hər birini çap et.”**

-   `Ticket` — hər elementin **tipidir**.
-   `ticket` — bu dövrdə götürülən obyektə istinad edən **müvəqqəti dəyişən adıdır**.
-   `:` — sadə dillə “bunların içərisindəki hər bir” kimi düşünə bilərəm.
-   `tickets.values()` — gəzmək istədiyim **Ticket obyektləri kolleksiyasıdır**.

Hər iterasiyada `ticket` mövcud Ticket obyektlərindən birinə istinad edir; **yeni Ticket obyekti yaradılmır**. Java növbəti elementə özü keçir. `i++` və `get(i)` yazmağıma ehtiyac qalmır.

### Konkret nümunə

Tutaq ki, `tickets` daxilindəki statuslar belədir:

```
101 -> OPEN
102 -> CLOSED
103 -> OPEN
```

```
for (Ticket ticket : tickets.values()) {
    if (ticket.getStatus() == TicketStatus.OPEN) {
        System.out.println(ticket);
    }
}
```

Dövr əvvəl 101-ə baxır (göstərir), sonra 102-yə baxır (keçir), sonra 103-ə baxır (göstərir). Burada Map-i əvvəlcədən `ArrayList`-ə çevirməyə ehtiyac yoxdur.

## 3.`getTicketByStatus()` — əvvəlki kodumu necə düzəltdim?

Bu, mənim əvvəlki `TicketService.java` kodumda olan yanaşmadır.

### ƏVVƏL — lazımsız `allTickets` yaradırdım

```
public List<Ticket> getTicketByStatus(TicketStatus ticketStatus) {

    if (ticketStatus == null) {
        throw new IllegalArgumentException("Status boş ola bilməz!");
    }

    List<Ticket> allTickets = new ArrayList<>(tickets.values());
    List<Ticket> result = new ArrayList<>();

    for (int i = 0; i < allTickets.size(); i++) {
        Ticket ticket = allTickets.get(i);

        if (ticket.getStatus() == ticketStatus) {
            result.add(ticket);
        }
    }

    return result;
}
```

**Əvvəl nə edirdim?**

-   Map-dəki bütün biletləri `allTickets` adlı əlavə siyahıya köçürürdüm.
-   Sonra `for (int i...)` və `get(i)` ilə yoxlayırdım.
-   Uyğun olanları ikinci siyahı — `result` — içində toplayırdım.
-   Halbuki mənə **biletləri sıralamaq lazım deyildi**, sadəcə statusa görə seçmək lazım idi.

### SONRA — özümün paylaşdığım düzəldilmiş variant

```
public List<Ticket> getTicketByStatus(TicketStatus ticketStatus) {

    // İstifadəçi boş status göndərsə, səhv bildiririk.
    if (ticketStatus == null) {
        throw new IllegalArgumentException("Status boş ola bilməz!");
    }

    // Yalnız uyğun gələn biletləri toplamaq üçün siyahı.
    List<Ticket> result = new ArrayList<>();

    // Map-in value-larını əlavə siyahı yaratmadan bir-bir gəzirik.
    for (Ticket ticket : tickets.values()) {

        // Ticket-in statusu axtarılan statusla eynidirsə, saxlayırıq.
        if (ticket.getStatus() == ticketStatus) {
            result.add(ticket);
        }
    }

    // Yalnız uyğun statuslu Ticket obyektlərini qaytarırıq.
    return result;
}
```

## 4.Temporary `ArrayList` yaratmadan filtering mümkündürmü?

**Bəli, mümkündür.** İki anlayışı fərqləndirdim:

1.  **Lazımsız müvəqqəti siyahı `allTickets`:** Əvvəlcə bütün Ticket-lərin reference-lərini kopyalayırdı. **Bunu silmək olar.**
2.  **Nəticə siyahısı `result`:** Filtrdən keçən Ticket-ləri toplamaq və `List<Ticket>` qaytarmaq üçün lazımdır. **Bizim seçdiyimiz method quruluşunda bunu saxlayırıq.**

```
List<Ticket> result = new ArrayList<>();

for (Ticket ticket : tickets.values()) {
    if (ticket.getStatus() == ticketStatus) {
        result.add(ticket);
    }
}

return result;
```

Beləliklə, filtering üçün **bütün biletlərin ayrı surət-siyahısını yaratmırıq**, amma uyğun biletlərin **nəticə siyahısını** yaradırıq.

`ArrayList`-ə əlavə etdiyim obyektlər **yeni Ticket nüsxələri deyil** — Map-dəki **eyni mövcud Ticket obyektlərinə reference-lərdir**.

## 5. `ArrayList` yaratmağın əlavə memory xərci nədir?

Yaddaşı ofisdəki rəf kimi düşünürəm:

-   `LinkedHashMap` — biletlərin saxlandığı əsas rəfdir.
-   `allTickets` — bütün biletlərin ünvanlarını yazdığım **əlavə siyahıdır**.
-   `result` — yalnız axtardığım biletlərin ünvanlarını yazdığım **nəticə siyahısıdır**.

**Misal:** Map-də 1,000 Ticket var, onların 200-ü OPEN-dır.

Variant

Əlavə saxlanan Ticket reference-ləri (sadələşdirilmiş)

Köhnə kod: `allTickets` + `result`

1,000 + 200 = **1,200**

Yeni kod: yalnız `result`

**200**

Qənaət

**1,000 əlavə reference yeri**

Bu 1,000 yeni Ticket obyektini silmək deyil: **əvvəlki kodda da yeni Ticket obyektləri yaradılmırdı**. Sadəcə `allTickets` üçün əlavə massiv (reference-lər saxlayan yer) ayrılmır.

**Texniki olaraq:**

-   `n` — bütün biletlərin sayı;
-   `k` — filtrdən keçən biletlərin sayı.

Köhnə filter əlavə siyahılar üçün təxminən **O(n + k)** yer istifadə edir. Yeni filter isə nəticə üçün təxminən **O(k)** yer istifadə edir (sabit xərclər nəzərə alınmadan). Hər iki yanaşmada hər bileti yoxladığımız üçün vaxt mürəkkəbliyi **O(n)** qalır.

**Vacib:** 1,200 və 200 rəqəmləri byte deyil, **reference yerlərinin sayını** göstərir. `ArrayList` obyektinin özündə də müəyyən yaddaş xərci və bəzən əlavə tutum olur.

## 6. Niyə sorting-də `ArrayList` yenə qalır?

Mənim `TicketService.java` kodum:

```
public List<Ticket> getTicketsSortedByCreatedAt() {
    List<Ticket> list = new ArrayList<>(tickets.values());
    list.sort(Comparator.comparing(ticket -> ticket.getCreatedAt()));
    return list;
}
```

**Filtering** və **sorting** eyni deyil:

-   **Filtering:** “Bütün biletlərə bax, yalnız `OPEN` olanları seç.” Burada Map-in `values()` hissəsində birbaşa gəzmək kifayətdir.
-   **Sorting:** “Bütün biletləri `createdAt` tarixinə görə köhnədən yeniyə düz.” Burada ardıcıllığı dəyişə biləcəyim ayrıca `List` lazımdır.

`Map.values()` `Collection` qaytarır; mən `sort()`-u işlətmək üçün bunu yeni `ArrayList`-ə köçürürəm. Sorting **orijinal Map-in element sırasını dəyişmir**.

**CHATGPT CHAT FILE**
https://chatgpt.com/share/6b0447ff-0e14-83ed-ae28-af559f131c7drefactor: simplify ticket filtering and reduce unnecessary ArrayList usage

# Semestrálna práca z predmetu _vývoj aplikácií pre mobilné zariadenia_

## Asistent starostlivosti o zviera

**vypracoval:** Andrej Markuš

**študijná skupina:** 5ZYI23

**cvičiaci:** doc. Ing. Patrik Hrkút PhD.

**termín cvičenia:** streda bloky 8-10

## Obsah

[1\. Popis a analýza riešeného problému 3](#_Toc168851385)

[1.1. Špecifikácia zadania 3](#_Toc168851386)

[1.2. Podobné aplikácie 3](#_Toc168851387)

[2\. Návrh riešenia problému 4](#_Toc168851388)

[3\. Popis implementácie 5](#_Toc168851389)

[4\. Zoznam použitých zdrojov 6](#_Toc168851390)

## 1. Popis a analýza riešeného problému
### 1.1. Špecifikácia zadania

Pre moju semestrálnu prácu som sa rozhodol vyvinúť aplikáciu, ktorá bude slúžiť ako praktický pomocník pri starostlivosti o domáce zvieratá. Inšpiráciou pre túto tému mi boli moje vlastné skúsenosti s chovom zvierat, ktoré si vyžadujú pravidelnú starostlivosť a pozornosť. Ako informatik som videl príležitosť využiť svoje znalosti a zručnosti na vytvorenie nástroja, ktorý by túto starostlivosť zjednodušil a zefektívnil.

Koncept aplikácie sa stretol s pozitívnym ohlasom u viacerých ľudí, ktorým som o ňom povedal. Potvrdilo sa mi tak, že ide o tému s relevantným a užitočným riešením pre širokú skupinu ľudí – majiteľov domácich miláčikov.

Aplikácia teda funguje ako jednoduchý systém pre starostlivosť o domáce zviera, ktorý poskytuje nástroje na záznam každodenných aktivít, záznam udalostí, poskytuje prehľad zvieraťa a tipov na starostlivosť.

Moja aplikácia sa líši v tom, že obsahuje jednoduché a intuitívne používateľské rozhranie. Toto používateľské rozhranie poskytuje všetky potrebné funkcie pre informovanie používateľa. Neobsahuje pritom zložitejšie funkcie ako napríklad kalorický príjem, váhu a podobné. Je však oproti vyššie uvedeným aplikácia mierená na viac druhov domácich zvierat a nie len psov a mačky. Z tohto dôvodu je aplikácia oproti ostatným viac osekaná a viac univerzálna.

### 1.2. Podobné aplikácie

Po rozsiahlom prieskume a dôkladnej analýze dostupných aplikácií v službe Google Play som identifikoval nasledovné riešenia, ktoré sa do určitej miery zhodujú s mojou aplikáciou:

- [Clio: Dog Cat Pet Care Tracker](https://play.google.com/store/apps/details?id=com.lazyhippodevelopment.petdiary)
  - Umožňuje sledovať kŕmenie, prechádzky, váhu, lieky a veterinárne návštevy.
  - Má funkciu pre pripomienky a upozornenia.
  - Ponúka kalendár aktivít a grafy pre sledovanie zdravia vášho maznáčika.
  - Dostupná je aj bezplatná verzia s obmedzenými funkciami.
- [Pes & Mačka Aktivita Dog Cat](https://play.google.com/store/apps/details?id=dogcat.app.android)
  - Zaznamenáva aktivity vášho maznáčika, ako sú prechádzky, beh a hry.
  - Sleduje spánok a kalorický príjem.
  - Ponúka hry a výzvy pre motiváciu k aktivite.
  - Aplikácia je bezplatná.
- [Pet Parents: Easy Pet Records](https://play.google.com/store/apps/details?id=com.petparents.pet)
  - Umožňuje uchovávať všetky dôležité informácie o vašom maznáčikovi, ako sú veterinárne záznamy, očkovacie preukazy a poistné informácie.
  - Má funkciu pre zdieľanie informácií s veterinárom alebo opatrovateľom.
  - Ponúka pripomienky na termíny očkovania a odčervenia.
  - Dostupná je aj bezplatná verzia s obmedzenými funkciami.
- [Pet Care Tracker - PetNote](https://play.google.com/store/apps/details?id=com.lancerdog.petnote_plus)
  - Umožňuje sledovať kŕmenie, prechádzky, váhu, lieky a veterinárne návštevy.
  - Má funkciu pre pripomienky a upozornenia.
  - Ponúka kalendár aktivít a grafy pre sledovanie zdravia vášho maznáčika.
  - Aplikácia je bezplatná.

Podobných aplikácií sa v službe Google Play nachádza väčšie množstvo, no vybral som práve tieto z dôvodu ich popularity a kvality spracovania.

## 2. Návrh riešenia problému

Aplikácia je vyvinutá pre platformu Android v programovacom jazyku Kotlin, čím sa dosahuje vysoká stabilita a výkon. Pre tvorbu intuitívneho a responzívneho užívateľského rozhrania je využitá moderná knižnica Jetpack Compose, ktorá umožňuje rýchly a efektívny vývoj UI.

Dáta aplikácie sú uložené v lokálnej databáze SQLite, ktorá sa vyznačuje kompaktnou veľkosťou a rýchlym prístupom k dátam. Pre prácu s databázou je použitá knižnica Room, ktorá zjednodušuje a zefektívňuje prácu s dátami.

Architektúra aplikácie vychádza z osvedčeného vzoru MVVM, ktorý umožňuje čisté oddelenie funkčnej a vizuálnej časti aplikácie. Vďaka tomuto prístupu bude kód aplikácie prehľadný, ľahko testovateľný a pripravený na budúce rozšírenia.

Funkcie, ktoré sú dôležité pre túto aplikáciu:

- Vytvorenie profilu svojho zvieraťa
- Prehľad svojho zvieraťa, ktorý obsahuje fotku a základné informácie
- Prehľad denných aktivít, ktoré sú denné pripomínané pomocou upozornení
- Pridávanie a odoberanie denných aktivít
- Kalendár dôležitých udalostí, ktoré sú pripomenuté pomocou upozornenia
- Pridávanie a odoberanie udalostí do kalendára
- Zobrazovanie tipov pre chovateľa podľa druhu ich zvieraťa

**UML sa z dôvodu veľkosti nachádza ako príloha v adresári „dokumentacia“.**

## 3. Popis implementácie

Aplikácia je implementovaná s použitím **päť rôznych obrazoviek**, kde každá obrazovka zastáva inú funkciu. Úlohy týchto obrazoviek sú:

1. Pridaj zviera – táto obrazovka slúži na pridanie zvieraťa pri prvom spustení aplikácie
2. Prehľad – táto obrazovka slúži ako prehľad zvieraťa spolu so základnými informáciami
3. Aktivity – táto obrazovka slúži na pridávanie každodenných aktivít, na ktoré je používateľ v daný čas upozornený
4. Udalosti – táto obrazovka slúži na pridávanie udalosti, na ktoré je používateľ upozornený v čase konania
5. Tipy – táto obrazovka slúži na zobrazovanie tipov pre používateľa podľa zvoleného druhu zvieraťa

Pre korektné správanie a funkciu týchto obrazoviek používame komponent **Navigation**, ktorý nám umožňuje jednoduchý presun medzi obrazovkami. Na týchto obrazovkách zároveň zobrazujeme dáta, ktoré ukladáme do lokálnej databázy pomocou knižnice **Room**. Táto knižnica nám umožňuje jednoduchý prístup a modifikáciu tejto databázy. Ďalej sa v aplikácií používa **AlarmManager** pre plánovanie notifikácií a **ViewModel** pre správnu implementáciu jednotlivých obrazoviek.

Notifikácie fungujú na princípe vyššie spomínaného AlarmManager komponentu, ktorý naplánuje danú notifikáciu na určitý čas. Zobrazenie notifikácie sa potom spracuje pomocou implementácie komponentu BroadcastReceiver, ktorý odošle notifikáciu v čase na kedy je notifikácia naplánovaná. Na tieto notifikácie sú vytvorené dva kanály, kde jeden kanál odosiela notifikácie aktivít a druhý odosiela notifikácie udalostí. Aplikácia teda obsahuje dva typy notifikácií.

Načítavanie obrázkov v aplikácií je spracované pomocou **externej knižnice Coil**, ktorá nám umožňuje ich asynchrónne načítavanie. Obrázok zvieraťa, ktorý chceme načítavať sa nachádza v dátovom úložisku, ku ktorému pristupujeme pomocou knižnice **DataStore**. Tu prichádza na pomoc knižnica Coil, ktorá nám tento obrázok z dátového úložiska načíta.

Pre jednoduchý prehľad o zvierati bez otvárania aplikácie je k dispozícií **jednoduchý widget**. Tento widget obsahuje nadpis, informácie o zvierati a tlačidlo, ktoré po stlačení otvorí aplikáciu. Na jeho tvorbu bola využitá knižnica **Glance**, ktorá sa používa na jednoduchú tvorbu widgetov.

Aplikácia bola počas tvorby priebežne ukladaná na **Githube** za použitia technológie **Git**. Táto technika poskytla efektívne sledovanie zmien v kóde, spoľahlivosť systému a uloženie kódu na viacerých miestach.

Samotná architektúra aplikácia sa drží čo najviac štandardov tvorby Android aplikácia. Je rozdelená do priehľadných balíčkov, kde každý reprezentuje určitú časť aplikácie, napríklad UI, data, notification, atď. V týchto hlavných balíčkoch sa nachádzajú aj ďalšie balíčky, ktoré oddeľujú určité časti od väčšieho celku. Napríklad v UI sú takto oddelené jednotlivé obrazovky.

## 4. Zoznam použitých zdrojov
- <https://medium.com/@cherfaoui_dev/easy-image-picking-no-permissions-required-using-jetpack-compose-733c17163369>
- <https://www.youtube.com/watch?v=mWb_hEBLIqA>
- <https://www.youtube.com/watch?v=bHlLYhSrXvc&t=2s>
- <https://medium.com/@satyajeetmohalkar/pre-populating-your-app-database-using-room-using-json-file-abbc95140cc3>

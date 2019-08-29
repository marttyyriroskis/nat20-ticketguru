# Testisuunnitelma: *\<testauksen kohde\>*

## Testauksen tavoite

*\<Testattavat käyttäjätarinat tai muu tavoite kuten suorituskyky, tietoturva
tms.\>*

## Testit

 **Testattava asia**                | **Testin suoritus**
 ---------------------------------- | -------------------
*\<mikä on testattava\>* | *\<ohjeet, miten testaus suoritetaan: mitä tehdään, miten tarkistetaan testin tulos jne. \>*
*\<Esimerkki: kirjautuminen\>* | *\<Kirjaudutaan oikeilla tunnuksilla: ohjataan profiilisivulle. Kirjaudutaan väärillä tunnuksilla, toinen tai molemmat tyhjänä: paluu kirjautumissivulle ja virheilmoitus\>*
*\<Esimerkki: rekisteröityminen\>* | *\<Onnistunut rekisteröinti katsotaan tietokannasta. Syötteiden tarkistukset: kokeillaan virheellisiä ja puuttuvia syötteitä: paluu ja virheilmoitus, lomake esitäytetty. Samalla sähköpostilla/käyttäjätuunnuksella vain yksi rekisteröityminen\>* 

Testiloki *\<päiväys\>*
=======================
_Testejä voidaan tehdä useaan eri otteeseen tai eri ympäristöissä. Silloin näitä lokeja voi tulla useita._

**Testaaja:**

**Ympäristö:** *\<Testauksen ympäristö, esim. selain, verkkoympäristö\>* |

Löydökset:
----------

*\<Tämä osa dokumenttia liittyy testauksen suoritukseen. Listaa tähän löytämäsi
ongelmat ja muut kommenttisi\>*

_Löydetyistä vioista kirjaa selkeästi,_
1. _mikä vika on_
2. _miten sen saa aikaan (toistettua)_
3. _lyhyt kuvaus, miten ohjelmisto toimi väärin: mitä tapahtui, mitä olisi pitänyt tapahtua_

Jos projektissa on käytössä jokin vikojenhallintajärjestelmä, kuten suositeltaa on, kirjaukseksi riittää linkki vikaraporttiin.
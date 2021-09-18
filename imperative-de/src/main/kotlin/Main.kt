import de.github.dudrie.hamster.imperative.de.*

internal fun main() {
    starteSpiel()

    sage("Hallo ich bin ein Hamster :)")
    laufe()
    laufe()
    sammleKornAuf()

    val anzahlSchritte = holeAnzahlSchritte()
    val gelaufeneMeter = anzahlSchritte * holeFeldZuMeterSkalierung()
    sage("Ich bin ${gelaufeneMeter}m gelaufen.")

    stoppeSpiel()
}

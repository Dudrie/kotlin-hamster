package de.github.dudrie.hamster.functional

fun laufe() {
    SingletonSpiel.paule.laufe()
}

fun dreheNachLinks() {
    SingletonSpiel.paule.dreheNachLinks()
}

fun sammleKornAuf() {
    SingletonSpiel.paule.sammleKornAuf()
}

fun legeKornAb() {
    SingletonSpiel.paule.legeKornAb()
}

fun istVorDirFrei(): Boolean = SingletonSpiel.paule.istVorDirFrei()

fun liegtEinKornAufDeinemFeld(): Boolean = SingletonSpiel.paule.liegtEinKornAufDeinemFeld()

fun istDeinMundLeer(): Boolean = SingletonSpiel.paule.istDeinMundLeer()

fun sage(nachricht: String) {
    SingletonSpiel.paule.sage(nachricht)
}

# Hamster Simulator with Kotlin

This is an implementation of the ["Java Hamster Model"](https://www.java-hamster-modell.de/simulator.html) written
in [Kotlin](https://kotlinlang.org/). The simulator is a mini world which can be used to teach programming to students
or pupils.

# Modules

This repository contains several modules which all together represent the implementation of the Java Hamster Model.

## core

This module contains all code which other modules can use. It contains the basic types, interfaces, ... needed to create
a working hamster game. It also contains the game engine which controls the game state through commands that get
executed.

## oop

Implements an external object-oriented API which can be used by students in solving their exercises. It contains several
wrapper classes which provide a simpler API to change the game state instead of manually executing the game commands.

## functional

Implements an external imperative API which can be used by students in solving their exercises. This module wraps all
the API code from the `:oop` in functions therefore creating an API which does not need any object-oriented concepts.

## ui

Implements a basic UI using [Compose for Desktop][compose-for-desktop] representing the current game state. It also
contains the needed assets and resources for a hamster game.

## editor

An editor UI to create / edit the territories used by the game. It allows loading and exporting of territory files which
can then be used for the exercises. This UI also uses [Compose for Desktop][compose-for-desktop]

[compose-for-desktop]: https://www.jetbrains.com/de-de/lp/compose/

# Attributions

- Icon used for grains mady by [Freepik](https://www.freepik.com) from [www.flaticon.com](https://www.flaticon.com/).

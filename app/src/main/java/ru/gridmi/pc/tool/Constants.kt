package ru.gridmi.pc.tool

object Constants {

    const val URL: String = "http://file.gridmi.ru/data"//"https://edo.ilexx.ru"

    fun String.endpoint(): String = URL + this

}
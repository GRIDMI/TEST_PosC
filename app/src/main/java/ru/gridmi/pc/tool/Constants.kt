package ru.gridmi.pc.tool

object Constants {

    private const val URL: String = "https://edo.ilexx.ru"

    fun String.endpoint(): String = URL + this

}
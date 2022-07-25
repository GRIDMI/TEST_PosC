package ru.gridmi.pc.tool

object TinyHelper {

    inline fun <reified T> Result<T>.resultToAny(): Any? {
        return getOrNull() ?: exceptionOrNull()
    }

}
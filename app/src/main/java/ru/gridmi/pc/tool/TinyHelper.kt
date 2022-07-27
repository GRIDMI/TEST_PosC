package ru.gridmi.pc.tool

import java.util.*

object TinyHelper {

    inline fun <reified T> Result<T>.resultToAny(): Any? {
        return getOrNull() ?: exceptionOrNull()
    }

    inline fun <reified SET:Set<T>, T> SET.likeSync(): SET {
        return Collections.synchronizedSet(this) as SET
    }

}
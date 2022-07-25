package ru.gridmi.pc.tool

import android.util.Log
import com.fasterxml.jackson.dataformat.xml.XmlMapper

object XmlTool {

    val MAPPER: XmlMapper = XmlMapper()

    inline fun <reified T> to(v: String) = runCatching {
        MAPPER.readValue(v, T::class.java)
    }

}
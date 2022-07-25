package ru.gridmi.pc.model.dto

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText
import java.io.Serializable

data class Product(
    var code: Int,
    var ean13: String,
    var name: String,
    var tfc: String, // Text for check
    var price: Float,
    var remainder: Float
): Serializable {

    val attr: MutableList<Attribute> = mutableListOf()

    fun findAttr(a: AttrEnum) = attr.firstOrNull {
        a.code == it.attr && this.code == it.id
    }

    enum class AttrEnum(val code: Int) {
        TYPE_OF_ALCOHOLIC_PRODUCT(22),
        ALCOHOL_CONTENTING(27)
    }

    class Attribute: Serializable {

        @JacksonXmlProperty(isAttribute = true, localName = "id")
        var id: Int = 0
        @JacksonXmlProperty(isAttribute = true, localName = "attr_id")
        var attr: Int = 0
        @JacksonXmlText
        var value: String? = null

        override fun toString(): String {
            return "Attribute(id=$id, attrId=$attr, value=$value)"
        }

    }

}

//1	Число 13	Код
//2	Строка 13	Штрихкод
//3	Строка 100	Наименование
//4	Строка 100	Текст для чека
//5	Число 15.2	Цена
//6	Число 17.3	Остаток товара

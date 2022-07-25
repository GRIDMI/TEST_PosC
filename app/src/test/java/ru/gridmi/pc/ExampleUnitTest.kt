package ru.gridmi.pc

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.junit.Assert.*
import org.junit.Test
import ru.gridmi.pc.model.dto.Product
import java.util.regex.Pattern


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test() {

        val pattern = Pattern.compile("^(\\d+);(\\d{13});([^;]{1,100});([^;]{1,100});([+-]?\\d+(\\.\\d+)?);([+-]?\\d+(\\.\\d+)?)")
        val m = pattern.matcher("22;4602451013415;Водка \"Золото Башкирии Альфа\" 0,7л;Водка \"Золото Башкирии Альфа\" 0,7л;370.00;2.000;0;0;1;0;0;;0;1;0;13;1;;;;;;0;0;0;2")

        val r = m.find()

        System.out.println("---------")
        for (i in 0..m.groupCount()) {
            System.out.println(m.group(i))
        }
        System.out.println("---------")

        assertTrue(r)

        //val v = "20;4603928005223;Водка особая \"Государев заказ на черной смородине\"0,5л;Водка особая \"Государев заказ на черной смородине\"0,5л;327.00;3.000;0;0;1;0;50;;0;1;0;13;1;;;;;;0;0;0;2"


    }

    @Test
    fun fromXML() {

        val xmlMapper = XmlMapper()
        val value: Product.Attribute = xmlMapper.readValue(
            "<goods_attr id=\"128\" attr_id=\"23\">0.700</goods_attr>",
            Product.Attribute::class.java
        )

        assertTrue(value.id == 128 && value.attr == 23 && value.value == "0.700")

    }

}
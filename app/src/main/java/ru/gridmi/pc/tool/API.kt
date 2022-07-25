package ru.gridmi.pc.tool

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import ru.gridmi.pc.model.dto.Product
import ru.gridmi.pc.tool.Constants.endpoint
import ru.gridmi.pc.tool.TinyHelper.resultToAny
import java.io.IOException
import java.util.regex.Pattern
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object API {

    private val client: OkHttpClient = OkHttpClient.Builder().build()

    private val PROD_DIR_EXTRACTOR: ((String) -> List<Product>) = {

        val list: MutableList<Product> = mutableListOf()

        var temp: Product? = null

        val regExpItem: Pattern = Pattern.compile("^(\\d+);(\\d{13});([^;]{1,100});([^;]{1,100});([+-]?\\d+(\\.\\d+)?);([+-]?\\d+(\\.\\d+)?)")

        it.lines().forEach { line ->

            val matcher = regExpItem.matcher(line)
            if (matcher.find()) {
                runCatching {
                    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                    (Product(
        code = matcher.group(1).toInt(),
        ean13 = matcher.group(2) as String,
        name = matcher.group(3) as String,
        tfc = matcher.group(4) as String,
        price = matcher.group(5).toFloat(),
        remainder = matcher.group(7).toFloat()
    ))
                }.onSuccess { p ->
                    list.add(p)
                    temp = p
                }.onFailure {
                    temp = null
                }
                return@forEach
            }

            XmlTool.to<Product.Attribute>(line).onSuccess { attr ->
                temp?.attr?.add(attr)
            }

        }

        list

    }

    /**
     * Список продуктов
     * */
    fun listOfProducts(/*могли быть параметры*/) = runBlocking {
        val r = Request.Builder().get().url("/test/catalog.spr".endpoint())
        client.await(request = r.build(), extractor = PROD_DIR_EXTRACTOR)
    }

    private suspend inline fun <reified T> OkHttpClient.await(
        request: Request,
        noinline extractor: ((String) -> T)
    ) = suspendCancellableCoroutine<T> {

        val call = newCall(request)

        it.invokeOnCancellation {
            if (!call.isCanceled()) {
                call.cancel()
            }
        }

        call.enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                onFinal(data = null, error = e)
            }

            override fun onResponse(call: Call, response: Response) {
                onFinal(data = response.body?.string(), error = null)
            }

            private fun onFinal(data: String?, error: Throwable?) {

                val final: Any? = runCatching {

                    if (error != null) throw error

                    synchronized(extractor) {
                        extractor(data as String)
                    }

                }.resultToAny()

                if (final !is T || final is Throwable) {
                    val e = final as? Throwable ?: Throwable("Unknown error")
                    it.resumeWithException(e)
                    return
                }

                it.resume(final)

            }

        })

    }

}
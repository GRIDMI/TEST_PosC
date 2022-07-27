package ru.gridmi.pc.tool

import ru.gridmi.pc.tool.TinyHelper.likeSync
import java.util.concurrent.atomic.AtomicInteger

class LoadState(val state: State, val data: Any?): MLD.OnDealloc {

    private val id = sequence.getAndIncrement()

    fun isHandled() = !handled.add(id)

    override fun onDealloc() {
        handled.remove(id)
    }

    enum class State {
        INIT,
        LOAD,
        DONE
    }

    companion object {

        private val sequence = AtomicInteger(0)
        private val handled = mutableSetOf<Int>().likeSync()

        fun init() = LoadState(state = State.INIT, data = null)

    }

}
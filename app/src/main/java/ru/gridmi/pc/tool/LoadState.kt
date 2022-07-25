package ru.gridmi.pc.tool

import java.util.*

class LoadState(val state: State, val data: Any?) {

    private val id = UUID.randomUUID().toString()

    fun isHandled() = !isOnceHandledWithBlock(id)

    enum class State {
        INIT,
        LOAD,
        DONE
    }

    companion object {

        private val handled: MutableList<String> = mutableListOf()

        fun isOnceHandledWithBlock(id: String): Boolean {
            val once: Boolean = !handled.contains(id)
            if (once) handled.add(id)
            return once
        }

        fun init() = LoadState(state = State.INIT, data = null)

    }

}
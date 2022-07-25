package ru.gridmi.pc

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MainApp: Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        fun Int.toStringFromRes(): String {
            return context.resources.getString(this)
        }

    }

}
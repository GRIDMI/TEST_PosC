package ru.gridmi.pc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class MainFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(getLayout(), container, false)
    }

    fun singleActivity(): MainActivity = requireActivity() as MainActivity

    open fun getLayout(): Int = 0

}
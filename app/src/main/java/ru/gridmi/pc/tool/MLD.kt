package ru.gridmi.pc.tool

import androidx.lifecycle.MutableLiveData

class MLD<T>(v: T? = null): MutableLiveData<T>(v) {

    override fun setValue(value: T) {
        (this.value as? OnDealloc)?.onDealloc()
        super.setValue(value)
    }

    interface OnDealloc {
        fun onDealloc()
    }

}
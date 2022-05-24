package com.raywenderlich.weather.test

class Pizda(private val onOrgasm: () -> Unit) {
//class Pizda(private val callback: HuiCallback) {

    fun move() {
//        callback.orgasm()
        onOrgasm.invoke()
    }

    fun trah() {

    }
}
package com.main.app;

class KotBuilder {
    var age: Int = 0
    var name: String = ""

    fun build(): Person {
        return Person(age, name)
    }
}
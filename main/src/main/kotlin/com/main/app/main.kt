package com.main.app

fun main(args: Array<String>) {
    println("Hello World!")
    val personBuilder = PersonBuilder()
    personBuilder.age = 10
    personBuilder.name = "tEst"
    val test = personBuilder::class
    println(personBuilder.build().name)
}
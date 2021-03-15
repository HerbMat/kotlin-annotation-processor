package com.main.app

import com.main.app.annotation.processor.Builder
import com.main.app.annotation.processor.MapTo

@Builder
@MapTo(value = PersonDto::class)
data class Person(
    val age: Int,
    val name: String
)

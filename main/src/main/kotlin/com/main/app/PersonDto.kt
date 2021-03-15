package com.main.app

import com.main.app.annotation.processor.Builder
import com.main.app.annotation.processor.MapTo

@Builder
data class PersonDto(
    val age: Int,
    val name: String
)

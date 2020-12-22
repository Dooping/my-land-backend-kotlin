package com.gago.david.myland.controller

import com.gago.david.myland.entities.HelloDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("hello")
class HelloResource {

    @GetMapping("/hello")
    fun hello(): HelloDto {
        return HelloDto("Hello world!")
    }

}
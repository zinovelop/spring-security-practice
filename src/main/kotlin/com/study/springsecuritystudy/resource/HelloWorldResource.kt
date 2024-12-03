package com.study.springsecuritystudy.resource

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldResource {

    @GetMapping("/hello-world")
    fun helloWorld(): String = "Hello World"
}
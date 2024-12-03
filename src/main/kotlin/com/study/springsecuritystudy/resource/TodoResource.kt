package com.study.springsecuritystudy.resource

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class TodoResource {
    val TODO_LIST:MutableList<Todo> = mutableListOf(
        Todo(1,"jinho","스프링 시큐리티 배우기"),
        Todo(2,"jinho","Redis 배우기"),
        Todo(3,"jinho","스프링 시큐리티 배우기"),
        Todo(4,"jinho","스프링 시큐리티 배우기"),
        Todo(5,"jinho","스프링 시큐리티 배우기"),
    )

    @GetMapping("/todos")
    fun retrieveAllTodos():List<Todo> = TODO_LIST

    @GetMapping("/users/{username}/todos")
    fun retrieveTodosByUsername(@PathVariable username: String):List<Todo> = TODO_LIST.filter{ todo -> todo.username == username }

    @PostMapping("/users/{username}/todos")
    fun postTodo(@PathVariable username: String, @RequestBody todo:Todo) {
        TODO_LIST.add(todo)
    }
}

data class Todo (
        val id:Int,
        var username: String,
        var description: String,
    )
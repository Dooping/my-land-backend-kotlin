package com.gago.david.myland.controller

import com.gago.david.myland.entities.User
import com.gago.david.myland.entities.UserDto
import com.gago.david.myland.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController("user")
class UserController {

    @Autowired
    private val userService: UserService? = null

    @PostMapping("/register")
    fun register(@RequestBody user: UserDto) {
        userService?.registerNewUserAccount(user)
    }

    @GetMapping("users")
    fun users(): MutableIterable<User>? {
        return userService?.getAllUsers()
    }

}
package com.gago.david.myland.service

import com.gago.david.myland.entities.User
import com.gago.david.myland.entities.UserDto
import com.gago.david.myland.exceptions.EmailExistsException
import com.gago.david.myland.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsPasswordService
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.*


@Service
class UserService : UserDetailsService, UserDetailsPasswordService {
    @Autowired
    private val passwordEncoder: PasswordEncoder? = null

    @Autowired
    private val userRepository: UserRepository? = null

    @Throws(EmailExistsException::class)
    fun registerNewUserAccount(accountDto: UserDto): User {
        if (emailExist(accountDto.email)) {
            throw EmailExistsException(
                "There is an account with that email adress:" + accountDto.email
            )
        }
        val user = User(
            null,
            accountDto.name,
            accountDto.email,
            passwordEncoder!!.encode(accountDto.password),
            "USER"

        )
        return userRepository?.save(user)!!
    }

    private fun emailExist(email: String): Boolean {
        return userRepository?.findByEmail(email) != null
    }

    override fun loadUserByUsername(email: String?): UserDetails {
        val user = userRepository!!.findByEmail(email)
            ?: throw UsernameNotFoundException(
                "No user found with username: $email"
            )
        val enabled = true
        val accountNonExpired = true
        val credentialsNonExpired = true
        val accountNonLocked = true
        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password, enabled, accountNonExpired,
            credentialsNonExpired, accountNonLocked,
            Collections.singletonList(SimpleGrantedAuthority(user.role))
        )
    }

    fun getAllUsers(): MutableIterable<User>? {
        return userRepository?.findAll()
    }

    override fun updatePassword(userDetails: UserDetails?, newPassword: String?): UserDetails {
        val user: User? = userRepository?.findByEmail(userDetails?.username)
        if (newPassword != null && user != null) {
            user.password = newPassword
            userRepository?.save(user)
        }
        return org.springframework.security.core.userdetails.User(
            user?.email,
            user?.password, true, true,
            true, true,
            Collections.singletonList(SimpleGrantedAuthority(user?.role)))
    }
}
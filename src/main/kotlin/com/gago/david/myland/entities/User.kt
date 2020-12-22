package com.gago.david.myland.entities

import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "user", schema = "public")
data class User (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,

        @Column(nullable = false)
        var name: String,

        @Column(nullable = false, unique = true)
        var email: String,

        @Column(nullable = false)
        var password: String,

        @Column(nullable = false)
        var role: String

)
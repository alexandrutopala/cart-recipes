package com.cartrecipes

import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class CartRecipesApplication {
    fun main(args: Array<String>) {
        org.springframework.boot.runApplication<CartRecipesApplication>(*args)
    }
}
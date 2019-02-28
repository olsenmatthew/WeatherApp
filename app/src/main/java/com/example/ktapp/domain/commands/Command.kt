package com.example.ktapp.domain.commands

interface Command <out T> {
    suspend fun execute(): T
}
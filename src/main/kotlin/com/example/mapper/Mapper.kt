package com.example.mapper

interface Mapper<in Input, out Output> {
    operator fun invoke(input: Input): Output
}
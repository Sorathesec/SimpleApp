package com.netaphous.testapplication.network

object ColormindDto {
    data class Request(val model: String = "default")
    data class Response(val result: List<List<Int>>)
}
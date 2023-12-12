package com.netaphous.testapplication.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ColormindApi {
    @POST("api/")
    suspend fun getColorPalette(
        @Body dto: ColormindDto.Request = ColormindDto.Request()
    ): Response<ColormindDto.Response>
}
package com.example.randomdog.api

import com.example.randomdog.models.Dog
import retrofit2.http.GET

const val BASE_URL = "https://random.dog/"

interface ApiRequest {
    @GET("woof.json")
    suspend fun getRandomDog():Dog

}
package com.example.remote.api.Remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteApp {

    private var BASE_URL = "https://platform.antares.id:8443/~/antares-cse/"

    fun CreateInstance(): RemoteService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(RemoteService::class.java)
    }
}
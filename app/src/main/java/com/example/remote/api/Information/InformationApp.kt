package com.example.remote.api.Information

import com.example.remote.api.Remote.RemoteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InformationApp {
    private var BASE_URL = "https://platform.antares.id:8443/~/antares-cse/antares-id/TvRemote/MonitorData/"

    fun CreateInstance(): InformationService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(InformationService::class.java)
    }
}
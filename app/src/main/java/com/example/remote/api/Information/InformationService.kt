package com.example.remote.api.Information

import com.example.remote.api.Remote.RemoteModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers

interface InformationService {

    @Headers(
        "X-M2M-Origin: e09945e5329c4890:641f9aa460637c0f",
        "Content-Type: application/json;ty=4",
        "Accept: application/json"
    )
    @GET("la")
    fun getData() : Call<InformationModel>
}
package com.example.remote.api.Remote

import retrofit2.Call
import retrofit2.http.*

interface RemoteService {

    @Headers(
        "X-M2M-Origin: e09945e5329c4890:641f9aa460637c0f",
        "Content-Type: application/json;ty=4",
        "Accept: application/json"
    )
    @POST("cnt-N-Kf1-qcTKC4MFIm")
    fun insertCommand(@Body statusData: RemoteModel) : Call<RemoteModel>

}
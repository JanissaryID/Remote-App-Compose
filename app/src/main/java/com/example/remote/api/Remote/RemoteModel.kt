package com.example.remote.api.Remote

import com.google.gson.annotations.SerializedName

data class RemoteModel(

	@field:SerializedName("m2m:cin")
	val m2mCin: M2mCin? = null
)

data class M2mCin(

	@field:SerializedName("con")
	val con: String? = null
)

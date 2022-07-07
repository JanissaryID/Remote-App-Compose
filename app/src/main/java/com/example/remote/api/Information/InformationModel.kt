package com.example.remote.api.Information

import com.google.gson.annotations.SerializedName

data class InformationModel(

	@field:SerializedName("m2m:cin")
	val m2mCin: M2mCin? = null
)

data class M2mCin(

	@field:SerializedName("cs")
	val cs: Int? = null,

	@field:SerializedName("ct")
	val ct: String? = null,

	@field:SerializedName("st")
	val st: Int? = null,

	@field:SerializedName("con")
	val con: String? = null,

	@field:SerializedName("ty")
	val ty: Int? = null,

	@field:SerializedName("ri")
	val ri: String? = null,

	@field:SerializedName("lt")
	val lt: String? = null,

	@field:SerializedName("pi")
	val pi: String? = null,

	@field:SerializedName("cnf")
	val cnf: String? = null,

	@field:SerializedName("rn")
	val rn: String? = null
)

//data class Con(
//
//	@field:SerializedName("Freq")
//	val freq: String? = null,
//
//	@field:SerializedName("Post")
//	val freq: String? = null,
//)

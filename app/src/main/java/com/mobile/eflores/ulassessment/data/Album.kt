package com.mobile.eflores.ulassessment.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Album (

	@SerializedName("userId") val userId : Int,
	@SerializedName("id") val id : Int,
	@SerializedName("title") val title : String

) : Serializable
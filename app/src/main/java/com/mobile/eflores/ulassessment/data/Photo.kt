package com.mobile.eflores.ulassessment.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Photo (

	@SerializedName("albumId") val albumId : Int,
	@SerializedName("id") val id : Int,
	@SerializedName("title") val title : String,
	@SerializedName("url") val url : String,
	@SerializedName("thumbnailUrl") val thumbnailUrl : String
) : Serializable
package com.example.info.model

import com.google.gson.annotations.SerializedName

data class CanadaInfo(
    var title: String?,
    @SerializedName("rows")
    val details: List<Details>?
)
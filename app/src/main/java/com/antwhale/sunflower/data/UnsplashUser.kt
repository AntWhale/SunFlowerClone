package com.antwhale.sunflower.data

import com.google.gson.annotations.SerializedName

data class UnsplashUser(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("username") val username: String
) {
    val attributionUrl: String
        get() = "https://unsplash.com/$username?utm_source=sunflower&utm_medium=referral"
}

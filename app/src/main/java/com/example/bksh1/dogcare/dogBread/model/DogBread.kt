package com.example.bksh1.dogcare.dogBread.model


import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class DogBread {

    @SerializedName("message")
    @Expose
    var message:  Map<String, List<String>>? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

}
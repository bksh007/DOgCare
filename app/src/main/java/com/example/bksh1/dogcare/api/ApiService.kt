package com.example.bksh1.dogcare.api

import com.example.bksh1.dogcare.dogBread.model.DogBread
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("breeds/list/all/")
    fun getDogBread(): Observable<DogBread>
}
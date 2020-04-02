package com.example.bksh1.dogcare.dogBread

import com.example.bksh1.dogcare.dogBread.model.DogBread
import com.example.bksh1.dogcare.api.ApiService
import com.example.bksh1.dogcare.api.RetrofitHelper
import io.reactivex.Observable

class DogBreadListModel : DogBreadListContact.Model {
    private val service: ApiService = RetrofitHelper.getRetrofitInstance().create(ApiService::class.java)

    override fun getBreadList(): Observable<DogBread> {
        return service.getDogBread()
    }

}
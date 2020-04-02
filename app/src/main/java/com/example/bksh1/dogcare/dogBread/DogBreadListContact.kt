package com.example.bksh1.dogcare.dogBread

import com.example.bksh1.dogcare.dogBread.model.DogBread
import com.example.bksh1.dogcare.utils.LifeCycle
import io.reactivex.Observable

class DogBreadListContact {

    interface View {
        fun setUpDogBreadList(dogBread: ArrayList<String>): Observable<String>
        fun showToast(text: String)
    }

    interface Presenter : LifeCycle {
        fun callDogBread()

    }
    interface Model {
        fun getBreadList(): Observable<DogBread>

    }
}
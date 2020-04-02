package com.example.bksh1.dogcare.dogBread

import android.util.Log
import com.example.bksh1.dogcare.utils.RxUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class DogBreadListPresenter(var view: DogBreadListContact.View) : DogBreadListContact.Presenter {
    private val model: DogBreadListModel = DogBreadListModel()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onCreate() {
        callDogBread()
    }

    override fun callDogBread() {
        compositeDisposable.add(
                model.getBreadList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                                onNext = { dogBread ->
                                    val list: ArrayList<String> = ArrayList()
                                    for (i in dogBread.message?.entries!!) {
                                        list.add(i.key)
                                    }
                                    view.setUpDogBreadList(list).subscribeBy(
                                            onNext = { name ->
                                                view.showToast(name)
                                            },
                                            onError = {
                                                println(it.message)
                                            }
                                    )
                                    Log.d("Rkp", "breadList is $dogBread")

                                },
                                onError = {
                                    println(it.message)
                                    Log.d("Rkp", "On Error  is $it")
                                }
                        )
        )
    }

    override fun onDestroy() {
        RxUtil.clearCompositeDisposable(compositeDisposable)
    }

}
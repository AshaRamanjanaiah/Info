package com.project.info.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.info.di.ApiModule
import com.project.info.di.DaggerViewModelComponent
import com.project.info.model.CanadaInfo
import com.project.info.model.Details
import com.project.info.network.InfoApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class InfoViewModel(): ViewModel() {

    constructor(test: Boolean): this() {
        injected = true
    }

    val countryDetails by lazy { MutableLiveData<List<Details>>() }
    val title by lazy { MutableLiveData<String>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    @Inject
    lateinit var infoApiService: InfoApiService

    private val disposable = CompositeDisposable()

    private var injected = false

    init {
        refresh()
        loading.value = true
    }

    fun inject() {
        if(!injected) {
            DaggerViewModelComponent.builder()
                .apiModule(ApiModule())
                .build()
                .inject(this)
        }
    }

    fun refresh() {
        inject()
        loadError.value =false
        getCountryDetails()
    }

    fun getDetails(): MutableLiveData<List<Details>>{
        return countryDetails
    }

    fun getAppTitle(): MutableLiveData<String> {
        return title
    }

    private fun getCountryDetails() {
        disposable.add(
            infoApiService.getCanadaInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<CanadaInfo>(){
                    override fun onSuccess(canadaInfo: CanadaInfo) {
                        title.value = canadaInfo.title
                        countryDetails.value = canadaInfo.details
                        loadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        if(!countryDetails.value.isNullOrEmpty()) {
                            loadError.value = false
                        } else {
                            countryDetails.value = null
                            loadError.value = true
                        }
                        loading.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
package com.example.info.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.info.model.CanadaInfo
import com.example.info.model.Details
import com.example.info.network.InfoApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class InfoViewModel: ViewModel() {

    val countryDetails by lazy { MutableLiveData<List<Details>>() }
    val title by lazy { MutableLiveData<String>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private var infoApiService = InfoApiService()

    private val disposable = CompositeDisposable()

    fun refresh() {
        loading.value = true
        loadError.value =false
        getCountryDetails()
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
                        title.value = null
                        countryDetails.value = null
                        loadError.value = true
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
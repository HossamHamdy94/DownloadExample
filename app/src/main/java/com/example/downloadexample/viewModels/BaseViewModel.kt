package com.example.downloadexample.viewModels

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    val compositeSubscription: CompositeDisposable = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        compositeSubscription.clear()
    }
}
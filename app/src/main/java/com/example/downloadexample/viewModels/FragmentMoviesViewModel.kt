package com.example.downloadexample.viewModels

import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.downloadexample.data.FileModel
import com.example.downloadexample.repos.GetFileRepo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class FragmentMoviesViewModel @ViewModelInject constructor(private var getFileRepo: GetFileRepo) : BaseViewModel() {


    var filesLiveData = MutableLiveData<ArrayList<FileModel>>()


    // used when Sever Is Down for testing only
    var files =  ArrayList<FileModel> ()
    fun addfiles ()  {
        files.add(FileModel("https://kotlinlang.org/docs/kotlin-reference.pdf","Video1","Vidoe"))
        files.add(FileModel("https://kotlinlang.org/docs/kotlin-reference.pdf","Video1","pdf"))
        files.add(FileModel("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4","Video1","Vidoe"))
        files.add(FileModel("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4","Video1","Vidoe"))
        files.add(FileModel("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4","Video1","Vidoe"))
        files.add(FileModel("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4","Video1","Vidoe"))

    }

    fun getMovies () {

        filesLiveData = MutableLiveData()
        compositeSubscription.add(getFileRepo.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                filesLiveData.postValue(result)
            }, { error ->
                val generalResponse = ArrayList<FileModel> ()
                filesLiveData.postValue(generalResponse)
            }))

    }


}
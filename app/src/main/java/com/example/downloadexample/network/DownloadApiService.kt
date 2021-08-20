package com.example.downloadexample.network

import com.example.downloadexample.data.FileModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface DownloadApiService {


    @GET("movies")
    fun getMovies(): Observable<ArrayList<FileModel>>




}
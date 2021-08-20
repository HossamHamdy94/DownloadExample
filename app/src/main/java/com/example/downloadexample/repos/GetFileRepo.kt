package com.example.downloadexample.repos

import com.example.downloadexample.data.FileModel
import com.example.downloadexample.network.DownloadApiService
import io.reactivex.rxjava3.core.Observable
import jp.wasabeef.glide.transformations.internal.Utils
import javax.inject.Inject

class GetFileRepo  @Inject constructor(var downloadApiService: DownloadApiService) {

    fun getMovies() : Observable<ArrayList<FileModel>>
    {
        return downloadApiService.getMovies()
    }

}
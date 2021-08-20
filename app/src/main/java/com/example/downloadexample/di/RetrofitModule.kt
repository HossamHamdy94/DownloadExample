package com.example.downloadexample.di

import com.example.downloadexample.network.DownloadApiService
import com.example.downloadexample.utils.NetworkHelper.getBaseUrl
import com.example.downloadexample.utils.NetworkHelper.getOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule
{
    @Provides
    fun downloadApiService () : DownloadApiService
    {
        return Retrofit.Builder()
            .client(getOkHttpClient())
            .baseUrl(getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(DownloadApiService::class.java)

    }

}
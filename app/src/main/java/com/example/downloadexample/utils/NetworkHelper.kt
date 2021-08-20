package com.example.downloadexample.utils

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

import com.intuit.sdp.BuildConfig


object NetworkHelper {


    private const val baseUrlDebug              = "https://nagwa.free.beeceptor.com/"
    private const val baseUrlRelease            = "https://nagwa.free.beeceptor.com/"
    private lateinit var okHttpClient           :  OkHttpClient
    fun
            getBaseUrl() : String
    {
        return if(BuildConfig.DEBUG)
        baseUrlDebug
        else baseUrlRelease
    }


    fun getOkHttpClient() : OkHttpClient
    {
        val body = HttpLoggingInterceptor()
        body.apply { body.level = HttpLoggingInterceptor.Level.BODY }
        val header = HttpLoggingInterceptor()
        header.apply { header.level = HttpLoggingInterceptor.Level.HEADERS }

       /* if(user!=null&&!user?.authToken.isNullOrBlank())
            Log.e(TAG, "getOkHttpClient: ${user?.authToken!!}", )
*/
        if(BuildConfig.DEBUG)
        {
            okHttpClient = OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(body)

                .addInterceptor { chain ->
                    val original = chain.request()
                    val request =

                                original.newBuilder()
                                        .header("Content-Type", "application/json")


                                        .build()



                        chain.proceed(request)
                }.build()
        }else
        {
            okHttpClient = OkHttpClient.Builder()
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .addInterceptor(body)
                    .addInterceptor { chain ->
                        val original = chain.request()

                        val request =

                                    original.newBuilder()
                                            .header("Content-Type", "application/json")
                                            .build()



                        chain.proceed(request)
                    }.build()
        }

        return okHttpClient
    }


}
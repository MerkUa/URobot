package com.urobot.droid

import android.util.Log
import com.google.gson.GsonBuilder
import com.urobot.droid.Network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Apifactory {

    private fun provideOkHttpClientBuilder(
            interceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder =
            OkHttpClient.Builder()
                    .addInterceptor(interceptor)


    private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.d("retrofit", message)
            }).apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }


    fun create(): ApiService {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(
                        GsonConverterFactory.create(
                                GsonBuilder()
                                        .setLenient()
                                        .create()
                        )
                )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClientBuilder(provideLoggingInterceptor()).build())
                .baseUrl("https://urobot.ml/api/v1/")
                .build()

        return retrofit.create(ApiService::class.java);
    }

}
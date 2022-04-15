package com.example.dailyqoutesmotivational.network

import com.example.dailyqoutesmotivational.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitBuilder {

    fun makeRetrofitService(): ApiInterface {

        val builder: OkHttpClient.Builder = OkHttpClient().newBuilder()
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor)
        }

        val client: OkHttpClient = builder.build()

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        return retrofit.create(ApiInterface::class.java)
    }
}
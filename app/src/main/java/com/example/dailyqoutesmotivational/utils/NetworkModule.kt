package com.example.dailyqoutesmotivational.utils

import com.example.dailyqoutesmotivational.network.Constants
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofitService(okHttpClient: OkHttpClient?): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    //.cache(new Cache(MvpApplication.getInstance().getCacheDir(), 10 * 1024 * 1024)) // 10 MB
    //.addNetworkInterceptor(new StethoInterceptor())
    @get:Singleton
    @get:Provides
    val httpClient: OkHttpClient
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient()
                .newBuilder() //.cache(new Cache(MvpApplication.getInstance().getCacheDir(), 10 * 1024 * 1024)) // 10 MB
                .connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(AddHeaderInterceptor()) //.addNetworkInterceptor(new StethoInterceptor())
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .build()
        }

    inner class AddHeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            builder.addHeader("X-Requested-With", "XMLHttpRequest")
            return chain.proceed(builder.build())
        }
    }
}
package com.vb.store.di


import com.vb.store.BuildConfig
import com.vb.store.api.StoreApi
import com.vb.store.constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
               if (BuildConfig.DEBUG) {
                   val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
                   loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                   clientBuilder.addInterceptor(loggingInterceptor)
               }
        return clientBuilder.build()
    }

    @Provides
    fun createMovieApi(retrofit: Retrofit): StoreApi = retrofit.create(StoreApi::class.java)
}
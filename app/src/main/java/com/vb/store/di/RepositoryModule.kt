package com.vb.store.di

import com.vb.store.api.StoreApi
import com.vb.store.ui.store.StoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideMovieRepository(api: StoreApi): StoreRepository {
        return StoreRepository(api)
    }
}
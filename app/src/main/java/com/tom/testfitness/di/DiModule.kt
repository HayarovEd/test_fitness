package com.tom.testfitness.di


import com.tom.testfitness.data.repository.RemoteRepositoryImpl
import com.tom.testfitness.domain.repository.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DiModule {

    @Binds
    @Singleton
    abstract fun bindDataStore(remoteRepositoryImpl: RemoteRepositoryImpl): RemoteRepository

}
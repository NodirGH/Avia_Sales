package com.example.aviasales.di.domain

import com.example.data.repositoryImpl.TicketsRepositoryImpl
import com.example.domain.remote.TicketsApi
import com.example.domain.repository.TicketsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object DataModule {

//    @[Singleton Provides]
//    fun provideTicketsRepository(api: TicketsApi) : TicketsRepository = TicketsRepositoryImpl(api)
//}
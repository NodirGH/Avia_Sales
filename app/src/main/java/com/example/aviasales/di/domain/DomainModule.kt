package com.example.aviasales.di.domain

import com.example.domain.repository.TicketsRepository
import com.example.domain.usecases.TicketsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//@Module
//@InstallIn(SingletonComponent::class)
//object DomainModule {
//
//    @Provides
//    fun provideTicketsUseCase(ticketsRepository: TicketsRepository) = TicketsUseCase(ticketsRepository)
//}
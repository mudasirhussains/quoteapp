package com.example.dailyqoutesmotivational.utils

import com.example.dailyqoutesmotivational.network.BaseRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface BaseComponent {
    fun inject(repository: BaseRepository?)
}

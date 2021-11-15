package com.example.movies.utils.di.component

import com.example.movies.utils.database.DbModule
import com.example.movies.utils.di.module.AppModule
import com.example.movies.utils.di.module.RepoModule
import com.example.movies.utils.network.UrlModule
import com.example.movies.utils.network.NetworkModule
import com.example.movies.utils.di.builder.ActivityBuilder
import android.app.Application
import com.example.movies.PopMovApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidInjectionModule::class), (AppModule::class), (DbModule::class),
    (NetworkModule::class), (UrlModule::class),(RepoModule::class), (ActivityBuilder::class)])

interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: PopMovApp)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
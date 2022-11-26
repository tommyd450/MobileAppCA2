package org.com.animaltracker.main

import android.app.Application
import org.com.animaltracker.model.AnimalMemStore
import timber.log.Timber

class MainApp : Application() {

    lateinit var animals : AnimalMemStore

    override fun onCreate() {
        print("here")
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        animals = AnimalMemStore()
        Timber.i("Placemark started")
    }

}
package org.com.animaltracker.main

import android.app.Application
import org.com.animaltracker.model.AnimalMemStore
import timber.log.Timber

class MainApp : Application() {

    val animals = AnimalMemStore()

    override fun onCreate() {
        print("here")
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Placemark started")
    }

}
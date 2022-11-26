package org.com.animaltracker.main

import android.app.Application
import org.com.animaltracker.model.AnimalJSONStore
import org.com.animaltracker.model.AnimalMemStore
import org.com.animaltracker.model.AnimalStore
import timber.log.Timber

class MainApp : Application() {

    lateinit var animals : AnimalStore

    override fun onCreate() {
        print("here")
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        animals = AnimalJSONStore(applicationContext)
        Timber.i("Placemark started")
    }

}
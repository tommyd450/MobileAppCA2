package org.com.animaltracker.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimalModel(var id: Long = 0,
                          var uid: String = "",
                          var publicVisibility: Boolean = false,
                          var title: String = "",
                          var description: String = "",
                          var genus: String = "",
                          var image: Uri = Uri.EMPTY,
                          var location: Location = Location(52.245696, -7.139102, 15f)  ) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
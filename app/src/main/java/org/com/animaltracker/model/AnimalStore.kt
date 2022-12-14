package org.com.animaltracker.model

interface AnimalStore {
    fun findAll(): List<AnimalModel>
    fun findAllUser(uid: String) : List<AnimalModel>
    fun create(animal: AnimalModel)
    fun update(animal: AnimalModel)
    fun delete(animal: AnimalModel)
}
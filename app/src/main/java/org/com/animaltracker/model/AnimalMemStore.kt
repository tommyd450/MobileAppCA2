package org.com.animaltracker.model

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class AnimalMemStore : AnimalStore {

    val animals = ArrayList<AnimalModel>()

    override fun findAll(): List<AnimalModel> {
        return animals
    }

    override fun create(animal: AnimalModel) {
        animal.id = getId()
        animals.add(animal)
        logAll()
    }

    override fun update(animal: AnimalModel) {
        var foundAnimal: AnimalModel? = animals.find { p -> p.id == animal.id }
        if (foundAnimal != null) {
            foundAnimal.title = animal.title
            foundAnimal.description = animal.description
            foundAnimal.image = animal.image
            logAll()
        }
    }

    override fun delete(animal: AnimalModel) {
        var foundAnimal: AnimalModel? = animals.find { p -> p.id == animal.id }
        if (foundAnimal != null) {
            animals.remove(animal)
            logAll()
        }
    }

    private fun logAll() {
        animals.forEach { i("$it") }
    }
}
package org.com.animaltracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.com.animaltracker.databinding.CardAnimalBinding
import org.com.animaltracker.model.AnimalModel

interface AnimalListener {
    fun onAnimalClick(animal: AnimalModel)
    fun onDeleteButtonClick(animal: AnimalModel)
}

class AnimalAdapter constructor(private var animals: List<AnimalModel>,
                                   private val listener: AnimalListener) :
    RecyclerView.Adapter<AnimalAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardAnimalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }




    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val animal = animals[holder.adapterPosition]
        holder.bind(animal, listener)
    }

    override fun getItemCount(): Int = animals.size

    class MainHolder(private val binding : CardAnimalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(animal: AnimalModel, listener: AnimalListener) {
            binding.animalTitle.text = animal.title
            binding.description.text = animal.description
            Picasso.get()
                .load(animal.image)
                .into(binding.animalImage)
            binding.root.setOnClickListener { listener.onAnimalClick(animal) }
            binding.buttonDelete.setOnClickListener()
            {
                listener.onDeleteButtonClick(animal)
            }
        }
    }
}
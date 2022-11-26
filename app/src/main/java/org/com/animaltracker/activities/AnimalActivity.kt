package org.com.animaltracker.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.com.animaltracker.R
import org.com.animaltracker.databinding.ActivityAnimalBinding
import org.com.animaltracker.helpers.showImagePicker
import org.com.animaltracker.main.MainApp
import org.com.animaltracker.model.AnimalModel
import org.com.animaltracker.activities.MapActivity
import org.com.animaltracker.model.Location
import timber.log.Timber.i

class AnimalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimalBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var animal = AnimalModel()
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    lateinit var app: MainApp
    var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp
        i("Animal Activity started...")

        if (intent.hasExtra("animal_edit")) {
            animal = intent.extras?.getParcelable("animal_edit")!!
            binding.animalSpecies.setText(animal.title)
            binding.animalDescription.setText(animal.description)
            Picasso.get()
                .load(animal.image)
                .into(binding.animalImage)
            binding.btnAdd.setText(R.string.button_editAnimal)
            binding.chooseImage.setText(R.string.button_editImage)
        }



        binding.btnAdd.setOnClickListener() {
            animal.title = binding.animalSpecies.text.toString()
            animal.description = binding.animalDescription.text.toString()

            //animal.image = binding.animalImage.
            if (animal.title.isNotEmpty() && !intent.hasExtra("animal_edit")) {
                app.animals.create(animal.copy())
                i("add Button Pressed: ${animal}")


                setResult(RESULT_OK)
                finish()
            }
            else if(animal.title.isNotEmpty() && intent.hasExtra("animal_edit")){
                app.animals.update(animal)
                i("Update/Save Button Pressed: ${animal}")
                setResult(RESULT_OK)
                finish()
            }

            else {
                Snackbar.make(it,R.string.placemarkError, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.animalLocation.setOnClickListener {
            if(intent.hasExtra("animal_edit"))
            {
                val location = animal.location
                val launcherIntent = Intent(this, MapActivity::class.java)
                    .putExtra("location", location)
                mapIntentLauncher.launch(launcherIntent)

            }else if (!intent.hasExtra("animal_edit"))
            {
                val location = animal.location
                val launcherIntent = Intent(this, MapActivity::class.java)
                    .putExtra("location", location)
                mapIntentLauncher.launch(launcherIntent)

            }
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            animal.image = result.data!!.data!!
                            Picasso.get()
                                .load(animal.image)
                                .into(binding.animalImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            location = result.data!!.extras?.getParcelable("location")!!
                            animal.location = location
                            i("Location == $location")
                        } // end of if
                    }
                    RESULT_CANCELED -> {} else -> {}
                }
            }
    }
}

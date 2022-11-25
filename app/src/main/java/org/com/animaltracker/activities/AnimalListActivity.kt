package org.com.animaltracker.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.com.animaltracker.R
import org.com.animaltracker.adapters.AnimalAdapter
import org.com.animaltracker.adapters.AnimalListener
import org.com.animaltracker.databinding.ActivityAnimalListBinding
import org.com.animaltracker.main.MainApp
import org.com.animaltracker.model.AnimalModel

class AnimalListActivity : AppCompatActivity(), AnimalListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityAnimalListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = AnimalAdapter(app.animals.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, AnimalActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.animals.findAll().size)
            }
        }

    override fun onAnimalClick(animal: AnimalModel) {
        val launcherIntent = Intent(this, AnimalActivity::class.java)
        launcherIntent.putExtra("animal_edit", animal)
        getClickResult.launch(launcherIntent)
    }

    override fun onDeleteButtonClick(animal: AnimalModel) {
        val launcherIntent = Intent(this, AnimalListActivity::class.java)
        app.animals.delete(animal)
        finish()
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.animals.findAll().size)
            }
        }
}
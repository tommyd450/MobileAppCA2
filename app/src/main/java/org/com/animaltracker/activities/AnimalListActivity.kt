package org.com.animaltracker.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.com.animaltracker.R
import org.com.animaltracker.adapters.AnimalAdapter
import org.com.animaltracker.adapters.AnimalListener
import org.com.animaltracker.databinding.ActivityAnimalListBinding
import org.com.animaltracker.main.MainApp
import org.com.animaltracker.model.AnimalModel
import org.com.animaltracker.activities.LoginSignUpActivity

class AnimalListActivity : AppCompatActivity(), AnimalListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityAnimalListBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        auth = LoginSignUpActivity().auth
        app = application as MainApp
        val layoutManager = LinearLayoutManager(this)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = AnimalAdapter(app.animals.findAllUser(auth.currentUser!!.uid),this)
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
            R.id.item_logout ->{

                val launcherIntent = Intent(this, LoginSignUpActivity::class.java)
                getResult.launch(launcherIntent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {

            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0,app.animals.findAllUser(auth.currentUser!!.uid).size)
            }
        }

    override fun onAnimalClick(animal: AnimalModel) {
        val launcherIntent = Intent(this, AnimalActivity::class.java)
        launcherIntent.putExtra("animal_edit", animal)
        getClickResult.launch(launcherIntent)
    }

    override fun onDeleteButtonClick(animal: AnimalModel) {
        app.animals.delete(animal)
        binding.recyclerView.adapter = AnimalAdapter(app.animals.findAllUser(auth.currentUser!!.uid),this)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0,app.animals.findAllUser(auth.currentUser!!.uid).size)
            }
        }
}
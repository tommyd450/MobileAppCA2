package org.com.animaltracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.com.animaltracker.R
import org.com.animaltracker.databinding.ActivityLoginSignUpBinding
import timber.log.Timber


class LoginSignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginSignUpBinding
    var auth: FirebaseAuth = Firebase.auth
    //private lateinit var user : FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //auth = Firebase.auth
        auth.signOut()

        binding.login.setOnClickListener()
        {
            var email:String = binding.email.text.toString()
            var password:String = binding.password.text.toString()

            if(email != "" && password!="") {
                signIn(email, password)
                Timber.i("here" + auth.currentUser)
                if (auth.currentUser != null) {
                    Timber.i("Button Pressed")
                    val launcherIntent = Intent(this, AnimalListActivity::class.java)
                    startActivity(launcherIntent)
                    finish()
                }
            }
        }

        binding.signup.setOnClickListener()
        {
            var email:String = binding.email.text.toString()
            var password:String = binding.password.text.toString()

            if(email != "" && password!="") {
                createAccount(email, password)
                Timber.i("here" + auth.currentUser)
                if (auth.currentUser != null) {
                    Timber.i("Button Pressed")
                    val launcherIntent = Intent(this, AnimalListActivity::class.java)
                    startActivity(launcherIntent)
                    finish()
                }
            }
        }

    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                } else {
                }
            }
        // [END create_user_with_email]
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Timber.i("Button Pressed"+task.result)

                } else {

                }
            }
        // [END sign_in_with_email]
    }


}
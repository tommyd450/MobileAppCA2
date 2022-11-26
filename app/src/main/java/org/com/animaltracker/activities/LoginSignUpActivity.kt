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
    private lateinit var auth: FirebaseAuth
    private lateinit var user : FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth


        binding.login.setOnClickListener()
        {
            signIn(binding.email.text.toString(),binding.password.text.toString())
            Timber.i("here"+auth.currentUser)
            if(auth.currentUser!=null){
                Timber.i("Button Pressed")
                val launcherIntent = Intent(this, AnimalListActivity::class.java)
                startActivity(launcherIntent)
                finish()
            }

        }

    }

    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "createUserWithEmail:success")
                    user = auth.currentUser!!

                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    //Toast.makeText(baseContext, "Authentication failed.",
                        //Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "signInWithEmail:success")
                    ///user = task.result.user
                    Timber.i("Button Pressed"+task.result)

                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "signInWithEmail:failure", task.exception)
                    //Toast.makeText(baseContext, "Authentication failed.",
                        //Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }


}
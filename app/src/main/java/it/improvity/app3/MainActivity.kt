package it.improvity.app3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
// Libreria per autenticazione
import com.google.firebase.auth.FirebaseAuth
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    // oggetto per l'autenticazione generato con getInstance()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // **************************************
        val md5 = md5("ziobanana")
        // **************************************
        registerUser("marco.tripolini@gmail.com", "ziobanana")
    }

    fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    val user = auth.currentUser
                    Log.w("Registration", "createUserWithEmail: ok")
                } else {
                    Log.w("Registration", "createUserWithEmail:failure", task.exception)
                }
            }
    }

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login successful
                    val user = auth.currentUser
                } else {
                    // Login failed
                    // email o password errati!
                    // oppure utente non registrato!
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                }
            }
    }
    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        return digest.joinToString("") {
            String.format("%02x", it)
        }
    }
}
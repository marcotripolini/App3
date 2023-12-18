package it.improvity.app3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.speech.tts.TextToSpeech
// Libreria per autenticazione
import com.google.firebase.auth.FirebaseAuth
import java.security.MessageDigest


class MainActivity : AppCompatActivity() {
    // oggetto per l'autenticazione generato con getInstance()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var tts: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // speakOut()
        // **************************************
        val md5 = md5("ziobanana")
        // **************************************
        // registerUser("marco.tripolini@outlook.it", "ziobanana")
        // loginUser("marco.tripolini@outlook.it","ziobanana")
        // prima excetpion:
        // loginUser("marco.tripolini@outlook.ot","ziobanana")
        // nuovo caso d'uso: utente già registrato
        // che tenta di registrarsi nuovamente
        registerUser("marco.tripolini@outlook.it", "ziobanana")

        // *****************************************
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // L'utente è connesso
        } else {
            // L'utente non è connesso
        }

        // *****************************************
        // logout dell'utente
        FirebaseAuth.getInstance().signOut()
        // *****************************************


    }

    private fun speakOut() {
        val text = "La Vispa Teresa"
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }



    // due fasi di autenticazione
    // 1. registrazione utente
    // io passo email e pwd
    // se l'utente non esiste, viene creato
    // se l'utente esiste, viene restituito un errore
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

    // dopo aver fatto la registerUser ti salvi l'utente e la password dentro delle SharedPreferences e poi fai il login
    // 1) Ti leggi le shared preferences e recuperi i dati di login
    // 2) Fai il login con loginUser
    // ci sono due casi d'uso ha successo oppure no

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login successful
                    val user = auth.currentUser
                    Log.w("Login", "signInWithEmail:success")
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
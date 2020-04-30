package com.example.neighborhoodwork

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_utworz_konto_email.*

class utworzKontoEmail : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utworz_konto_email)
        auth = FirebaseAuth.getInstance()

        tx3WoliszUzycTel.setOnClickListener {
            var nowaAktywnosc = Intent(applicationContext, utworzNrTel::class.java)
            startActivity(nowaAktywnosc)
        }

        tx3Utworz.setOnClickListener {
            if(sprawdziDane()) dodajUzytkownika()

        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(currentUser : FirebaseUser?){
        if(currentUser != null){
            if(currentUser.isEmailVerified){
                var nowaAktywnosc = Intent(applicationContext, Home::class.java)
                startActivity(nowaAktywnosc)
                finish()
            };else {
                Toast.makeText(baseContext, "Zweryfikuj swojego e-maila", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun sprawdziDane() : Boolean{
        var poprawnosc = false
        if(Etx3Email.length()>5&&Etx3Nazwa.text.length>6&&Etx3Haslo.text.length>=8){
            poprawnosc = true
        };else{

            if(Etx3Email.length()<=5){
                img3warning1.visibility = View.VISIBLE
                tx3warning1.visibility = View.VISIBLE
                tx3warning1.setText("Podano nie poprawny e-mail")
            };else {
                img3warning1.visibility = View.INVISIBLE
                tx3warning1.visibility = View.INVISIBLE
            }


            if(Etx3Nazwa.text.length<=6){
                img3warning2.visibility = View.VISIBLE
                tx3warning2.visibility = View.VISIBLE
                tx3warning2.setText("Podano niepoprawne imie i nazwisko")
            };else{
                img3warning2.visibility = View.INVISIBLE
                tx3warning2.visibility = View.INVISIBLE
            }


            if(Etx3Haslo.text.length<8){
                img3warning3.visibility = View.VISIBLE
                tx3warning3.visibility = View.VISIBLE
                tx3warning3.setText("Twoje hasło musi zawierać\nprzynajmniej 8 znaków")
            };else{
                img3warning3.visibility = View.INVISIBLE
                tx3warning3.visibility = View.INVISIBLE
            }

        }
        return poprawnosc
    }

    fun dodajUzytkownika(){
        auth.createUserWithEmailAndPassword(Etx3Email.text.toString(), Etx3Haslo.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                var nowaAktywnosc = Intent(applicationContext, Home::class.java)
                                startActivity(nowaAktywnosc)
                                Toast.makeText(baseContext, "Pamiętaj aby zweryfikować swoje konto E-mail", Toast.LENGTH_LONG).show()
                                finish()
                            }
                        }

                } else {
                    Toast.makeText(baseContext, "Nie Udało się połączyć z baządanych", Toast.LENGTH_LONG).show()
                }
            }
    }
}

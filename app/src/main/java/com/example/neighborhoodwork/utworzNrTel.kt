package com.example.neighborhoodwork

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_utworz_nr_tel.*
import java.util.concurrent.TimeUnit

class utworzNrTel : AppCompatActivity() {

    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utworz_nr_tel)
        tx7Wolisz.setOnClickListener {
            var nowaAktywnosc = Intent(applicationContext, utworzKontoEmail::class.java)
            startActivity(nowaAktywnosc)
        }
        tx7Utworz.setOnClickListener {
            if(sprawdziDane()){
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+48606309379", 180, TimeUnit.SECONDS, this, mCallbacks )
                veryfikacjaCallbacks()

            }
        }
    }

   fun veryfikacjaCallbacks(){
       mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

           override fun onVerificationCompleted(credential: PhoneAuthCredential) {
           }

           override fun onVerificationFailed(e: FirebaseException) {
           }

           override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {

           }
       }


   }


    fun sprawdziDane() : Boolean{
        var poprawnosc = false
        if(Etx7Tel.length()>=9&&Etx7Nazwa.text.length>6&&Etx7Haslo.text.length>=8){
            poprawnosc = true
        };else{

            if(Etx7Tel.length()<9){
                img7warning1.visibility = View.VISIBLE
                tx7warning1.visibility = View.VISIBLE
                tx7warning1.setText("Podano nie poprawny e-mail")
            };else {
                img7warning1.visibility = View.INVISIBLE
                tx7warning1.visibility = View.INVISIBLE
            }


            if(Etx7Nazwa.text.length<=6){
                img7warning2.visibility = View.VISIBLE
                tx7warning2.visibility = View.VISIBLE
                tx7warning2.setText("Podano niepoprawne imie i nazwisko")
            };else{
                img7warning2.visibility = View.INVISIBLE
                tx7warning2.visibility = View.INVISIBLE
            }


            if(Etx7Haslo.text.length<8){
                img7warning3.visibility = View.VISIBLE
                tx7warning3.visibility = View.VISIBLE
                tx7warning3.setText("Twoje hasło musi zawierać\nprzynajmniej 8 znaków")
            };else{
                img7warning3.visibility = View.INVISIBLE
                tx7warning3.visibility = View.INVISIBLE
            }

        }
        return poprawnosc
    }
}

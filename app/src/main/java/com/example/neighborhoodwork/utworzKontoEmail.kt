package com.example.neighborhoodwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_utworz_konto_email.*
import android.view.View

class utworzKontoEmail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utworz_konto_email)
        tx3Utworz.setOnClickListener {
            if(Etx3Email.length()>5&&Etx3Nazwa.text.length>6&&Etx3Haslo.text.length>=8){
                var nowaAktywnosc = Intent(applicationContext, Home::class.java)
                startActivity(nowaAktywnosc)
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

        }
    }
}

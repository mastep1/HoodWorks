package com.example.neighborhoodwork

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View

import kotlinx.android.synthetic.main.edit_profil.*

public class EditProfil : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profil)

        afterStarted()
    }

     fun afterStarted(){
        if(user.imie == "") {
            Etx7Imie.hint = "NIE PODANO"
        } else{
            Etx7Imie.setText(user.imie)
        }
        if(user.email == "") {
            Etx7Email.hint = "NIE PODANO"
        } else{
            Etx7Email.setText(user.email)
        }
        if(user.tel == "") {
            Etx7Tel.hint = "NIE PODANO"
        } else{
            Etx7Tel.setText(user.tel)
        }
        if(user.opis == "") {
            Etx7Opis.hint = "NIE PODANO"
        } else{
            Etx7Opis.setText(user.opis)
        }

        Etx7Imie.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(Etx7Imie.text.toString()!=user.imie){
                    tx7ZmianyImie.visibility = View.VISIBLE
                }else{
                    tx7ZmianyImie.visibility = View.INVISIBLE
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        Etx7Email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(Etx7Email.text.toString()!=user.email){
                    tx7ZmianyEmail.visibility = View.VISIBLE
                }else{
                    tx7ZmianyEmail.visibility = View.INVISIBLE
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        Etx7Tel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(Etx7Tel.text.toString()!=user.tel){
                    tx7ZmianyTel.visibility = View.VISIBLE
                }else{
                    tx7ZmianyTel.visibility = View.INVISIBLE
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        Etx7Opis.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(Etx7Opis.text.toString()!=user.opis){
                    tx7ZmianyOpis.visibility = View.VISIBLE
                }else{
                    tx7ZmianyOpis.visibility = View.INVISIBLE
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

         FAB7Commit.setOnClickListener {
             commit()
         }

         BT7Commmit.setOnClickListener {
             commit()
         }


    }

    fun commit(){

        if(Etx7Imie.text.toString() != user.imie){
            user.imie = Etx7Imie.text.toString()
            zakoncz(applicationContext)
        }

        if(Etx7Email.text.toString() != user.email){

            val fm = supportFragmentManager
            val F_Map = Verification("kk", applicationContext)
            fm.beginTransaction().add(R.id.l7Main, F_Map).commit()

        }

        if(Etx7Tel.text.toString() != user.tel){
            fireBaseConnection().ustawTel(Etx7Tel.text.toString())
            zakoncz(applicationContext)
        }

        if(Etx7Opis.text.toString() != user.opis){
            fireBaseConnection().ustawOpis(Etx7Opis.text.toString())
            zakoncz(applicationContext)
        }


    }

    public fun zakoncz(context: Context){
        var powrot = Intent(context , Profil::class.java)
        startActivity(powrot)
    }




}








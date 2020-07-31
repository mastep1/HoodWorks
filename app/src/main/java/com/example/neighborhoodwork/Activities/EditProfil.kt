package com.example.neighborhoodwork.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.neighborhoodwork.*
import com.example.neighborhoodwork.Fragments.Verification
import com.example.neighborhoodwork.support.dane
import com.example.neighborhoodwork.support.fireBaseConnection
import com.example.neighborhoodwork.support.user
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.edit_profil.*

class EditProfil : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profil)

        afterStart()
    }

    private fun afterStart(){
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
                if(Etx7Imie.text.toString()!= user.imie){
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
                if(Etx7Email.text.toString()!= user.email){
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
                if(Etx7Tel.text.toString()!= user.tel){
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
                if(Etx7Opis.text.toString()!= user.opis){
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

         FAB7Save.setOnClickListener {
             commitMaster()
         }

         BT7Commmit.setOnClickListener {
             commitMaster()
         }

         SBT7Haslo.setOnClickListener {
             if(SBT7Haslo.text=="on"){
                 SBT7Haslo.text = "off"
                 Etx7CurrentPasword.visibility = View.GONE
                 Etx7NewPassword.visibility = View.GONE
                 Etx7RepeatPassword.visibility = View.GONE
                 tx7HasloZmiany.visibility = View.GONE
             }else{
                 SBT7Haslo.text = "on"
                 Etx7CurrentPasword.visibility = View.VISIBLE
                 Etx7NewPassword.visibility = View.VISIBLE
                 Etx7RepeatPassword.visibility = View.VISIBLE
             }
         }


    }


    private fun commitMaster(){

        if(Etx7Imie.text.toString() != user.imie){
            user.imie = Etx7Imie.text.toString()
        }

        if(Etx7Email.text.toString() != user.email){

            val frag = supportFragmentManager
            val F_verifiction = Verification(
                "kk",
                applicationContext
            )
            frag.beginTransaction().add(R.id.l7Main, F_verifiction).commit()

        }

        if(Etx7Tel.text.toString() != user.tel){
            fireBaseConnection().ustawTel(Etx7Tel.text.toString())
        }

        commmitDescription(Etx7Opis.text.toString())

        commitPasword()

        
        finishIFShould(this)
    }


    private fun commmitDescription(opis : String){
         if(opis!=user.opis){
             changeDescription(opis)
         }

    }

    private fun commitPasword(){

        if(SBT7Haslo.text == "on"){

            var new = Etx7NewPassword.text.toString()
            var repeaidNew = Etx7RepeatPassword.text.toString()
            var current = Etx7CurrentPasword.text.toString()

            if( new == repeaidNew &&
                current !="" &&
                new !="" &&
                repeaidNew !=""&&
                new != current){

                changePassword(current, new, tx7HasloZmiany)

            }

            else if(current==""||
                new==""||
                repeaidNew==""){

                tx7HasloZmiany.text = "Wypełnij wszystkie pola"
                tx7HasloZmiany.visibility = View.VISIBLE

            }
            else if(new != repeaidNew){

                tx7HasloZmiany.text = "Hasła są różne od siebie"
                tx7HasloZmiany.visibility = View.VISIBLE

            }
            else if(new == current){

                tx7HasloZmiany.text = "Nowa hasło jest takie samo jak obecne"
                tx7HasloZmiany.visibility = View.VISIBLE

            }
        }
    }



    private fun changePassword(currentPasword: String, newPassword : String, tx : TextView){

        if (dane.currentUser != null && dane.currentUser.email != null){


            val credential = EmailAuthProvider.getCredential(dane.currentUser.email!!, currentPasword)

            dane.currentUser?.reauthenticate(credential)
                ?.addOnCompleteListener {
                    if(it.isSuccessful){
                        dane.currentUser?.updatePassword(newPassword)
                            ?.addOnCompleteListener { task ->
                                if(task.isSuccessful){
                                    tx.text = ""
                                    tx.visibility = View.GONE
                                }else{
                                    tx.text = "Błąd połączenia z internetem"
                                    tx.visibility = View.VISIBLE
                                }
                            }
                    } else {
                        tx.text = "Błędne hasło, spróbuj ponownie"
                        tx.visibility = View.VISIBLE
                    }
                }
        }

    }

    private fun changeDescription(description: String){
        var bazaDane = FirebaseDatabase.getInstance()
        var link = bazaDane.getReference("Users")
        user.opis = description

        var id = dane.currentUser!!.displayName.toString()
        link.child(id).child("Data").child("opis").setValue("$description")
    }


    private fun finishIFShould(context: Context){

        if(tx7ZmianyImie.visibility==View.GONE&&
            tx7ZmianyEmail.visibility==View.GONE&&
            tx7ZmianyTel.visibility==View.GONE&&
            tx7ZmianyOpis.visibility==View.GONE&&
            tx7HasloZmiany.visibility==View.GONE){
            var powrot = Intent(context , Profil::class.java)
            startActivity(powrot)
            finish()
        }

    }
}








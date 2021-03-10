package com.example.neighborhoodwork.Activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import com.example.neighborhoodwork.support.user
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.edit_profil.*


class EditProfil : AppCompatActivity() {


    var wykonano = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profil)

        afterStart()
    }

    private fun afterStart(){
        if(dane.currentUser.displayName == "") {
            Etx7Imie.hint = "NIE PODANO"
        } else{
            Etx7Imie.setText(dane.currentUser.displayName)
        }
        if(dane.currentUser.email == "") {
            Etx7Email.hint = "NIE PODANO"
        } else{
            Etx7Email.setText(dane.currentUser.email)
        }
        if(dane.currentUser.phoneNumber == "") {
            Etx7Tel.hint = "NIE PODANO"
        } else{
            Etx7Tel.setText(dane.currentUser.phoneNumber)
        }
        if(dane.currentUsersDataUsers!!.description == "") {
            Etx7Opis.hint = "NIE PODANO"
        } else{
            Etx7Opis.setText(dane.currentUsersDataUsers!!.description)
        }

        Etx7Imie.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (Etx7Imie.text.toString() != dane.currentUser.displayName) {
                    tx7ZmianyImie.visibility = View.VISIBLE
                } else {
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
                if (Etx7Email.text.toString() != dane.currentUser.email) {
                    if (tx7EmailError.visibility != View.VISIBLE) {
                        tx7ZmianyEmail.visibility = View.VISIBLE
                    }
                } else {
                    if (tx7EmailError.visibility == View.VISIBLE) {
                        tx7ZmianyEmail.visibility = View.INVISIBLE
                    } else {
                        tx7ZmianyEmail.visibility = View.GONE
                    }
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        Etx7Tel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (tx7ErrorTel.visibility != View.VISIBLE) {
                    if (Etx7Tel.text.toString() != dane.currentUser.phoneNumber) {
                        tx7ZmianyTel.visibility = View.VISIBLE
                    } else {
                        tx7ZmianyTel.visibility = View.INVISIBLE
                    }
                } else {
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
                if (Etx7Opis.text.toString() != dane.currentUsersDataUsers!!.description) {
                    tx7ZmianyOpis.visibility = View.VISIBLE
                } else {
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

        SBT7Email.setOnClickListener {
            if(SBT7Email.text=="on"){
                SBT7Email.text = "off"
                Etx7Email.setText(user.email)
                Etx7PasswordForEmail.visibility = View.GONE
                tx7ZmianyEmail.visibility = View.GONE
                tx7EmailError.visibility = View.GONE
                Etx7Email.visibility = View.GONE

            }else{
                SBT7Email.text = "on"
                Etx7PasswordForEmail.visibility = View.VISIBLE
                Etx7Email.visibility = View.VISIBLE
            }
        }

        SBT7TEL.setOnClickListener {
            if(SBT7TEL.text=="on"){
                SBT7TEL.text = "off"
                Etx7Tel.setText(user.tel)
                Etx7PasswordForTel.visibility = View.GONE
                tx7ZmianyTel.visibility = View.GONE
                tx7ErrorTel.visibility = View.GONE
                Etx7Tel.visibility = View.GONE

            }else{
                SBT7TEL.text = "on"
                Etx7PasswordForTel.visibility = View.VISIBLE
                Etx7Tel.visibility = View.VISIBLE
            }
        }


    }




    private fun commitMaster(){

        var signInUser = false
        lateinit var credital : AuthCredential

        PB7.visibility = View.VISIBLE
        tx7Zapisywanie.visibility = View.VISIBLE

        wykonano = 0

        if(Etx7Imie.text.toString() != user.imie){
            changeUsername(Etx7Imie.text.toString())
        }else{
            finishIFShould()
        }


        if(SBT7Email.text == "on"){
            if(Etx7Email.text.toString()==user.email){
                tx7ZmianyEmail.visibility = View.INVISIBLE
                tx7EmailError.text = "Nowy email jest taki sam jak stary"
                tx7EmailError.visibility = View.VISIBLE
                tx7Zapisywanie.visibility = View.GONE
                PB7.visibility = View.GONE
            }else if(Etx7PasswordForEmail.text.toString()==""||Etx7Email.text.toString()==""){
                tx7ZmianyEmail.visibility = View.INVISIBLE
                tx7EmailError.text = "Wypełnij wszystkie pola"
                tx7EmailError.visibility = View.VISIBLE
                tx7Zapisywanie.visibility = View.GONE
                PB7.visibility = View.GONE
            }else{
                signInUser = true
                credital = loginUser(Etx7PasswordForEmail.text.toString())
                changeEmail(credital, Etx7Email.text.toString(), tx7EmailError, tx7ZmianyEmail)
            }

        }else{
            finishIFShould()
        }

        if(SBT7TEL.text == "on"){
            if(Etx7Tel.text.toString() == user.tel){
                tx7ErrorTel.text = "Nowy numer jest taki sam jak poprzedni"
                tx7ErrorTel.visibility = View.VISIBLE
                tx7ZmianyTel.visibility = View.INVISIBLE
                tx7Zapisywanie.visibility = View.GONE
                PB7.visibility = View.GONE

            }else if(Etx7Tel.text.toString() == "" || Etx7PasswordForTel.text.toString() == ""){
               tx7ErrorTel.text = "Wypełnij wszystkie pola"
                tx7ErrorTel.visibility = View.VISIBLE
                tx7ZmianyTel.visibility = View.INVISIBLE
                tx7Zapisywanie.visibility = View.GONE
                PB7.visibility = View.GONE
            }else{
                signInUser = true
                credital = loginUser(Etx7PasswordForTel.text.toString())
                changeTel(credital, Etx7Tel.text.toString().toInt(), tx7ErrorTel, tx7ZmianyTel)
            }
        }else{
            finishIFShould()
        }

        commmitDescription(Etx7Opis.text.toString())

        if(SBT7Haslo.text == "on"){

            var new = Etx7NewPassword.text.toString()
            var repeaidNew = Etx7RepeatPassword.text.toString()
            var current = Etx7CurrentPasword.text.toString()

            if( new == repeaidNew &&
                current !="" &&
                new !="" &&
                repeaidNew !=""&&
                new != current){
                    if(signInUser==false){
                        credital = loginUser(current)
                        signInUser = true
                    }
                changePassword(credital, new, tx7HasloZmiany)
            }

            else if(current==""||
                new==""||
                repeaidNew==""){

                tx7HasloZmiany.text = "Wypełnij wszystkie pola"
                tx7HasloZmiany.visibility = View.VISIBLE
                PB7.visibility = View.INVISIBLE
                tx7Zapisywanie.visibility = View.INVISIBLE
            }
            else if(new != repeaidNew){

                tx7HasloZmiany.text = "Hasła są różne od siebie"
                tx7HasloZmiany.visibility = View.VISIBLE
                PB7.visibility = View.INVISIBLE
                tx7Zapisywanie.visibility = View.INVISIBLE

            }
            else if(new == current){

                tx7HasloZmiany.text = "Nowa hasło jest takie samo jak obecne"
                tx7HasloZmiany.visibility = View.VISIBLE
                PB7.visibility = View.INVISIBLE
                tx7Zapisywanie.visibility = View.INVISIBLE

            }
        }else{
            finishIFShould()
        }
        
    }


    private fun commmitDescription(opis: String){
         if(opis!=user.opis){
             changeDescription(opis)
             finishIFShould()
         }else{
             finishIFShould()
         }
    }



    private fun loginUser(currentPasword: String) : AuthCredential {

        if(dane.currentUser != null && dane.currentUser.email != null){
            val credentialEmail = EmailAuthProvider.getCredential(
                dane.currentUser.email!!,
                currentPasword
            )

            return credentialEmail

        }else{
            val credentialPhone = PhoneAuthProvider.getCredential(
                dane.currentUser.phoneNumber!!,
                currentPasword
            )
            return credentialPhone
        }
    }


    private fun changeDescription(description: String){
        var bazaDane = FirebaseDatabase.getInstance()
        var link = bazaDane.getReference("Users")
        user.opis = description

        var id = dane.currentUser!!.displayName.toString()
        link.child(id).child("Data").child("opis").setValue("$description")
        finishIFShould()
    }

    private fun changeEmail(
        credential: AuthCredential,
        newEmail: String,
        tx: TextView,
        bait: TextView
    ){

        dane.currentUser?.reauthenticate(credential)
            ?.addOnCompleteListener {
                if(it.isSuccessful){
                    dane.currentUser?.updateEmail(newEmail)
                        ?.addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                tx.text = ""
                                tx.visibility = View.GONE
                                bait.visibility = View.GONE
                                finishIFShould()
                                user.email = newEmail
                            }else{
                                bait.visibility = View.INVISIBLE
                                tx.text = "Błąd połączenia z internetem"
                                tx.visibility = View.VISIBLE
                                PB7.visibility = View.INVISIBLE
                                tx7Zapisywanie.visibility = View.INVISIBLE
                            }
                        }
                } else {
                    bait.visibility = View.INVISIBLE
                    tx.text = "Błędne hasło, spróbuj ponownie"
                    tx.visibility = View.VISIBLE
                    PB7.visibility = View.INVISIBLE
                    tx7Zapisywanie.visibility = View.INVISIBLE
                }
            }
    }

    private fun changeUsername(newUsername: String){

        var newProfileException = UserProfileChangeRequest.Builder()
            .setDisplayName(newUsername)
            .build()

        dane.currentUser.updateProfile(newProfileException).addOnCompleteListener {
            if(it.isSuccessful()){
                finishIFShould()
            }
        }

        user.imie = Etx7Imie.text.toString()
    }

    private fun changeTel(
        credital: AuthCredential,
        newNumber: Int,
        txError: TextView,
        txZmiany: TextView
    ) {
        
        dane.currentUser?.reauthenticate(credital)
            ?.addOnCompleteListener {
                if(it.isSuccessful){
                    var phoneNumbere = newNumber.toString()
                    dane.currentUser.updatePhoneNumber(credital as PhoneAuthCredential)
                        ?.addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                txError.text = ""
                                txError.visibility = View.GONE
                                txZmiany.visibility = View.GONE
                                finishIFShould()
                                user.tel = newNumber.toString()
                            }else{
                                txZmiany.visibility = View.INVISIBLE
                                txError.text = "Błąd połączenia z internetem"
                                txError.visibility = View.VISIBLE
                                PB7.visibility = View.INVISIBLE
                                tx7Zapisywanie.visibility = View.INVISIBLE
                            }
                        }
                } else {
                    txZmiany.visibility = View.INVISIBLE
                    txError.text = "Błędne hasło, spróbuj ponownie"
                    txError.visibility = View.VISIBLE
                    PB7.visibility = View.INVISIBLE
                    tx7Zapisywanie.visibility = View.INVISIBLE
                }
            }
    }

    private fun changePassword(credential: AuthCredential, newPassword: String, tx: TextView){
        dane.currentUser?.reauthenticate(credential)
            ?.addOnCompleteListener {
                if(it.isSuccessful){
                    dane.currentUser?.updatePassword(newPassword)
                        ?.addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                tx.text = ""
                                tx.visibility = View.GONE
                                finishIFShould()
                            }else{
                                tx.text = "Wymyśl inne hasło "
                                tx.visibility = View.VISIBLE
                                PB7.visibility = View.INVISIBLE
                                tx7Zapisywanie.visibility = View.INVISIBLE
                            }
                        }
                } else {
                    tx.text = "Błędne hasło, spróbuj ponownie"
                    tx.visibility = View.VISIBLE
                    PB7.visibility = View.INVISIBLE
                    tx7Zapisywanie.visibility = View.INVISIBLE
                }
            }
    }
    
    private fun finishIFShould(){

        wykonano++
        if(wykonano==5){

            var powrot = Intent(applicationContext, Profil::class.java)
            powrot.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(powrot)
            this@EditProfil.finish()
        }

    }
}








package com.example.neighborhoodwork.support

import android.content.Context
import com.example.neighborhoodwork.Activities.EditProfil
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase



 public class fireBaseConnection () {

    var bazaUzytkownik = FirebaseAuth.getInstance()
    var uzytkownik = bazaUzytkownik.currentUser

      public  fun ustawEmail(newemail : String, context: Context){

        val user = FirebaseAuth.getInstance().currentUser

        val credential = EmailAuthProvider.getCredential("user@example.com", "password1234")


        //user!!.reauthenticate(credential).addOnCompleteListener {
            user!!.updateEmail("$newemail")




       // }
           /* .addOnCompleteListener(object : OnCompleteListener<Void?>() {
                fun onComplete(task: Task<Void?>) {
                    Log.d(TAG, "User re-authenticated.")
                    //Now change your email address \\
                    //----------------Code for Changing Email Address----------\\
                    val user = FirebaseAuth.getInstance().currentUser
                    user!!.updateEmail("user@example.com")
                        .addOnCompleteListener(object : OnCompleteListener<Void?>() {
                            fun onComplete(task: Task<Void?>) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User email address updated.")
                                }
                            }
                        })
                    //----------------------------------------------------------\\
                }
            })

            */
    }

    open fun ustawTel(tel : String){
       // var elephen = PhoneAuthCredential()
        //uzytkownik!!.updatePhoneNumber(tel)
        user.tel = tel
    }

    open fun ustawOpis(opis : String){

        var bazaDane = FirebaseDatabase.getInstance()
        var link = bazaDane.getReference("Users")
        user.opis = opis

        var id = uzytkownik!!.displayName.toString()
        link.child(id).child("Data").child("opis").setValue("$opis")
    }
}



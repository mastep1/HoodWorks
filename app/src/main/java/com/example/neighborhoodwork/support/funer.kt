package com.example.neighborhoodwork.support

import android.content.Context
import android.database.CursorWindow
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.neighborhoodwork.Models.MessageModel
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.PhotosDatabase.DataEntityPhotos
import com.example.neighborhoodwork.support.PhotosDatabase.DatabasePhotos
import com.example.neighborhoodwork.support.UsersDatabase.DataEntityUsers
import com.example.neighborhoodwork.support.UsersDatabase.DatabaseUsers
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream


fun uploadProfilePicture(img: ImageView, Uname: String){

    var iii = 0
      for(i in dane.contactUsers){

          if(i.uid == Uname){
              var string = i.photoURL
              //dane.info = imageBytes
              var imageBytes = string.toByteArray()

              var objectBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

              img.setImageBitmap(objectBitmap)
          }
          iii++
      }
}

fun uploadPhotoToFireStore(photo: ByteArray, Uname: String, context: Context, bt: Button) {
    var imageRef = FirebaseStorage.getInstance().reference.child("usersAvatars").child(Uname)
    imageRef.putBytes(photo).addOnSuccessListener { taskSnapshot ->

        imageRef.downloadUrl.addOnCompleteListener { taskSnapshot ->
            dane.photoURL = taskSnapshot.result.toString()
            bt.isClickable = true
        }
    }
}

fun uploadPhotoToSQL(photo: ByteArray, Uname: String, context: Context){
    var db = DatabasePhotos.getInstance(context)

    val sc = GlobalScope.launch {
        db.daoPhotos().insertOrUpdate(DataEntityPhotos(Uname, photo))
    }
    runBlocking {
       sc.join()
    }

}

fun downloadSBsProfil(Uname: String, context: Context){

    var fireuserBase = FirebaseDatabase.getInstance()
    var userDataPath = fireuserBase.getReference("Users").child(Uname).child("Data")
    userDataPath.addValueEventListener(object : ValueEventListener {

        override fun onDataChange(p0: DataSnapshot) {

            userDataPath.removeEventListener(this)

            var newData = DataEntityUsers(
                "", "", 0.0, 0, 0, 0, 0,
                "", 0.0, 0.0, 0, "", 1
            )

            for (i in p0.children) {

                when (i.key) {

                    "description" -> newData.description = i.value.toString()
                    "dislikes" -> newData.dislikes = i.value.toString().toInt()
                    "likes" -> newData.likes = i.value.toString().toInt()
                    "daysWithApp" -> newData.daysWithApp = i.value.toString().toInt()
                    "completed" -> newData.completed = i.value.toString().toInt()
                    "rating" -> newData.rating = i.value.toString().toDouble()
                    "homeAdress" -> newData.homeAdress = i.value.toString()
                    "homeX" -> newData.homeX = i.value.toString().toDouble()
                    "homeY" -> newData.homeY = i.value.toString().toDouble()
                    "homeType" -> newData.HomeType = i.value.toString().toInt()
                    "uid" -> newData.uid = i.value.toString()
                    "photo" -> {
                        newData.photoURL = i.value.toString()
                    }
                    "version" -> newData.Version = i.value.toString().toInt()
                }
            }

            dane.contactUsers.add(newData)

            var databaseFromFirebaseToSQL = DatabaseUsers.getInstance(context)

            Toast.makeText(context, "$userDataPath", Toast.LENGTH_LONG).show()
            Log.e("Alicja", "$userDataPath")

            val globalscope = GlobalScope.launch {
                dane.info =
                    databaseFromFirebaseToSQL.usersAvatarsDao().insertOrUpdate(newData).toString()
            }
            runBlocking {
                globalscope.join()
            }
        }

        override fun onCancelled(p0: DatabaseError) {
        }
    })

}

fun compressPhotoToBytes(bitmap: Bitmap): ByteArray?{



    try{
        var photoInBytes : ByteArray? = null

        var objectByteArrayOutputStream: ByteArrayOutputStream?

        objectByteArrayOutputStream = ByteArrayOutputStream()

        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream)


        photoInBytes = objectByteArrayOutputStream.toByteArray()

        return photoInBytes
        
    }catch (e: Exception){
       Log.e("errorKrystian", e.message)

    }


    return null
}

fun addMessage(context: Context, message: String, date: String, thisUser: Boolean){

    lateinit var SQL_MESSAGE : SQL_MESSAGE

    SQL_MESSAGE = SQL_MESSAGE(context)            //// SQL
    SQL_MESSAGE.insert(
        MessageModel(
            message,
            true,
            "$date",
            dane.Contasts[dane.openConversation],
            true
        )
    )             /// SQL

    dane.messages.add(MessageModel(message, true, date, dane.Contasts[dane.openConversation], true))         /// RAM

}

fun setCurrentActivity(tx: TextView, activityName: String){
    dane.tx = tx
    dane.currentActivity =  activityName
    Log.e("FUCKINGERROR1", "${dane.tx.text},  $activityName")
}                             

fun znaczniki(googleMap: GoogleMap) {
    var i = 0
    while (i < dane.zadania.size) {

        val wspolrzedne = LatLng(dane.zadania[i].x.toDouble(), dane.zadania[i].y.toDouble())
        googleMap.addMarker(
            MarkerOptions().position(wspolrzedne)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                .snippet("${dane.zadania[i].ID}")
        )
        i++
    }


    googleMap.setOnMarkerClickListener { marker ->
        if (marker.isInfoWindowShown) {
            marker.hideInfoWindow()
        } else {
            marker.showInfoWindow()

        }
        true

    }




}

fun makeListWithMessages() : java.util.ArrayList<MessageModel> {

    var listOfMessages = arrayListOf<MessageModel>()
    var index = 0

    while(index < dane.messages.size){
        if(dane.messages[index].user==dane.Contasts[dane.openConversation]){

            Log.e("FUCKING_ERROR", "found ${listOfMessages.size + 1}")

            listOfMessages.add(dane.messages[index])
        }
        index ++
    }
    return listOfMessages
}



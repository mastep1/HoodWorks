package com.example.neighborhoodwork.support

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.neighborhoodwor.ZadanieModel
import com.example.neighborhoodwork.Activities.ChatView
import com.example.neighborhoodwork.Adapters.ChatMenagerAdapter
import com.example.neighborhoodwork.Adapters.ChatViewAdapter
import com.example.neighborhoodwork.Adapters.OnSelectConConversation
import com.example.neighborhoodwork.Adapters.OnSelectConConversationV
import com.example.neighborhoodwork.Models.MessageModel
import com.example.neighborhoodwork.support.PhotosDatabase.DataEntityPhotos
import com.example.neighborhoodwork.support.PhotosDatabase.DatabasePhotos
import com.example.neighborhoodwork.support.UsersDatabase.DataEntityUsers
import com.example.neighborhoodwork.support.UsersDatabase.DatabaseUsers
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception


class MyService : Service(), OnSelectConConversation, OnSelectConConversationV {

    lateinit var contactsBase : SQL_CONTACTS
    lateinit var messageBase : SQL_MESSAGE
    lateinit var photosDatabase : DatabasePhotos
    lateinit var databaseUserData: DatabaseUsers
    lateinit var SQL_CONTACTS_DB : SQL_CONTACTS
    lateinit var databaseFromFirebaseToSQL: DatabaseUsers


    override fun onCreate() {
        super.onCreate()

        downloadUserDataFromSQL(dane.currentUser)  // pobieranie danych użytkownika, kontaktów, wiadomośći i potem wiadomośći z firebase

        checkNewTask(dane.googleMap)
    }

    override fun onBind(intent: Intent): IBinder?{
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service destroyed by user.", Toast.LENGTH_LONG).show()
    }

    private fun downloadUserDataFromSQL(currentUser: FirebaseUser?){

        /// Kontakty
        contactsBase = SQL_CONTACTS(this)
        dane.Contasts.clear()
        contactsBase.readAllUsers()

        //// Wiadomości
        messageBase = SQL_MESSAGE(this)
        dane.messages.clear()
        messageBase.readAllMessages(this)

        ///


        if(currentUser != null){

        photosDatabase = DatabasePhotos.getInstance(this)


             try{
                 val sc = GlobalScope.launch{
                // var photos  = photosDatabase.daoPhotos().getAll()




                   // if(photos!!.isNotEmpty()){

                        var i = 0
                       // while(i != photos.size){

                         //   if(photos[i].userid == currentUser.displayName) {

                             //   dane.currentUserPhoto = photos[i].photoInBytes
                           // }else{
                             //   dane.photos.add(photos[i])
                           // }
                           // i++
                        //}
                    //}

                 }
                 runBlocking {
                     sc.join()
                 }
             } catch (e: Exception){
                 Log.e("Alicja", e.message)
             }







            databaseUserData = DatabaseUsers.getInstance(this)

            GlobalScope.launch{
                var data = databaseUserData.usersAvatarsDao().getAll()

                if(data.isNotEmpty()){
                    var i = 0
                    while(i != data.size){
                        if(data[i].uid == currentUser.displayName) {
                            dane.currentUsersDataUsers = data[i]
                        }else{
                            dane.contactUsers.add(data[i])
                        }
                        checkIfFirebaseUserDataDownloadIsNecessary(data[i])
                        i++
                    }
                }
            }
        }
    }


    private fun checkIfFirebaseUserDataDownloadIsNecessary(user: DataEntityUsers){

        val fireuserBase = FirebaseDatabase.getInstance()

        var userDataPath = fireuserBase.getReference("Users").child(user.uid).child("Data")
        var userDataVersionPath = userDataPath.child("version")

        userDataVersionPath.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(p0: DataSnapshot){
                if(p0.value != null){
                    if(user.Version.toString().toInt() == p0.value.toString().toInt()){
                    }else{
                        userDataVersionPath.removeEventListener(this)
                        downloadUserDataFromFirebase(user)
                    }
                }
                Toast.makeText(applicationContext, "${user.Version}${p0.value}", Toast.LENGTH_LONG).show()

            }

            override fun onCancelled(p0: DatabaseError){
            }
        })

        if(dane.forListeningNewMessages){
            dane.expectedMessage = dane.messages.size + 1
            downloadMessage(dane.messages.size + 1)
            dane.forListeningNewMessages = false
        }




    }

    private fun downloadUserDataFromFirebase(user: DataEntityUsers){

            lateinit var userDataPath: DatabaseReference

            val fireuserBase = FirebaseDatabase.getInstance()

            userDataPath = fireuserBase.getReference("Users").child(user.uid).child("Data")

            userDataPath.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    userDataPath.removeEventListener(this)

                    var newData = DataEntityUsers("","", 0.0, 0, 0, 0, 0,
                        "", 0.0, 0.0, 0, "", 1)

                    for (i in dataSnapshot.children) {

                        when(i.key){

                           "description" -> newData.description = i.value.toString()
                           "dislikes" ->  newData.dislikes = i.value.toString().toInt()
                           "likes" ->  newData.likes = i.value.toString().toInt()
                           "daysWithApp" -> newData.daysWithApp = i.value.toString().toInt()
                           "completed" -> newData.completed = i.value.toString().toInt()
                           "rating" -> newData.rating = i.value.toString().toDouble()
                           "homeAdress" -> newData.homeAdress = i.value.toString()
                           "homeX" -> newData.homeX = i.value.toString().toDouble()
                           "homeY" -> newData.homeY = i.value.toString().toDouble()
                           "homeType" -> newData.HomeType = i.value.toString().toInt()
                           "uid" -> newData.uid = i.value.toString()
                           "photoURL" -> {
                               Toast.makeText(applicationContext, "PHOTO", Toast.LENGTH_LONG).show()
                               newData.photoURL = i.value.toString()
                           }
                           "version" -> newData.Version = i.value.toString().toInt()
                        }
                    }

                    if(dane.currentUsersDataUsers!!.uid == newData.uid ){
                        dane.currentUsersDataUsers = newData
                    }else{
                        dane.contactUsers.add(newData)
                    }

                   checkIfFirebaseUserDataDownloadIsNecessary(newData)

                    databaseFromFirebaseToSQL = DatabaseUsers.getInstance(applicationContext)
                    val scope = GlobalScope.launch {
                        databaseFromFirebaseToSQL.usersAvatarsDao().insertOrUpdate(newData)
                    }
                    runBlocking {
                        scope.join()
                    }
                }
            })
    }

    fun downloadMessage(messageIndex : Int){

        lateinit var messaesPath: DatabaseReference
        val fireuserBase = FirebaseDatabase.getInstance()    /// Połączenie z bazą

        messaesPath = fireuserBase.getReference("Users").child(dane.currentUser.displayName.toString())
            .child("Conversation").child("$messageIndex")


        messaesPath.addValueEventListener( object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if((dataSnapshot.value != null) && (dane.expectedMessage == dataSnapshot.key.toString().toInt())){

                    messaesPath.removeEventListener(this)

                    dane.expectedMessage++

                    lateinit var SQL_MESSAGE : SQL_MESSAGE

                    downloadMessage(dataSnapshot.key.toString().toInt() + 1)


                    val element = dataSnapshot.getValue(MessageModel::class.java)
                    dane.avoid = 0
                    dane.messages.add(element!!)                                         /// RAM
                    SQL_MESSAGE = SQL_MESSAGE(this@MyService)                   /// SQL
                    SQL_MESSAGE.insert(element)                                          /// SQL

                    if(!checkDoExistConversation(element.user)){
                        addConversation(element.user)
                        //rc3.adapter = ChatMenagerAdapter(this@MyService)

                    }

                    if(dane.currentActivity=="ChatMenager"){
                        dane.recycler.adapter = ChatMenagerAdapter(this@MyService)
                    }else if(dane.currentActivity=="ChatView"){
                        dane.recycler.adapter = ChatViewAdapter(this@MyService)
                    }else if(dane.currentActivity =="Home"){
                        dane.newMessage++
                        dane.tx.text = dane.newMessage.toString()
                        dane.tx.visibility = View.VISIBLE
                    }else if(dane.currentActivity =="Profil"){
                        dane.newMessage++
                        dane.tx.text = dane.newMessage.toString()
                        dane.tx.visibility = View.VISIBLE

                    }
                }else if(dataSnapshot.value == null){

                }else if(dane.expectedMessage > dataSnapshot.key.toString().toInt()){

                    messaesPath.removeEventListener(this)

                    downloadMessage(dataSnapshot.key.toString().toInt() + 1)
                }
            }
        })
    }


    private fun checkReciveMessage(){

        lateinit var newMessaesPath: DatabaseReference
        val fireuserBase = FirebaseDatabase.getInstance()    /// Połączenie z bazą

        newMessaesPath = fireuserBase.getReference("Users").child(dane.currentUser.displayName.toString())
            .child("Conversation")


        newMessaesPath.addValueEventListener( object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (i in dataSnapshot.children) {

                    lateinit var SQL_MESSAGE : SQL_MESSAGE


                    val element = i.getValue(MessageModel::class.java)
                    dane.avoid = 0
                   dane.messages.add(element!!)                                         /// RAM
                   SQL_MESSAGE = SQL_MESSAGE(this@MyService)                   /// SQL
                   SQL_MESSAGE.insert(element)                                          /// SQL

                   var toRemove = fireuserBase.getReference("Users").child(dane.currentUser.displayName.toString()).child("Conversation").child("${i.key}")
                   toRemove.removeValue()

                   if(!checkDoExistConversation(element.user)){
                       addConversation(element.user)
                       //rc3.adapter = ChatMenagerAdapter(this@MyService)
                   }

                     if(dane.currentActivity=="ChatMenager"){
                         dane.recycler.adapter = ChatMenagerAdapter(this@MyService)
                     }else if(dane.currentActivity=="ChatView"){
                         dane.recycler.adapter = ChatViewAdapter(this@MyService)
                     }else if(dane.currentActivity =="Home"){
                         dane.newMessage++
                         dane.tx.text = dane.newMessage.toString()
                         dane.tx.visibility = View.VISIBLE
                     }else if(dane.currentActivity =="Profil"){
                         dane.newMessage++
                         dane.tx.text = dane.newMessage.toString()
                         dane.tx.visibility = View.VISIBLE
                     }
                }


            }
        })


    }

    private fun checkDoExistConversation(conversation : String) : Boolean{

        var size = dane.Contasts.size;  var i = 0;  while(i<size){
            if(dane.Contasts[i]==conversation){
                i = size
                return true
            }else{
                i++
            }
        }
        return false
    }

    private fun addConversation(newConversation : String){

        dane.Contasts.add(newConversation)          /// RAM

        SQL_CONTACTS_DB = SQL_CONTACTS(this)            //// SQL
        SQL_CONTACTS_DB.insertUser("${newConversation}")        /// SQL

        downloadSBsProfil(newConversation,  applicationContext)
        dane.newContact = ""             /// wyczyszczenie zmiennej
    }

    override fun onItemClick(selectUser: String, position : Int) {
        dane.openConversation = position
        var chatView = Intent(this, ChatView::class.java)
        startActivity(chatView)
    }

    override fun onItemClickV(selectUser: String, position: Int) {
    }

    private fun checkNewTask(googleMap: GoogleMap){

        lateinit var myRef: DatabaseReference

        val fireBase = FirebaseDatabase.getInstance()
        myRef = fireBase.getReference("Zadania")

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (i in dataSnapshot.children) {

                    val element = i.getValue(ZadanieModel::class.java)
                    dane.zadania.add(element!!)
                }
                znaczniki(googleMap)
            }
        })
    }
}


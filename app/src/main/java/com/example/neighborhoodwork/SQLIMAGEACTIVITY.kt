package com.example.neighborhoodwork

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Images.Media.getBitmap
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.neighborhoodwork.support.ForeignUserDatabaseHandler
import com.example.neighborhoodwork.support.UsersDatabase.DatabaseUsers
import com.example.neighborhoodwork.support.dane
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_s_q_l_i_m_a_g_e_a_c_t_i_v_i_t_y.*
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

open class SQLIMAGEACTIVITY : AppCompatActivity() {

    private var PICK_IMAGE_REQUEST = 100
    private var imageFilePath : Uri? =  null
    private var imageToStore : Bitmap? = null
    lateinit var objectDatabaseHandler: ForeignUserDatabaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_q_l_i_m_a_g_e_a_c_t_i_v_i_t_y)

        TestImg.setOnClickListener {
            chooseImage(TestImg)
        }

        TestBtnSave.setOnClickListener {
                storageImage()

        }

        TestBtnSecound.setOnClickListener {
            Toast.makeText(this, dane.info, Toast.LENGTH_LONG).show()
        }

        TestGetAll.setOnClickListener {
            val local = DatabaseUsers.getInstance(this)
            var tyt = GlobalScope.launch{
                var files = local.usersAvatarsDao().getAll()

                if(files.size != 0){
                    
                        var nameOfImage = files[files.size - 1].uid
                        //var imageBytes = files[files.size - 1].photo

                       // var objectBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                        //TestEtx.setText("nameOfImage")

                        //TestImg.setImageBitmap(objectBitmap)
                    dane.info = nameOfImage
                   // dane.photo =  objectBitmap
                    }else{
                    dane.info = "no values in database"
                }
            }
            
        }

        solo.setOnClickListener {
            TestEtx.setText(dane.info)
            TestImg.setImageBitmap(dane.photo)
        }



    }

    fun chooseImage(objeccView: View){

        try{
            var objectIntent = Intent()
            objectIntent.setType("image/*")

            objectIntent.setAction(Intent.ACTION_GET_CONTENT)

           startActivityForResult(objectIntent, PICK_IMAGE_REQUEST)

        }catch (e: Exception){
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
        }
    }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         try{
             super.onActivityResult(requestCode, resultCode, data)
             if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null){

                imageFilePath = data.data
               getBitmap(contentResolver, imageFilePath)

                 val source = imageFilePath?.let { ImageDecoder.createSource(this.contentResolver, it) }
                 val bitmap = source?.let { ImageDecoder.decodeBitmap(it) }

                 imageToStore = bitmap

                TestImg.setImageBitmap(bitmap)
             }
         }catch (e: Exception){
             Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
         }
    }


    fun storageImage(){

         if (TestEtx.text.toString() == "" || imageToStore == null){
             Toast.makeText(applicationContext, "Please write description or imageTOStore is null", Toast.LENGTH_LONG).show()

         }else{

             try{


                 lateinit var userDataPath: DatabaseReference

                 val fireuserBase = FirebaseDatabase.getInstance()

                 userDataPath = fireuserBase.getReference("Users2")



                 val charset: Charset = Charsets.UTF_8

                 var objectByteArrayOutputStream = ByteArrayOutputStream()

                 imageToStore!!.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream)


                  var po = objectByteArrayOutputStream.toByteArray().contentToString()

                 userDataPath.setValue(po)

                 Log.e("Alicja", po)
                  //var string = String(po, charset)

                 Toast.makeText(applicationContext, " ||| ${po.length}", Toast.LENGTH_LONG).show()

                 var PiS = po.toByteArray(charset)
                
                 
                 var objectBitmap17 = BitmapFactory.decodeByteArray(PiS, 0, PiS.size)

                TestImg.setImageBitmap(objectBitmap17)


                 //var dataToPush = DataEntityUsersAvatars(TestEtx.text.toString(), imagesInBytes)
                // Toast.makeText(applicationContext, "$dataToPush,", Toast.LENGTH_LONG).show()


                 val local =  DatabaseUsers.getInstance(this)
                 //GlobalScope.launch{
                     //local.usersAvatarsDao().insertOrUpdate(dataToPush)
               //  }
             }catch(e : java.lang.Exception){
                 Log.e("Alicja", e.message)
                 Toast.makeText(applicationContext,  e.message, Toast.LENGTH_LONG).show()
             }


         }


    }


}


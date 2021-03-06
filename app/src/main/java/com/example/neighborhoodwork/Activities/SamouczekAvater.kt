package com.example.neighborhoodwork.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.neighborhoodwork.Models.MessageModel
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.samouczekavatar.*
import java.io.File


class SamouczekAvater : Activity() {




    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.samouczekavatar)

        buttonLoadPicture!!.setOnClickListener { openGallery() }

        button3.setOnClickListener {
        }

        button2.setOnClickListener {

            dane.photoInBytes = compressPhotoToBytes(bitmap!!)

            var intent = Intent(applicationContext, Samouczek::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun openGallery() {
        try{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICK_IMAGE)
        }catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.data

            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)

            val source = imageUri?.let { ImageDecoder.createSource(this.contentResolver, it)}
            bitmap = source?.let { ImageDecoder.decodeBitmap(it) }

            
            imageView!!.setImageBitmap(bitmap)
        }
    }

    companion object {
        private const val PICK_IMAGE = 100
        var imageUri: Uri? = null
        var bitmap : Bitmap? = null
    }
}
   
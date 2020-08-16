package com.example.neighborhoodwork.Activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.neighborhoodwork.Models.MessageModel
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.SQL_CONTACTS
import com.example.neighborhoodwork.support.SQL_USER
import com.example.neighborhoodwork.support.dane
import com.example.neighborhoodwork.support.user
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.samouczekavatar.*
import java.io.File


class SamouczekAvater : Activity() {


    var imageUri: Uri? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.samouczekavatar)

        lateinit var  mStorageRef : StorageReference

        mStorageRef = FirebaseStorage.getInstance().getReference("Images")

        var storagePath = mStorageRef.child("${System.currentTimeMillis()}")

        buttonLoadPicture!!.setOnClickListener { openGallery() }

        button2.setOnClickListener {
            lateinit var bazaDanych : SQL_USER

            bazaDanych = SQL_USER(this)            //// SQL
                                                            /// SQL

                val image_path: StorageReference = storagePath
                val uploadTask = image_path.putFile(imageUri!!)
                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        throw task.exception!!
                    }
                    image_path.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUrl = task.result
                        user.avatarPhotoURL = downloadUrl.toString()
                        bazaDanych.insert(downloadUrl.toString())
                    }
                }



            var intent = Intent(applicationContext, Samouczek::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun openGallery() {
        val gallery =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, SamouczekAvater.Companion.PICK_IMAGE)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == SamouczekAvater.Companion.PICK_IMAGE) {
            imageUri = data.data
            imageView!!.setImageURI(imageUri)
            dane.UriBuffer = data.data!!
        }
    }

    companion object {
        private const val PICK_IMAGE = 100
    }
}
   
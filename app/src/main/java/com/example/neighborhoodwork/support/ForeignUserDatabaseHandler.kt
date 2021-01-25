package com.example.neighborhoodwork.support

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import com.example.neighborhoodwork.Models.ForeignUser
import java.io.ByteArrayOutputStream

class ForeignUserDatabaseHandler(context: Context) : SQLiteOpenHelper(context,
   DATABASE_NAME, null,
   DATABASE_VERSION
){





    private var objectByteArrayOutputStream : ByteArrayOutputStream? = null

    private var imagesInBytes : ByteArray? = null




    override fun onCreate(db: SQLiteDatabase?) {
        try{
            if (db != null) {
                db.execSQL(createTableQuery)
            }

        }catch (e: Exception){
             dane.info = e.message.toString()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun storeImage(objectModelClass: ForeignUser){

        try{
            var objectSQLiteDatabase = this.writableDatabase
            var bitmap = objectModelClass.image

            objectByteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream)

            imagesInBytes = objectByteArrayOutputStream!!.toByteArray()
            var objectContentValues = ContentValues()

            objectContentValues.put("imageName", objectModelClass.imageName)
            objectContentValues.put("image", imagesInBytes)

            var checkIfQueryRuns =  objectSQLiteDatabase.insert("info", null, objectContentValues)
            
            if(checkIfQueryRuns != 0.toLong()){
                dane.info = "DATA ADDED INTO OUR TABLE"
                objectSQLiteDatabase.close()
            }else{
                dane.info = "Fails to add. O Fuck"
            }
            
        }catch (e: Exception){
            dane.info = e.message.toString()
        }
    }

    companion object {
        private var DATABASE_NAME = "foreignUserDatabaseHandler.db"

        private var DATABASE_VERSION = 1

        private var createTableQuery = "create table imageInfo (imageName TEXT" +
                ", image BLOB"
    }

    fun getAll() : ArrayList<ForeignUser>? {

        try{
            var objectSQLiteDatabase = this.readableDatabase
            var objectModelClassList = ArrayList<ForeignUser>()

            var objectCursor = objectSQLiteDatabase.rawQuery("select * from info", null)
            if(objectCursor.count != 0) {

                while(objectCursor.moveToNext()){

                    var nameOfImage = objectCursor.getString(0)
                    var imageBytes = objectCursor.getBlob(1)

                    var objectBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    objectModelClassList.add(ForeignUser(nameOfImage, objectBitmap))
                }
                return objectModelClassList
            }else{
                dane.info = "no values in database"
                return null
            }
        }catch(e : Exception){
             return null
        }

    }


}
package com.example.neighborhoodwork.support


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.view.View
import android.widget.Toast


class SQL_USER(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insert(string: String){
        var db = writableDatabase
        var values = ContentValues()
        values.put(SQL_USER_OBJECT.userSQL.IMIE, user.imie)
        values.put(SQL_USER_OBJECT.userSQL.EMAIL, user.email)
        values.put(SQL_USER_OBJECT.userSQL.TEL, user.tel)
        values.put(SQL_USER_OBJECT.userSQL.RATING, user.rating.toString())
        values.put(SQL_USER_OBJECT.userSQL.UKONCZONE, user.ukonczone.toString())
        values.put(SQL_USER_OBJECT.userSQL.DNI, user.dni.toString())
        values.put(SQL_USER_OBJECT.userSQL.LIKE, user.like.toString())
        values.put(SQL_USER_OBJECT.userSQL.DISLIKE, user.dislike.toString())
        values.put(SQL_USER_OBJECT.userSQL.OPIS, user.opis)
        values.put(SQL_USER_OBJECT.userSQL.AVATARPHOTOURL, string)

       db.insert(SQL_USER_OBJECT.userSQL.NAZWATABELI, null, values)
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(userid: String): Boolean {
        return true
    }

    fun readOut(){
        val db = writableDatabase
        var cursor: Cursor? = null
         try {
             cursor = db.rawQuery("select * from " + SQL_USER_OBJECT.userSQL.NAZWATABELI, null)
         } catch (e: SQLiteException) {
             db.execSQL(SQL_CREATE_ENTRIES)
         }
         if (cursor!!.moveToFirst()) {
             while (cursor.isAfterLast == false) {
                 user.avatarPhotoURL = cursor.getString(cursor.getColumnIndex(SQL_USER_OBJECT.userSQL.AVATARPHOTOURL))
                 cursor.moveToNext()
             }
         }
    }

    companion object {
        val DATABASE_VERSION = 215
        val DATABASE_NAME = "User.db"


        private val SQL_CREATE_ENTRIES = "CREATE TABLE " + SQL_USER_OBJECT.userSQL.NAZWATABELI + " (" +
                SQL_USER_OBJECT.userSQL.IMIE + " TEXT PRIMARY KEY," +
                SQL_USER_OBJECT.userSQL.EMAIL + " TEXT," +
                SQL_USER_OBJECT.userSQL.TEL + " TEXT," +
                SQL_USER_OBJECT.userSQL.RATING + " TEXT," +
                SQL_USER_OBJECT.userSQL.UKONCZONE + " TEXT," +
                SQL_USER_OBJECT.userSQL.DNI + " TEXT," +
                SQL_USER_OBJECT.userSQL.LIKE + " TEXT," +
                SQL_USER_OBJECT.userSQL.DISLIKE + " TEXT," +
                SQL_USER_OBJECT.userSQL.OPIS + " TEXT," +
                SQL_USER_OBJECT.userSQL.AVATARPHOTOURL + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SQL_USER_OBJECT.userSQL.NAZWATABELI
    }
}


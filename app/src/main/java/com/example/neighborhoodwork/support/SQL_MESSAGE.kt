package com.example.neighborhoodwork.support

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.neighborhoodwork.Models.MessageModel


class SQL_MESSAGE(context: Context) : SQLiteOpenHelper(context,
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
    fun insert(newMessage: MessageModel){
        var db = writableDatabase
        var values = ContentValues()
        values.put(SQL_DB_MESSAGE.Message.MESSAGE, newMessage.message)
        values.put(SQL_DB_MESSAGE.Message.THIS_USER, newMessage.thisUser)
        values.put(SQL_DB_MESSAGE.Message.TIME, newMessage.time)
        values.put(SQL_DB_MESSAGE.Message.USER, newMessage.user)
        values.put(SQL_DB_MESSAGE.Message.BRING, newMessage.bring)

        val newRowId = db.insert(SQL_DB_MESSAGE.Message.TABLE_NAME, null, values)
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(userid: String): Boolean {

        return true
    }

    fun readAllMessages(context: Context) {
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + SQL_DB_MESSAGE.Message.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }

        var text = ""
        var thisUser : Boolean = true
        var time = ""
        var user = ""
        var bring = ""
        var correctlyBring = false


        if (cursor!!.moveToFirst()){
            while (cursor.isAfterLast == false) {
                text = cursor.getString(cursor.getColumnIndex(SQL_DB_MESSAGE.Message.MESSAGE))
                thisUser = cursor.getInt(cursor.getColumnIndex(SQL_DB_MESSAGE.Message.THIS_USER)) > 0
                time = cursor.getString(cursor.getColumnIndex(SQL_DB_MESSAGE.Message.TIME))
                user = cursor.getString(cursor.getColumnIndex(SQL_DB_MESSAGE.Message.USER))
                bring = cursor.getString(cursor.getColumnIndex(SQL_DB_MESSAGE.Message.BRING))

                if(bring=="0"){
                    correctlyBring = false
                }else if(bring=="1"){
                    correctlyBring = true
                }


                dane.messages.add(MessageModel(message = text, thisUser = thisUser, time = time, user = user, bring = correctlyBring))
                cursor.moveToNext()
                dane.newMessage++
            }
        }
    }

    companion object {
        val DATABASE_VERSION = 228
        val DATABASE_NAME = "Messagecztery.db"

        private val SQL_CREATE_ENTRIES = "CREATE TABLE " + SQL_DB_MESSAGE.Message.TABLE_NAME + " (" +
                SQL_DB_MESSAGE.Message.MESSAGE + " TEXT PRIMARY KEY," +
                SQL_DB_MESSAGE.Message.THIS_USER + " TEXT," +
                SQL_DB_MESSAGE.Message.TIME + " TEXT," +
                SQL_DB_MESSAGE.Message.USER + " TEXT," +
                SQL_DB_MESSAGE.Message.BRING + " TEXT)"
        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SQL_DB_MESSAGE.Message.TABLE_NAME
    }
}
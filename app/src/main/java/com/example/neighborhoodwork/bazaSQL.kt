package com.example.neighborhoodwork

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.neighborhoodwor.Zadanie

class bazaSQL (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        //db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(zad: Zadanie): Boolean {
        //val db = writableDatabase
       // val values = ContentValues()
       // values.put(DBContract.wzorSQL.XXX, zad.x)
       // values.put(DBContract.wzorSQL.YYY, zad.y)

       // val newRowId = db.insert(DBContract.wzorSQL.XXX, null, values)
        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(userid: String): Boolean {

        val db = writableDatabase

        val selection = DBContract.wzorSQL.XXX + " LIKE ?"

        val selectionArgs = arrayOf(userid)

        db.delete(DBContract.wzorSQL.YYY, selection, selectionArgs)

        return true
    }



    fun readAllUsers(){
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.wzorSQL.YYY, null)
        } catch (e: SQLiteException) {
            db.execSQL(bazaSQL.SQL_CREATE_ENTRIES)
        }

        var pozioma : String
        var pionowa : String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                pozioma = cursor.getString(cursor.getColumnIndex(DBContract.wzorSQL.XXX))
                pionowa = cursor.getString(cursor.getColumnIndex(DBContract.wzorSQL.YYY))

                dane.zadania.add(Zadanie(pozioma, pionowa))
                cursor.moveToNext()
            }
        }
    }


    companion object {
        val DATABASE_VERSION = 3
        val DATABASE_NAME = "ZadaniaBaza.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.wzorSQL.XXX + " (" +
                    DBContract.wzorSQL.YYY + " TEXT PRIMARY KEY,)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.wzorSQL.YYY
    }
}

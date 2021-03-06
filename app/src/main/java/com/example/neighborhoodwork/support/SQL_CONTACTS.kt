package com.example.neighborhoodwork.support

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper


class SQL_CONTACTS(context: Context) : SQLiteOpenHelper(context,
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
    fun insertUser(newContact: String){
        val db = writableDatabase
        val values = ContentValues()
        values.put(SQL_BD_CONTACT.Contacts.CONTACT_ROW, newContact)

        val newRowId = db.insert(SQL_BD_CONTACT.Contacts.TABLE_NAME, null, values)
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(userid: String): Boolean {

        val db = writableDatabase

        val selection = SQL_BD_CONTACT.Contacts.CONTACT_ROW + " LIKE ?"

        val selectionArgs = arrayOf(userid)

        db.delete(SQL_BD_CONTACT.Contacts.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readAllUsers() {
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + SQL_BD_CONTACT.Contacts.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }

        var contactRow : String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                contactRow = cursor.getString(cursor.getColumnIndex(SQL_BD_CONTACT.Contacts.CONTACT_ROW))

                dane.Contasts.add(contactRow)
                cursor.moveToNext()
            }
        }

    }

    companion object {
        val DATABASE_VERSION = 252
        val DATABASE_NAME = "Contacts.db"

        private val SQL_CREATE_ENTRIES = "CREATE TABLE " + SQL_BD_CONTACT.Contacts.TABLE_NAME + " (" +
                    SQL_BD_CONTACT.Contacts.CONTACT_ROW + " TEXT PRIMARY KEY)"
        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SQL_BD_CONTACT.Contacts.TABLE_NAME
    }
}
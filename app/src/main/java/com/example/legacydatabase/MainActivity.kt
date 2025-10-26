package com.example.legacydatabase

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var db : SQLiteDatabase
    private lateinit var tvMain : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = MyDbHelper(this).writableDatabase
        tvMain = findViewById(R.id.tvMain)
    }

    fun addButtonClick(view: View) {
        val values = ContentValues().apply {
            put(Customer.COLUMN_FN, listOf("John","Sam").shuffled(). first())
            put(Customer.COLUMN_LN, listOf("Doe", "Smith").shuffled(). first())
        }
        val newCustomerId = db.insert(Customer.TABLE_NAME, null, values)

        val cursor = db.query(Customer.TABLE_NAME, null, "${BaseColumns._ID} = ?", arrayOf(newCustomerId.toString()), null, null, null)
        cursor.moveToFirst()
        val txt = """
            $newCustomerId:
            ${cursor.getString(cursor.getColumnIndexOrThrow(Customer.COLUMN_FN))}
            ${cursor.getString(cursor.getColumnIndexOrThrow(Customer.COLUMN_LN))}"""
        cursor.close()
        tvMain.text = txt
    }
}
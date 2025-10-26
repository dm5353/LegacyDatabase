package com.example.legacydatabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object Customer : BaseColumns {
    const val TABLE_NAME = "customers"
    const val COLUMN_FN = "firstName"
    const val COLUMN_LN = "lastName"
}

object Order : BaseColumns {
    const val TABLE_NAME = "orders"
    const val COLUMN_CUSTID = "custId"
    const val COLUMN_TOTAL = "total"
}

private const val SQL_CREATE2 = """
create table ${Order.TABLE_NAME}
(${BaseColumns._ID} integer primary key autoincrement,
${Order.COLUMN_CUSTID} integer,
${Order.COLUMN_TOTAL} real);"""


private const val SQL_CREATE : String = """
   create table ${Customer.TABLE_NAME}
   (${BaseColumns._ID} integer primary key autoincrement,
   ${Customer.COLUMN_FN} text,
    ${Customer.COLUMN_LN} text);"""

private const val SQL_DELETE = "drop table ${Customer.TABLE_NAME};"

private const val SQL_CREATE3 = """
CREATE INDEX order_cust_idx ON ${Order.TABLE_NAME}(${Order.COLUMN_CUSTID})"""

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE)
        db.execSQL(SQL_CREATE2)
        db.execSQL(SQL_CREATE3)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion == 1) db.execSQL(SQL_CREATE2)
        if (oldVersion == 2) db.execSQL(SQL_CREATE3)
    }

    companion object {
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "my_database.db"
    }
}
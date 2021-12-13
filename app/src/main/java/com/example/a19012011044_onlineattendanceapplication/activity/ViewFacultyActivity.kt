package com.android.attendance.activity

import android.database.sqlite.SQLiteOpenHelper
import com.android.attendance.db.DBAdapter
import android.database.sqlite.SQLiteDatabase
import com.android.attendance.bean.FacultyBean
import com.android.attendance.bean.StudentBean
import com.android.attendance.bean.AttendanceSessionBean
import com.android.attendance.bean.AttendanceBean
import android.app.Application
import android.app.Activity
import android.os.Bundle
import com.example.androidattendancesystem.R
import android.content.Intent
import com.android.attendance.activity.LoginActivity
import com.android.attendance.activity.AddStudentActivity
import com.android.attendance.activity.AddFacultyActivity
import com.android.attendance.activity.ViewFacultyActivity
import com.android.attendance.activity.ViewStudentActivity
import com.android.attendance.activity.MainActivity
import com.android.attendance.context.ApplicationContext
import com.android.attendance.activity.ViewAttendancePerStudentActivity
import android.widget.AdapterView.OnItemSelectedListener
import android.text.TextUtils
import com.android.attendance.activity.MenuActivity
import com.android.attendance.activity.AddAttandanceSessionActivity
import android.widget.AdapterView.OnItemLongClickListener
import android.app.AlertDialog
import android.content.DialogInterface
import com.android.attendance.activity.ViewStudentByBranchYear
import android.widget.AdapterView.OnItemClickListener
import com.android.attendance.activity.AddAttendanceActivity
import com.android.attendance.activity.ViewAttendanceByFacultyActivity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import java.util.ArrayList

class ViewFacultyActivity : Activity() {
    var facultyBeanList: ArrayList<FacultyBean?>? = null
    private var listView: ListView? = null
    private var listAdapter: ArrayAdapter<String>? = null
    var dbAdapter = DBAdapter(this)
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.__listview_main)
        listView = findViewById<View>(R.id.listview) as ListView
        val facultyList = ArrayList<String>()
        dbAdapter.allFaculty.also { facultyBeanList }
        for (facultyBean in facultyBeanList!!) {
            val users = """ FirstName: ${facultyBean?.faculty_firstname}
Lastname:${facultyBean?.faculty_lastname}"""
            facultyList.add(users)
            Log.d("users: ", users)
        }
        listAdapter = ArrayAdapter(this, R.layout.view_faculty_list, R.id.labelF, facultyList)
        listView!!.adapter = listAdapter
        listView!!.onItemLongClickListener = OnItemLongClickListener { arg0, arg1, position, arg3 ->
            val alertDialogBuilder = AlertDialog.Builder(this@ViewFacultyActivity)
            alertDialogBuilder.setTitle(title.toString() + "decision")
            alertDialogBuilder.setMessage("Are you sure?")
            alertDialogBuilder.setPositiveButton("Yes") { dialog, id ->
                facultyList.removeAt(position)
                listAdapter!!.notifyDataSetChanged()
                listAdapter!!.notifyDataSetInvalidated()
                facultyBeanList!![position]?.let { dbAdapter.deleteFaculty(it.faculty_id) }
                dbAdapter.allFaculty.also { facultyBeanList }
                for (facultyBean in facultyBeanList!!) {
                    val users = """ FirstName: ${facultyBean?.faculty_id}
Lastname:${facultyBean?.faculty_id}"""
                    facultyList.add(users)
                    Log.d("users: ", users)
                }
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, id -> // cancel the alert box and put a Toast to the user
                dialog.cancel()
                Toast.makeText(
                    applicationContext, "You choose cancel",
                    Toast.LENGTH_LONG
                ).show()
            }
            val alertDialog = alertDialogBuilder.create()
            // show alert
            alertDialog.show()
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
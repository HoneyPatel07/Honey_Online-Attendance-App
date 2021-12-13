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
import android.view.Menu
import android.view.View
import android.widget.*

class MenuActivity : Activity() {
    var addStudent: Button? = null
    var addFaculty: Button? = null
    var viewStudent: Button? = null
    var viewFaculty: Button? = null
    var logout: Button? = null
    var attendancePerStudent: Button? = null
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        addStudent = findViewById<View>(R.id.buttonaddstudent) as Button
        addFaculty = findViewById<View>(R.id.buttonaddfaculty) as Button
        viewStudent = findViewById<View>(R.id.buttonViewstudent) as Button
        viewFaculty = findViewById<View>(R.id.buttonviewfaculty) as Button
        logout = findViewById<View>(R.id.buttonlogout) as Button
        addStudent!!.setOnClickListener { // TODO Auto-generated method stub
            val intent = Intent(this@MenuActivity, AddStudentActivity::class.java)
            startActivity(intent)
        }
        addFaculty!!.setOnClickListener { // TODO Auto-generated method stub
            val intent = Intent(this@MenuActivity, AddFacultyActivity::class.java)
            startActivity(intent)
        }
        viewFaculty!!.setOnClickListener { // TODO Auto-generated method stub
            val intent = Intent(this@MenuActivity, ViewFacultyActivity::class.java)
            startActivity(intent)
        }
        viewStudent!!.setOnClickListener { // TODO Auto-generated method stub
            val intent = Intent(this@MenuActivity, ViewStudentActivity::class.java)
            startActivity(intent)
        }
        logout!!.setOnClickListener { // TODO Auto-generated method stub
            val intent = Intent(this@MenuActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        attendancePerStudent = findViewById<View>(R.id.attendancePerStudentButton) as Button
        attendancePerStudent!!.setOnClickListener {
            val dbAdapter = DBAdapter(this@MenuActivity)
            val attendanceBeanList = dbAdapter.allAttendanceByStudent
            attendanceBeanList.also { (this@MenuActivity.applicationContext as ApplicationContext).attendanceBeanList }
            val intent = Intent(this@MenuActivity, ViewAttendancePerStudentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
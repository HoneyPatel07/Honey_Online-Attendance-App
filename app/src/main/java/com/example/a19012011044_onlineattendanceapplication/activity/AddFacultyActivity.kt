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

class AddFacultyActivity : Activity() {
    var registerButton: Button? = null
    var textFirstName: EditText? = null
    var textLastName: EditText? = null
    var textemail: EditText? = null
    var textcontact: EditText? = null
    var textaddress: EditText? = null
    var textusername: EditText? = null
    var textpassword: EditText? = null
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addfaculty)
        textFirstName = findViewById<View>(R.id.editTextFirstName) as EditText
        textLastName = findViewById<View>(R.id.editTextLastName) as EditText
        textcontact = findViewById<View>(R.id.editTextPhone) as EditText
        textaddress = findViewById<View>(R.id.editTextaddr) as EditText
        textusername = findViewById<View>(R.id.editTextUserName) as EditText
        textpassword = findViewById<View>(R.id.editTextPassword) as EditText
        registerButton = findViewById<View>(R.id.RegisterButton) as Button
        registerButton!!.setOnClickListener { // TODO Auto-generated method stub
            val first_name = textFirstName!!.text.toString()
            val last_name = textLastName!!.text.toString()
            val phone_no = textcontact!!.text.toString()
            val address = textaddress!!.text.toString()
            val userName = textusername!!.text.toString()
            val passWord = textpassword!!.text.toString()
            if (TextUtils.isEmpty(first_name)) {
                textFirstName!!.error = "please enter firstname"
            } else if (TextUtils.isEmpty(last_name)) {
                textLastName!!.error = "please enter lastname"
            } else if (TextUtils.isEmpty(phone_no)) {
                textcontact!!.error = "please enter phoneno"
            } else if (TextUtils.isEmpty(address)) {
                textaddress!!.error = "enter address"
            } else if (TextUtils.isEmpty(userName)) {
                textcontact!!.error = "please enter username"
            } else if (TextUtils.isEmpty(passWord)) {
                textaddress!!.error = "enter password"
            } else {
                val facultyBean = FacultyBean()
                facultyBean.faculty_firstname = first_name
                facultyBean.faculty_lastname = last_name
                facultyBean.faculty_mobilenumber = phone_no
                facultyBean.faculty_address = address
                facultyBean.faculty_username = userName
                facultyBean.faculty_password = passWord
                val dbAdapter = DBAdapter(this@AddFacultyActivity)
                dbAdapter.addFaculty(facultyBean)
                val intent = Intent(this@AddFacultyActivity, MenuActivity::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext, "Faculty added successfully", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
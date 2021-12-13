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
import android.graphics.Color
import android.view.Menu
import android.view.View
import android.widget.*

class LoginActivity : Activity() {
    var login: Button? = null
    var username: EditText? = null
    var password: EditText? = null
    var spinnerloginas: Spinner? = null
    var userrole: String? = null
    private val userRoleString = arrayOf("admin", "faculty")
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        login = findViewById<View>(R.id.buttonlogin) as Button
        username = findViewById<View>(R.id.editTextusername) as EditText
        password = findViewById<View>(R.id.editTextpassword) as EditText
        spinnerloginas = findViewById<View>(R.id.spinnerloginas) as Spinner
        spinnerloginas!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>, view: View,
                arg2: Int, arg3: Long
            ) {
                // TODO Auto-generated method stub
                (arg0.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                userrole = spinnerloginas!!.selectedItem as String
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }
        val adapter_role = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, userRoleString
        )
        adapter_role
            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerloginas!!.adapter = adapter_role
        login!!.setOnClickListener { // TODO Auto-generated method stub
            if (userrole == "admin") {
                val user_name = username!!.text.toString()
                val pass_word = password!!.text.toString()
                if (TextUtils.isEmpty(user_name)) {
                    username!!.error = "Invalid User Name"
                } else if (TextUtils.isEmpty(pass_word)) {
                    password!!.error = "enter password"
                } else {
                    if ((user_name == "admin") and (pass_word == "admin123")) {
                        val intent = Intent(this@LoginActivity, MenuActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                val user_name = username!!.text.toString()
                val pass_word = password!!.text.toString()
                if (TextUtils.isEmpty(user_name)) {
                    username!!.error = "Invalid User Name"
                } else if (TextUtils.isEmpty(pass_word)) {
                    password!!.error = "enter password"
                }
                val dbAdapter = DBAdapter(this@LoginActivity)
                val facultyBean = dbAdapter.validateFaculty(user_name, pass_word)
                if (facultyBean != null) {
                    val intent =
                        Intent(this@LoginActivity, AddAttandanceSessionActivity::class.java)
                    startActivity(intent)
                    (this@LoginActivity.applicationContext as ApplicationContext).facultyBean =
                        facultyBean
                    Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
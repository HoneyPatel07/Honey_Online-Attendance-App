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
import android.widget.EditText
import android.widget.Spinner
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.AdapterView
import android.widget.TextView
import android.widget.ArrayAdapter
import android.text.TextUtils
import com.android.attendance.activity.MenuActivity
import android.widget.Toast
import com.android.attendance.activity.AddAttandanceSessionActivity
import android.widget.AdapterView.OnItemLongClickListener
import android.app.AlertDialog
import android.content.DialogInterface
import com.android.attendance.activity.ViewStudentByBranchYear
import android.widget.AdapterView.OnItemClickListener
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.ImageButton
import com.android.attendance.activity.AddAttendanceActivity
import com.android.attendance.activity.ViewAttendanceByFacultyActivity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Color
import android.view.Menu
import android.view.View
import android.widget.DatePicker

class ViewAttandanceActivity : Activity() {
    var spinnerbranch: Spinner? = null
    var spinneryear: Spinner? = null
    var userrole: String? = null
    var branch: String? = null
    var year: String? = null
    private val branchString = arrayOf("cse")
    private val yearString = arrayOf("SE", "TE", "BE")
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewstudent)
        spinnerbranch = findViewById<View>(R.id.spinnerbranchView) as Spinner
        spinneryear = findViewById<View>(R.id.spinneryearView) as Spinner
        spinnerbranch!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>, view: View,
                arg2: Int, arg3: Long
            ) {
                // TODO Auto-generated method stub
                (arg0.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                branch = spinnerbranch!!.selectedItem as String
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }
        val adapter_branch = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, branchString
        )
        adapter_branch
            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerbranch!!.adapter = adapter_branch

        ///......................spinner2
        spinneryear!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>, view: View,
                arg2: Int, arg3: Long
            ) {
                // TODO Auto-generated method stub
                (arg0.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                year = spinneryear!!.selectedItem as String
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }
        val adapter_year = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, yearString
        )
        adapter_year
            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinneryear!!.adapter = adapter_year
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
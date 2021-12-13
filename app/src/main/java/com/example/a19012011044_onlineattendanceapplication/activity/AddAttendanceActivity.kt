package com.android.attendance.activity

import android.app.*
import android.database.sqlite.SQLiteOpenHelper
import com.android.attendance.db.DBAdapter
import android.database.sqlite.SQLiteDatabase
import com.android.attendance.bean.FacultyBean
import com.android.attendance.bean.StudentBean
import com.android.attendance.bean.AttendanceSessionBean
import com.android.attendance.bean.AttendanceBean
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
import android.content.DialogInterface
import com.android.attendance.activity.ViewStudentByBranchYear
import android.widget.AdapterView.OnItemClickListener
import com.android.attendance.activity.AddAttendanceActivity
import com.android.attendance.activity.ViewAttendanceByFacultyActivity
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Color
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.Window
import android.widget.*
import java.util.ArrayList

class AddAttendanceActivity : Activity() {
    var studentBeanList: ArrayList<StudentBean?>? = null
    private var listView: ListView? = null
    private var listAdapter: ArrayAdapter<String>? = null
    var sessionId = 0
    var status = "P"
    var attendanceSubmit: Button? = null
    var dbAdapter = DBAdapter(this)
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.__listview_main)
        sessionId = intent.extras.getInt("sessionId")
        listView = findViewById<View>(R.id.listview) as ListView
        val studentList = ArrayList<String>()
        studentBeanList =
            (this@AddAttendanceActivity.applicationContext as ApplicationContext).studentBeanList
        for (studentBean in studentBeanList!!) {
            val users = studentBean?.student_id
            studentList.add(users.toString())
            Log.d("users: ", users.toString())
        }
        listAdapter = ArrayAdapter(this, R.layout.add_student_attendance, R.id.labelA, studentList)
        listView!!.adapter = listAdapter
        listView!!.onItemClickListener = OnItemClickListener { arg0, arg1, arg2, arg3 ->
            arg0.getChildAt(arg2).setBackgroundColor(Color.TRANSPARENT)
            //arg0.setBackgroundColor(234567);
            arg1.setBackgroundColor(334455)
            val studentBean = studentBeanList!![arg2]
            val dialog = Dialog(this@AddAttendanceActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) //...........
            dialog.setContentView(R.layout.test_layout)
            // set title and cancelable
            val radioGroup: RadioGroup
            val present: RadioButton
            val absent: RadioButton
            radioGroup = dialog.findViewById<View>(R.id.radioGroup) as RadioGroup
            present = dialog.findViewById<View>(R.id.PresentradioButton) as RadioButton
            absent = dialog.findViewById<View>(R.id.AbsentradioButton) as RadioButton
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == R.id.PresentradioButton) {
                    status = "P"
                } else if (checkedId == R.id.AbsentradioButton) {
                    status = "A"
                } else {
                }
            }
            attendanceSubmit = dialog.findViewById<View>(R.id.attendanceSubmitButton) as Button
            attendanceSubmit!!.setOnClickListener {
                val attendanceBean = AttendanceBean()
                attendanceBean.attendance_session_id = sessionId
                if (studentBean != null) {
                    attendanceBean.attendance_student_id = studentBean.student_id
                }
                attendanceBean.attendance_status = status
                val dbAdapter = DBAdapter(this@AddAttendanceActivity)
                dbAdapter.addNewAttendance(attendanceBean)
                dialog.dismiss()
            }
            dialog.setCancelable(true)
            dialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
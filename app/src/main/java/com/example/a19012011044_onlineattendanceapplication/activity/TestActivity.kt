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
import android.view.View
import android.widget.*

class TestActivity : Activity() {
    var submit: Button? = null
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_main)
        submit = findViewById<View>(R.id.button1) as Button
        submit!!.setOnClickListener {
            val dbAdapter = DBAdapter(this@TestActivity)
            val attendanceSessionBean = AttendanceSessionBean()
            attendanceSessionBean.attendance_session_faculty_id = 1
            attendanceSessionBean.attendance_session_department = "CSE"
            attendanceSessionBean.attendance_session_class = "BE"
            attendanceSessionBean.attendance_session_date = "06/04/2016"
            attendanceSessionBean.attendance_session_subject = "DataBase"
            dbAdapter.addAttendanceSession(attendanceSessionBean)
            Log.d("add", "inserted")

            /*AttendanceSessionBean bean = new AttendanceSessionBean();
                    bean=dbAdapter.getAttendance();
                    Log.d("AsessionId", bean.getAttendance_session_id()+"");
                    Log.d("fId", bean.getAttendance_session_faculty_id()+"");
                    Log.d("class", bean.getAttendance_session_class());
                    Log.d("dept", bean.getAttendance_session_department());
                    Log.d("fId", bean.getAttendance_session_faculty_id()+"");
                    Log.d("status", bean.getAttendance_session_class());
                    
                    FacultyBean facultyBean = new FacultyBean();
                    
                    facultyBean.setFaculty_firstname("Abc");
                    facultyBean.setFaculty_lastname("a");
                    facultyBean.setFaculty_mobilenumber("9898989898");
                    facultyBean.setFaculty_address("pune");
                    facultyBean.setFaculty_username("a");
                    facultyBean.setFaculty_password("Abc123");
                    
                    dbAdapter.addFaculty(facultyBean);
                    
                    StudentBean studentBean = new StudentBean();
                    
                    studentBean.setStudent_firstname("b");
                    studentBean.setStudent_lastname("b");
                    studentBean.setStudent_mobilenumber("8989898988");
                    studentBean.setStudent_address("pune");
                    studentBean.setStudent_department("CSE");
                    studentBean.setStudent_class("BE");
                    
                    dbAdapter.addStudent(studentBean);*/
            val attendanceSessionBeanList = dbAdapter.allAttendanceSession
            for (sessionBean in attendanceSessionBeanList!!) {
                Log.d("for", "in for loop")
                val aid = sessionBean.attendance_session_id
                val fid = sessionBean.attendance_session_faculty_id
                val sclass = sessionBean.attendance_session_class
                val dept = sessionBean.attendance_session_department
                val date = sessionBean.attendance_session_date
                val sub = sessionBean.attendance_session_subject
                Log.d("id", aid.toString() + "")
                Log.d("fid", fid.toString() + "")
                Log.d("sclass", sclass)
                Log.d("dept", dept)
                Log.d("date", date)
                Log.d("sub", sub)
            }
        }
    }
}
package com.android.attendance.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.android.attendance.bean.AttendanceBean
import com.android.attendance.bean.AttendanceSessionBean
import com.android.attendance.bean.StudentBean
import com.android.attendance.context.ApplicationContext
import com.android.attendance.db.DBAdapter
import com.example.androidattendancesystem.R
import java.util.*

class AddAttandanceSessionActivity<AddAttandanceActivity> : Activity() {
    private var date: ImageButton? = null
    private var cal: Calendar? = null
    private var day = 0
    private var month = 0
    private var dyear = 0
    private var dateEditText: EditText? = null
    var submit: Button? = null
    var viewAttendance: Button? = null
    var viewTotalAttendance: Button? = null
    var spinnerbranch: Spinner? = null
    var spinneryear: Spinner? = null
    var spinnerSubject: Spinner? = null
    var branch = "cse"
    var year = "SE"
    var subject = "SC"
    private val branchString = arrayOf("cse")
    private val yearString = arrayOf("SE", "TE", "BE")
    private val subjectSEString = arrayOf("SC", "MC")
    private val subjectTEString = arrayOf("GT", "CN")
    private val subjectBEString = arrayOf("DS", "NS")
    private val subjectFinal = arrayOf("M3", "DS", "M4", "CN", "M5", "NS")
    var attendanceSessionBean: AttendanceSessionBean? = null
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_attandance)

        //Assume subject will be SE
        //subjectFinal = subjectSEString;
        spinnerbranch = findViewById<View>(R.id.spinner1) as Spinner
        spinneryear = findViewById<View>(R.id.spinneryear) as Spinner
        spinnerSubject = findViewById<View>(R.id.spinnerSE) as Spinner
        val adapter_branch = ArrayAdapter(this, android.R.layout.simple_spinner_item, branchString)
        adapter_branch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerbranch!!.adapter = adapter_branch
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

        ///......................spinner2
        val adapter_year = ArrayAdapter(this, android.R.layout.simple_spinner_item, yearString)
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinneryear!!.adapter = adapter_year
        spinneryear!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>, view: View,
                arg2: Int, arg3: Long
            ) {
                // TODO Auto-generated method stub
                (arg0.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                year = spinneryear!!.selectedItem as String
                Toast.makeText(applicationContext, "year:$year", Toast.LENGTH_SHORT).show()

                /*if(year.equalsIgnoreCase("se"))
                    {
                        subjectFinal = subjectSEString;
                    }
                    else if(year.equalsIgnoreCase("te"))
                    {
                        subjectFinal = subjectTEString;
                    }
                    else if(year.equalsIgnoreCase("be"))
                    {
                        subjectFinal = subjectBEString;
                    }*/
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }
        val adapter_subject = ArrayAdapter(this, android.R.layout.simple_spinner_item, subjectFinal)
        adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSubject!!.adapter = adapter_subject
        spinnerSubject!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>, view: View,
                arg2: Int, arg3: Long
            ) {
                // TODO Auto-generated method stub
                (arg0.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                subject = spinnerSubject!!.selectedItem as String
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }
        date = findViewById<View>(R.id.DateImageButton) as ImageButton
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH).also { day = it }
        month = Calendar.getInstance().get(Calendar.MONTH)
        Calendar.getInstance().get(Calendar.YEAR).also { dyear = it }
        dateEditText = findViewById<View>(R.id.DateEditText) as EditText
        date!!.setOnClickListener { showDialog(0) }
        submit = findViewById<View>(R.id.buttonsubmit) as Button
        submit!!.setOnClickListener {
            val attendanceSessionBean = AttendanceSessionBean()
            val bean =
                (this@AddAttandanceSessionActivity.applicationContext as ApplicationContext).facultyBean
            attendanceSessionBean.attendance_session_faculty_id = bean!!.faculty_id
            attendanceSessionBean.attendance_session_department = branch
            attendanceSessionBean.attendance_session_class = year
            attendanceSessionBean.attendance_session_date = dateEditText!!.text.toString()
            attendanceSessionBean.attendance_session_subject = subject
            val dbAdapter = DBAdapter(this@AddAttandanceSessionActivity)
            val sessionId = dbAdapter.addAttendanceSession(attendanceSessionBean)
            val studentBeanList: ArrayList<StudentBean> =
                dbAdapter.getAllStudentByBranchYear(branch, year)
            studentBeanList.also { (this@AddAttandanceSessionActivity.applicationContext as ApplicationContext).studentBeanList}
            val intent = Intent(
                this@AddAttandanceSessionActivity,
                AddAttendanceActivity::class.java
            )
            intent.putExtra("sessionId", sessionId)
            startActivity(intent)
        }
        viewAttendance = findViewById<View>(R.id.viewAttendancebutton) as Button
        viewAttendance!!.setOnClickListener {
            val attendanceSessionBean = AttendanceSessionBean()
            val bean =
                (this@AddAttandanceSessionActivity.applicationContext as ApplicationContext).facultyBean
            attendanceSessionBean.attendance_session_faculty_id = bean!!.faculty_id
            attendanceSessionBean.attendance_session_department = branch
            attendanceSessionBean.attendance_session_class = year
            attendanceSessionBean.attendance_session_date = dateEditText!!.text.toString()
            attendanceSessionBean.attendance_session_subject = subject
            val dbAdapter = DBAdapter(this@AddAttandanceSessionActivity)
            val attendanceBeanList: ArrayList<AttendanceBean> =
                dbAdapter.getAttendanceBySessionID(attendanceSessionBean)
            attendanceBeanList.also { (this@AddAttandanceSessionActivity.applicationContext as ApplicationContext).attendanceBeanList}
            val intent = Intent(
                this@AddAttandanceSessionActivity,
                ViewAttendanceByFacultyActivity::class.java
            )
            startActivity(intent)
        }
        viewTotalAttendance = findViewById<View>(R.id.viewTotalAttendanceButton) as Button
        viewTotalAttendance!!.setOnClickListener {
            val attendanceSessionBean = AttendanceSessionBean()
            val bean =
                (this@AddAttandanceSessionActivity.applicationContext as ApplicationContext).facultyBean
            attendanceSessionBean.attendance_session_faculty_id = bean!!.faculty_id
            attendanceSessionBean.attendance_session_department = branch
            attendanceSessionBean.attendance_session_class = year
            attendanceSessionBean.attendance_session_subject = subject
            val dbAdapter = DBAdapter(this@AddAttandanceSessionActivity)
            val attendanceBeanList: ArrayList<AttendanceBean> =
                dbAdapter.getTotalAttendanceBySessionID(attendanceSessionBean)
            attendanceBeanList.also { (this@AddAttandanceSessionActivity.applicationContext as ApplicationContext).attendanceBeanList}
            val intent = Intent(
                this@AddAttandanceSessionActivity,
                ViewAttendanceByFacultyActivity::class.java
            )
            startActivity(intent)
        }
    }

    @Deprecated("")
    override fun onCreateDialog(id: Int): Dialog {
        return DatePickerDialog(this, datePickerListener, dyear, month, day)
    }

    private val datePickerListener =
        OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            dateEditText!!.setText(
                selectedDay.toString() + " / " + (selectedMonth + 1) + " / "
                        + selectedYear
            )
        }
}
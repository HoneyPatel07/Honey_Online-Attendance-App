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

class ViewAttendanceByFacultyActivity : Activity() {
    var attendanceBeanList: ArrayList<AttendanceBean?>? = null
    private var listView: ListView? = null
    private var listAdapter: ArrayAdapter<String?>? = null
    var dbAdapter = DBAdapter(this)
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.__listview_main)
        listView = findViewById<View>(R.id.listview) as ListView
        val attendanceList = ArrayList<String?>()
        attendanceList.add("Id | StudentName |  Status")
        attendanceBeanList =
            (this@ViewAttendanceByFacultyActivity.applicationContext as ApplicationContext).attendanceBeanList
        for (attendanceBean in attendanceBeanList!!) {
            var users: String? = ""
            if (attendanceBean != null) {
                users = if (attendanceBean.attendance_session_id != 0) {
                    val dbAdapter = DBAdapter(this@ViewAttendanceByFacultyActivity)
                    val studentBean =
                        dbAdapter.getStudentById(attendanceBean.attendance_session_id)
                    attendanceBean.attendance_session_id
                        .toString() + ".     " + studentBean.student_firstname + "," + studentBean.student_lastname + "                  " + attendanceBean.attendance_status
                } else ({
                    attendanceBean.attendance_session_id
                }).toString()
            }
            attendanceList.add(users)
            Log.d("users: ", users)
        }
        listAdapter =
            ArrayAdapter(this, R.layout.view_attendance_list, R.id.labelAttendance, attendanceList)
        listView!!.adapter = listAdapter

        /*listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {



				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewAttendanceByFacultyActivity.this);

				alertDialogBuilder.setTitle(getTitle()+"decision");
				alertDialogBuilder.setMessage("Are you sure?");

				alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {

						facultyList.remove(position);
						listAdapter.notifyDataSetChanged();
						listAdapter.notifyDataSetInvalidated();   

						dbAdapter.deleteFaculty(facultyBeanList.get(position).getFaculty_id());
						facultyBeanList=dbAdapter.getAllFaculty();

						for(FacultyBean facultyBean : facultyBeanList)
						{
							String users = " FirstName: " + facultyBean.getFaculty_firstname()+"\nLastname:"+facultyBean.getFaculty_lastname();
							facultyList.add(users);
							Log.d("users: ", users); 

						}
						
					}
					
				});
				alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// cancel the alert box and put a Toast to the user
						dialog.cancel();
						Toast.makeText(getApplicationContext(), "You choose cancel", 
								Toast.LENGTH_LONG).show();
					}
				});

				AlertDialog alertDialog = alertDialogBuilder.create();
				// show alert
				alertDialog.show();





				return false;
			}
		});
*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
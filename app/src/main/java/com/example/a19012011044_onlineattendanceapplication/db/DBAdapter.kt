package com.android.attendance.db

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
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import java.lang.Exception
import java.util.ArrayList

class DBAdapter(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val queryFaculty = "CREATE TABLE " + FACULTY_INFO_TABLE + " (" +
                KEY_FACULTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_FACULTY_FIRSTNAME + " TEXT, " +
                KEY_FACULTY_LASTNAME + " TEXT, " +
                KEY_FACULTY_MO_NO + " TEXT, " +
                KEY_FACULTY_ADDRESS + " TEXT," +
                KEY_FACULTY_USERNAME + " TEXT," +
                KEY_FACULTY_PASSWORD + " TEXT " + ")"
        Log.d("queryFaculty", queryFaculty)
        val queryStudent = "CREATE TABLE " + STUDENT_INFO_TABLE + " (" +
                KEY_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_STUDENT_FIRSTNAME + " TEXT, " +
                KEY_STUDENT_LASTNAME + " TEXT, " +
                KEY_STUDENT_MO_NO + " TEXT, " +
                KEY_STUDENT_ADDRESS + " TEXT," +
                KEY_STUDENT_DEPARTMENT + " TEXT," +
                KEY_STUDENT_CLASS + " TEXT " + ")"
        Log.d("queryStudent", queryStudent)
        val queryAttendanceSession = "CREATE TABLE " + ATTENDANCE_SESSION_TABLE + " (" +
                KEY_ATTENDANCE_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ATTENDANCE_SESSION_FACULTY_ID + " INTEGER, " +
                KEY_ATTENDANCE_SESSION_DEPARTMENT + " TEXT, " +
                KEY_ATTENDANCE_SESSION_CLASS + " TEXT, " +
                KEY_ATTENDANCE_SESSION_DATE + " DATE," +
                KEY_ATTENDANCE_SESSION_SUBJECT + " TEXT" + ")"
        Log.d("queryAttendanceSession", queryAttendanceSession)
        val queryAttendance = "CREATE TABLE " + ATTENDANCE_TABLE + " (" +
                KEY_SESSION_ID + " INTEGER, " +
                KEY_ATTENDANCE_STUDENT_ID + " INTEGER, " +
                KEY_ATTENDANCE_STATUS + " TEXT " + ")"
        Log.d("queryAttendance", queryAttendance)
        try {
            db.execSQL(queryFaculty)
            db.execSQL(queryStudent)
            db.execSQL(queryAttendanceSession)
            db.execSQL(queryAttendance)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Exception", e.message)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, arg1: Int, arg2: Int) {
        val queryFaculty = "CREATE TABLE " + FACULTY_INFO_TABLE + " (" +
                KEY_FACULTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_FACULTY_FIRSTNAME + " TEXT, " +
                KEY_FACULTY_LASTNAME + " TEXT, " +
                KEY_FACULTY_MO_NO + " TEXT, " +
                KEY_FACULTY_ADDRESS + " TEXT," +
                KEY_FACULTY_USERNAME + " TEXT," +
                KEY_FACULTY_PASSWORD + " TEXT " + ")"
        Log.d("queryFaculty", queryFaculty)
        val queryStudent = "CREATE TABLE " + STUDENT_INFO_TABLE + " (" +
                KEY_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_STUDENT_FIRSTNAME + " TEXT, " +
                KEY_STUDENT_LASTNAME + " TEXT, " +
                KEY_STUDENT_MO_NO + " TEXT, " +
                KEY_STUDENT_ADDRESS + " TEXT," +
                KEY_STUDENT_DEPARTMENT + " TEXT," +
                KEY_STUDENT_CLASS + " TEXT " + ")"
        Log.d("queryStudent", queryStudent)
        val queryAttendanceSession = "CREATE TABLE " + ATTENDANCE_SESSION_TABLE + " (" +
                KEY_ATTENDANCE_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ATTENDANCE_SESSION_FACULTY_ID + " INTEGER, " +
                KEY_ATTENDANCE_SESSION_DEPARTMENT + " TEXT, " +
                KEY_ATTENDANCE_SESSION_CLASS + " TEXT, " +
                KEY_ATTENDANCE_SESSION_DATE + " TEXT," +
                KEY_ATTENDANCE_SESSION_SUBJECT + " TEXT" + ")"
        Log.d("queryAttendanceSession", queryAttendanceSession)
        val queryAttendance = "CREATE TABLE " + ATTENDANCE_TABLE + " (" +
                KEY_SESSION_ID + " INTEGER, " +
                KEY_ATTENDANCE_STUDENT_ID + " INTEGER, " +
                KEY_ATTENDANCE_STATUS + " TEXT " + ")"
        Log.d("queryAttendance", queryAttendance)
        try {
            db.execSQL(queryFaculty)
            db.execSQL(queryStudent)
            db.execSQL(queryAttendanceSession)
            db.execSQL(queryAttendance)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Exception", e.message)
        }
    }

    //facult crud
    fun addFaculty(facultyBean: FacultyBean) {
        val db = this.writableDatabase
        val query =
            "INSERT INTO faculty_table (faculty_firstname,faculty_Lastname,faculty_mobilenumber,faculty_address,faculty_username,faculty_password) values ('" +
                    facultyBean.faculty_firstname + "', '" +
                    facultyBean.faculty_lastname + "', '" +
                    facultyBean.faculty_mobilenumber + "', '" +
                    facultyBean.faculty_address + "', '" +
                    facultyBean.faculty_username + "', '" +
                    facultyBean.faculty_password + "')"
        Log.d("query", query)
        db.execSQL(query)
        db.close()
    }

    fun validateFaculty(userName: String, password: String): FacultyBean? {
        val db = this.writableDatabase
        val query =
            "SELECT * FROM faculty_table where faculty_username='$userName' and faculty_password='$password'"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            val facultyBean = FacultyBean()
            facultyBean.faculty_id = cursor.getString(0).toInt()
            facultyBean.faculty_firstname = cursor.getString(1)
            facultyBean.faculty_lastname = cursor.getString(2)
            facultyBean.faculty_mobilenumber = cursor.getString(3)
            facultyBean.faculty_address = cursor.getString(4)
            facultyBean.faculty_username = cursor.getString(5)
            facultyBean.faculty_password = cursor.getString(6)
            return facultyBean
        }
        return null
    }

    val allFaculty: ArrayList<FacultyBean>
        get() {
            Log.d("in get all", "in get all")
            val list = ArrayList<FacultyBean>()
            val db = this.writableDatabase
            val query = "SELECT * FROM faculty_table"
            val cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    val facultyBean = FacultyBean()
                    facultyBean.faculty_id = cursor.getString(0).toInt()
                    facultyBean.faculty_firstname = cursor.getString(1)
                    facultyBean.faculty_lastname = cursor.getString(2)
                    facultyBean.faculty_mobilenumber = cursor.getString(3)
                    facultyBean.faculty_address = cursor.getString(4)
                    facultyBean.faculty_username = cursor.getString(5)
                    facultyBean.faculty_password = cursor.getString(6)
                    list.add(facultyBean)
                } while (cursor.moveToNext())
            }
            return list
        }

    fun deleteFaculty(facultyId: Int) {
        val db = this.writableDatabase
        val query = "DELETE FROM faculty_table WHERE faculty_id=$facultyId"
        Log.d("query", query)
        db.execSQL(query)
        db.close()
    }

    //student crud
    fun addStudent(studentBean: StudentBean) {
        val db = this.writableDatabase
        val query =
            "INSERT INTO student_table (student_firstname,student_lastname,student_mobilenumber,student_address,student_department,student_class) values ('" +
                    studentBean.student_firstname + "', '" +
                    studentBean.student_lastname + "','" +
                    studentBean.student_mobilenumber + "', '" +
                    studentBean.student_address + "', '" +
                    studentBean.student_department + "', '" +
                    studentBean.student_class + "')"
        Log.d("query", query)
        db.execSQL(query)
        db.close()
    }

    val allStudent: ArrayList<StudentBean>
        get() {
            val list = ArrayList<StudentBean>()
            val db = this.writableDatabase
            val query = "SELECT * FROM student_table"
            val cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    val studentBean = StudentBean()
                    studentBean.student_id = cursor.getString(0).toInt()
                    studentBean.student_firstname = cursor.getString(1)
                    studentBean.student_lastname = cursor.getString(2)
                    studentBean.student_mobilenumber = cursor.getString(3)
                    studentBean.student_address = cursor.getString(4)
                    studentBean.student_department = cursor.getString(5)
                    studentBean.student_class = cursor.getString(6)
                    list.add(studentBean)
                } while (cursor.moveToNext())
            }
            return list
        }

    fun getAllStudentByBranchYear(branch: String?, year: String?): ArrayList<StudentBean> {
        val list = ArrayList<StudentBean>()
        val db = this.writableDatabase
        val query =
            "SELECT * FROM student_table where student_department='$branch' and student_class='$year'"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val studentBean = StudentBean()
                studentBean.student_id = cursor.getString(0).toInt()
                studentBean.student_firstname = cursor.getString(1)
                studentBean.student_lastname = cursor.getString(2)
                studentBean.student_mobilenumber = cursor.getString(3)
                studentBean.student_address = cursor.getString(4)
                studentBean.student_department = cursor.getString(5)
                studentBean.student_class = cursor.getString(6)
                list.add(studentBean)
            } while (cursor.moveToNext())
        }
        return list
    }

    fun getStudentById(studentId: Int): StudentBean {
        val studentBean = StudentBean()
        val db = this.writableDatabase
        val query = "SELECT * FROM student_table where student_id=$studentId"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                studentBean.student_id = cursor.getString(0).toInt()
                studentBean.student_firstname = cursor.getString(1)
                studentBean.student_lastname = cursor.getString(2)
                studentBean.student_mobilenumber = cursor.getString(3)
                studentBean.student_address = cursor.getString(4)
                studentBean.student_department = cursor.getString(5)
                studentBean.student_class = cursor.getString(6)
            } while (cursor.moveToNext())
        }
        return studentBean
    }

    fun deleteStudent(studentId: Int) {
        val db = this.writableDatabase
        val query = "DELETE FROM student_table WHERE student_id=$studentId"
        Log.d("query", query)
        db.execSQL(query)
        db.close()
    }

    //attendance session Table crud
    fun addAttendanceSession(attendanceSessionBean: AttendanceSessionBean): Int {
        val db = this.writableDatabase
        val query =
            "INSERT INTO attendance_session_table (attendance_session_faculty_id,attendance_session_department,attendance_session_class,attendance_session_date,attendance_session_subject) values ('" +
                    attendanceSessionBean.attendance_session_faculty_id + "', '" +
                    attendanceSessionBean.attendance_session_department + "','" +
                    attendanceSessionBean.attendance_session_class + "', '" +
                    attendanceSessionBean.attendance_session_date + "', '" +
                    attendanceSessionBean.attendance_session_subject + "')"
        Log.d("query", query)
        db.execSQL(query)
        val query1 = "select max(attendance_session_id) from attendance_session_table"
        val cursor = db.rawQuery(query1, null)
        if (cursor.moveToFirst()) {
            return cursor.getString(0).toInt()
        }
        db.close()
        return 0
    }

    val allAttendanceSession: ArrayList<AttendanceSessionBean>
        get() {
            val list = ArrayList<AttendanceSessionBean>()
            val db = this.writableDatabase
            val query = "SELECT * FROM attendance_session_table"
            val cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    val attendanceSessionBean = AttendanceSessionBean()
                    attendanceSessionBean.attendance_session_id = cursor.getString(0).toInt()
                    attendanceSessionBean.attendance_session_faculty_id =
                        cursor.getString(1).toInt()
                    attendanceSessionBean.attendance_session_department = cursor.getString(2)
                    attendanceSessionBean.attendance_session_class = cursor.getString(3)
                    attendanceSessionBean.attendance_session_date = cursor.getString(4)
                    attendanceSessionBean.attendance_session_subject = cursor.getString(5)
                    list.add(attendanceSessionBean)
                } while (cursor.moveToNext())
            }
            return list
        }

    fun deleteAttendanceSession(attendanceSessionId: Int) {
        val db = this.writableDatabase
        val query =
            "DELETE FROM attendance_session_table WHERE attendance_session_id=$attendanceSessionId"
        Log.d("query", query)
        db.execSQL(query)
        db.close()
    }

    //attendance crud
    fun addNewAttendance(attendanceBean: AttendanceBean) {
        val db = this.writableDatabase
        val query = "INSERT INTO attendance_table values (" +
                attendanceBean.attendance_session_id + ", " +
                attendanceBean.attendance_student_id + ", '" +
                attendanceBean.attendance_status + "')"
        Log.d("query", query)
        db.execSQL(query)
        db.close()
    }

    fun getAttendanceBySessionID(attendanceSessionBean: AttendanceSessionBean): ArrayList<AttendanceBean> {
        var attendanceSessionId = 0
        val list = ArrayList<AttendanceBean>()
        val db = this.writableDatabase
        val query =
            ("SELECT * FROM attendance_session_table where attendance_session_faculty_id=" + attendanceSessionBean.attendance_session_faculty_id + ""
                    + " AND attendance_session_department='" + attendanceSessionBean.attendance_session_department + "' AND attendance_session_class='" + attendanceSessionBean.attendance_session_class + "'" +
                    " AND attendance_session_date='" + attendanceSessionBean.attendance_session_date + "' AND attendance_session_subject='" + attendanceSessionBean.attendance_session_subject + "'")
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                attendanceSessionId = cursor.getString(0).toInt()
            } while (cursor.moveToNext())
        }
        val query1 =
            "SELECT * FROM attendance_table where attendance_session_id=$attendanceSessionId order by attendance_student_id"
        val cursor1 = db.rawQuery(query1, null)
        if (cursor1.moveToFirst()) {
            do {
                val attendanceBean = AttendanceBean()
                attendanceBean.attendance_session_id = cursor1.getString(0).toInt()
                attendanceBean.attendance_student_id = cursor1.getString(1).toInt()
                attendanceBean.attendance_status = cursor1.getString(2)
                list.add(attendanceBean)
            } while (cursor1.moveToNext())
        }
        return list
    }

    fun getTotalAttendanceBySessionID(attendanceSessionBean: AttendanceSessionBean): ArrayList<AttendanceBean> {
        var attendanceSessionId = 0
        val list = ArrayList<AttendanceBean>()
        val db = this.writableDatabase
        val query =
            ("SELECT * FROM attendance_session_table where attendance_session_faculty_id=" + attendanceSessionBean.attendance_session_faculty_id + ""
                    + " AND attendance_session_department='" + attendanceSessionBean.attendance_session_department + "' AND attendance_session_class='" + attendanceSessionBean.attendance_session_class + "'" +
                    " AND attendance_session_subject='" + attendanceSessionBean.attendance_session_subject + "'")
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                attendanceSessionId = cursor.getString(0).toInt()
                val query1 =
                    "SELECT * FROM attendance_table where attendance_session_id=$attendanceSessionId order by attendance_student_id"
                val cursor1 = db.rawQuery(query1, null)
                if (cursor1.moveToFirst()) {
                    do {
                        val attendanceBean = AttendanceBean()
                        attendanceBean.attendance_session_id = cursor1.getString(0).toInt()
                        attendanceBean.attendance_student_id = cursor1.getString(1).toInt()
                        attendanceBean.attendance_status = cursor1.getString(2)
                        list.add(attendanceBean)
                    } while (cursor1.moveToNext())
                }
                val attendanceBean = AttendanceBean()
                attendanceBean.attendance_session_id = 0
                attendanceBean.attendance_status = "Date : " + cursor.getString(4)
                list.add(attendanceBean)
            } while (cursor.moveToNext())
        }
        return list
    }

    /*public ArrayList<AttendanceBean> getAllAttendanceBySessionID(int sessionId)
	{
		ArrayList<AttendanceBean> list = new ArrayList<AttendanceBean>();

		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT * FROM attendance_table where attendance_session_id=" + sessionId;
		Cursor cursor = db.rawQuery(query, null);

		if(!cursor.moveToFirst()) 
		{
			do{
				AttendanceBean attendanceBean = new AttendanceBean();
				attendanceBean.setAttendance_session_id(Integer.parseInt(cursor.getString(0)));
				attendanceBean.setAttendance_student_id(Integer.parseInt(cursor.getString(1)));
				attendanceBean.setAttendance_status(cursor.getString(2));
				list.add(attendanceBean);

			}while(cursor.moveToNext());
		}
		return list;
	}*/
    val allAttendanceByStudent: ArrayList<AttendanceBean>
        get() {
            val list = ArrayList<AttendanceBean>()
            val db = this.writableDatabase
            val query =
                "SELECT attendance_student_id,count(*) FROM attendance_table where attendance_status='P' group by attendance_student_id"
            Log.d("query", query)
            val cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    Log.d(
                        "studentId",
                        "studentId:" + cursor.getString(0) + ", Count:" + cursor.getString(1)
                    )
                    val attendanceBean = AttendanceBean()
                    attendanceBean.attendance_student_id = cursor.getString(0).toInt()
                    attendanceBean.attendance_session_id = cursor.getString(1).toInt()
                    list.add(attendanceBean)
                } while (cursor.moveToNext())
            }
            return list
        }
    // Creating Tables
    /*@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_User_Info_TABLE = "CREATE TABLE " + TABLE_INFO_USER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY, " + KEY_FIRSTNAME + " TEXT, "+ KEY_LASTNAME + " TEXT, " +KEY_MO_NO +" TEXT, "
				+KEY_EMAIL +" TEXT, " +KEY_USERNAME +" TEXT, " + KEY_PASSWORD +" TEXT " + ")";

		Log.d("rupali",CREATE_User_Info_TABLE );
		db.execSQL(CREATE_User_Info_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO_USER);

		// Create tables again
		onCreate(db);
	}

	 */
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    /*



	void addUserInfo(UserInfo userinfo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FIRSTNAME, userinfo.getUser_Firstname()); //  Name
		values.put(KEY_LASTNAME, userinfo.getUser_Lastname()); //  Name
		values.put(KEY_MO_NO, userinfo.getUser_MobileNo()); // Contact Phone
		values.put(KEY_EMAIL, userinfo.getUser_EmailId());
		values.put(KEY_USERNAME, userinfo.getUser_Username());
		values.put(KEY_PASSWORD, userinfo.getUser_Password());

		// Inserting Row
		db.insert(TABLE_INFO_USER, null, values);
		//2nd argument is String containing nullColumnHack
		db.close(); // Closing database connection
	}


	// Getting single contact
	UserInfo getUserInfo(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_INFO_USER, new String[] { KEY_ID,
				KEY_FIRSTNAME, KEY_LASTNAME,KEY_MO_NO,  KEY_EMAIL, KEY_USERNAME, KEY_PASSWORD }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		UserInfo userinfo = new UserInfo(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6));
		// return contact
				return userinfo;
	}

	public UserInfo validateUser(String username, String password)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "Select * from User_Info_Table WHERE User_Username='"+ username +"' AND User_Password='"+password+"'";
		Log.d("Rupali", "Login QUERY:" + query);

		Cursor cursor = db.rawQuery(query, null);


		if(!cursor.moveToFirst()) 
		{
			Log.d("Rupali", "cursor is null.. returing NULL");
			return null;
		}
		Log.d("Rupali", "cursor is NOT null.. we got user data...");


		UserInfo userinfo = new UserInfo(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6));

		return userinfo;
	}

	// Updating single contact
	public int updateUserPassword(UserInfo userinfo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PASSWORD, userinfo.getUser_Password());


		// updating row
		return db.update(TABLE_INFO_USER, values, KEY_ID + " = ?",
				new String[] { String.valueOf(userinfo.getUser_id()) });
	}

	public int updateUserContact(UserInfo userinfo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_MO_NO, userinfo.getUser_MobileNo());
		values.put(KEY_EMAIL, userinfo.getUser_EmailId());


		// updating row
		return db.update(TABLE_INFO_USER, values, KEY_ID + " = ?",
				new String[] { String.valueOf(userinfo.getUser_id()) });
	}


	//veiw details

	public UserInfo viewUserInfo(String id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String query = "Select * from User_Info_Table WHERE id='"+id+"'";
		Cursor cursor = db.rawQuery(query, null);
		if(!cursor.moveToFirst()) 
		{
			Log.d("Rupali", "cursor is null.. returing NULL");
			return null;
		}
		Log.d("Rupali", "cursor is NOT null.. we got user data...");

		UserInfo userinfo = new UserInfo(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6));
		// return contact
		return userinfo;
	}



	 // Getting All users
    public List<UserInfo> getAllUserInfo() {
        List<UserInfo> userinfolist = new ArrayList<UserInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INFO_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                UserInfo userinfo=new UserInfo();

                userinfo.setUser_id(Integer.parseInt(cursor.getString(0)));
                userinfo.setUser_Lastname(cursor.getString(2));
                userinfo.setUser_Username(cursor.getString(5));
                userinfo.setUser_Firstname(cursor.getString(1));



                // Adding contact to list
                userinfolist.add(userinfo);
            } while (cursor.moveToNext());
        }

        // return contact list
        return userinfolist;
    }

    // Deleting single contact
    public void deleteUser(UserInfo userinfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INFO_USER, KEY_ID + " = ?",
                new String[] { String.valueOf(userinfo.getUser_id()) });
        db.close();
    }
	  */
    companion object {
        // All Static variables
        // Database Version
        private const val DATABASE_VERSION = 1

        // Database Name
        private const val DATABASE_NAME = "Attendance"

        // Contacts table name
        private const val FACULTY_INFO_TABLE = "faculty_table"
        private const val STUDENT_INFO_TABLE = "student_table"
        private const val ATTENDANCE_SESSION_TABLE = "attendance_session_table"
        private const val ATTENDANCE_TABLE = "attendance_table"

        // Contacts Table Columns names
        private const val KEY_FACULTY_ID = "faculty_id"
        private const val KEY_FACULTY_FIRSTNAME = "faculty_firstname"
        private const val KEY_FACULTY_LASTNAME = "faculty_Lastname"
        private const val KEY_FACULTY_MO_NO = "faculty_mobilenumber"
        private const val KEY_FACULTY_ADDRESS = "faculty_address"
        private const val KEY_FACULTY_USERNAME = "faculty_username"
        private const val KEY_FACULTY_PASSWORD = "faculty_password"
        private const val KEY_STUDENT_ID = "student_id"
        private const val KEY_STUDENT_FIRSTNAME = "student_firstname"
        private const val KEY_STUDENT_LASTNAME = "student_lastname"
        private const val KEY_STUDENT_MO_NO = "student_mobilenumber"
        private const val KEY_STUDENT_ADDRESS = "student_address"
        private const val KEY_STUDENT_DEPARTMENT = "student_department"
        private const val KEY_STUDENT_CLASS = "student_class"
        private const val KEY_ATTENDANCE_SESSION_ID = "attendance_session_id"
        private const val KEY_ATTENDANCE_SESSION_FACULTY_ID = "attendance_session_faculty_id"
        private const val KEY_ATTENDANCE_SESSION_DEPARTMENT = "attendance_session_department"
        private const val KEY_ATTENDANCE_SESSION_CLASS = "attendance_session_class"
        private const val KEY_ATTENDANCE_SESSION_DATE = "attendance_session_date"
        private const val KEY_ATTENDANCE_SESSION_SUBJECT = "attendance_session_subject"
        private const val KEY_SESSION_ID = "attendance_session_id"
        private const val KEY_ATTENDANCE_STUDENT_ID = "attendance_student_id"
        private const val KEY_ATTENDANCE_STATUS = "attendance_status"
    }
}
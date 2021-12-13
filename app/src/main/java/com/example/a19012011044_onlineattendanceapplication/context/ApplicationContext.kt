package com.android.attendance.context

import android.app.Application
import com.android.attendance.bean.AttendanceBean
import com.android.attendance.bean.AttendanceSessionBean
import com.android.attendance.bean.FacultyBean
import com.android.attendance.bean.StudentBean
import java.util.*

class ApplicationContext : Application() {
    var facultyBean: FacultyBean? = null
    var attendanceSessionBean: AttendanceSessionBean? = null
    var studentBeanList: ArrayList<StudentBean?>? = null
    var attendanceBeanList: ArrayList<AttendanceBean?>? = null
}
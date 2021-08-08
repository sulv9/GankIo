package com.aefottt.gankio

import android.content.Context
import android.widget.Toast

class BugTest {
    fun bug(context: Context){
        Toast.makeText(context, "1234567", Toast.LENGTH_SHORT).show()
    }
}
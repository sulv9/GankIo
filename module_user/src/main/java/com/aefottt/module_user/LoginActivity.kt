package com.aefottt.module_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

@Route(path="/user/login")
class LoginActivity : AppCompatActivity() {
    companion object{
        var isLogin = false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        isLogin = getSharedPreferences("is_login", MODE_PRIVATE)
            .getBoolean("isLogin", false)
        findViewById<Button>(R.id.btn_login).setOnClickListener{
            // 登录
            val editor = getSharedPreferences("is_login", MODE_PRIVATE).edit()
            editor.putBoolean("isLogin", true)
            editor.apply()
            // 跳转到主界面
            ARouter.getInstance().build("/main/main").navigation()
        }
    }
}
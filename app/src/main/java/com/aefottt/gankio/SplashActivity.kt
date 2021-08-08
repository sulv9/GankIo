package com.aefottt.gankio

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // 判断是否已登录
        val isLogin = getSharedPreferences("is_login", MODE_PRIVATE)
            .getBoolean("isLogin", false)
        // 判断是否需要热修复
        if (FixDexUtil.isNeedFix(this)) {
            FixDexUtil.dexInject(this) // 注入补丁
        }
        Handler().postDelayed({
            if (isLogin){
                // 跳转到主界面
                ARouter.getInstance().build("/main/main").navigation()
            }else{
                // 跳转到登录界面
                ARouter.getInstance().build("/user/login").navigation()
            }
        }, 2000)
    }
}
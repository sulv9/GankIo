package com.aefottt.gankio

import android.app.Application
import com.aefottt.module_common.AppConfig
import com.aefottt.module_common.BaseApp
import com.alibaba.android.arouter.launcher.ARouter
import java.lang.Exception

class GankApplication: BaseApp() {

    private val isDebug = false

    override fun onCreate() {
        super.onCreate()

        initModuleApp(this)

        // 初始化ARouter
        if (isDebug){
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    override fun initModuleApp(application: Application) {
        for (appPath in AppConfig.moduleApps){
            try {
                val clazz = Class.forName(appPath)
                val baseApp = clazz.newInstance() as BaseApp
                baseApp.initModuleApp(application)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}
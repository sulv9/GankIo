package com.aefottt.module_user

import android.app.Application
import com.aefottt.module_common.BaseApp
import com.aefottt.module_common.ServiceFactory
import com.aefottt.module_user.service.UserService

class LoginApp : BaseApp() {
    override fun initModuleApp(application: Application) {
        ServiceFactory.user_service = UserService()
    }
}
package com.aefottt.module_user.service

import com.aefottt.module_common.service.IUserService
import com.aefottt.module_user.LoginActivity

class UserService : IUserService {
    override fun isLogin(): Boolean {
        return LoginActivity.isLogin
    }

    override fun getUserId(): String? {
        return "这是用户信息"
    }
}
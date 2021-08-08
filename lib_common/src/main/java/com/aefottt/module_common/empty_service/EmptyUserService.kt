package com.aefottt.module_common.empty_service

import com.aefottt.module_common.service.IUserService

class EmptyUserService : IUserService {
    override fun isLogin() = false

    override fun getUserId(): String? = null
}
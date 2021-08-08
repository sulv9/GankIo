package com.aefottt.module_common.service

interface IUserService {
    fun isLogin(): Boolean
    fun getUserId(): String?
}
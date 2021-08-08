package com.aefottt.module_common.empty_service

import com.aefottt.module_common.AppConfig
import com.aefottt.module_common.service.IArticleService

class EmptyArticleService : IArticleService {
    override fun getArticleDesc(): String? {
        AppConfig.moduleApps
        return null
    }
}
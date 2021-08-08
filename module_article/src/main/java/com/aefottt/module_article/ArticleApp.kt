package com.aefottt.module_article

import android.app.Application
import com.aefottt.module_article.service.ArticleService
import com.aefottt.module_common.BaseApp
import com.aefottt.module_common.ServiceFactory

class ArticleApp : BaseApp() {
    override fun initModuleApp(application: Application) {
        ServiceFactory.article_service = ArticleService()
    }
}
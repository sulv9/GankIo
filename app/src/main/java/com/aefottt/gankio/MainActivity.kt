package com.aefottt.gankio

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aefottt.module_common.ServiceFactory
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_main.*

@Route(path="/main/main")
class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_main.text = ServiceFactory.user_service.getUserId() +
                "\n" + ServiceFactory.article_service.getArticleDesc()
        BugTest().bug(this)
    }
}
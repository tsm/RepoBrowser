package com.tomszom.repobrowser.repository

import android.os.Bundle
import android.support.design.widget.Snackbar
import com.tomszom.repobrowser.R
import com.tomszom.repobrowser.core.presentation.BaseActivity
import kotlinx.android.synthetic.main.repository_activity.*

class RepositoryActivity : BaseActivity() {

    override fun layoutId() = R.layout.repository_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "TODO add", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}

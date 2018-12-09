package com.tomszom.repobrowser.repository

import android.os.Bundle
import com.tomszom.repobrowser.R
import com.tomszom.repobrowser.core.presentation.BaseActivity

class RepositoryActivity : BaseActivity() {

    companion object {
        const val EXTRA_LOGIN = "EXTRA_LOGIN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showBackButton()
    }

    private fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun layoutId() = R.layout.repository_activity

}

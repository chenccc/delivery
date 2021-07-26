package com.james.delivery.ui.main
import android.os.Bundle
import androidx.activity.viewModels
import com.james.delivery.R
import com.james.delivery.base.BaseActivity
import com.james.delivery.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // hide action bar
        supportActionBar?.hide()
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun getVM(): MainViewModel = mainViewModel

    override fun bindVM(binding: ActivityMainBinding, vm: MainViewModel) = Unit
}
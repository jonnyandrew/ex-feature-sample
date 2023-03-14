package io.element.android.feature.poc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.element.android.feature.SampleElementFeatureProvider
import io.element.android.feature.poc.databinding.ActivityMainBinding
import io.element.modulesdk.lifecycle.LifecycleModule

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var lifecycleModule: LifecycleModule

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleModule = SampleElementFeatureProvider().lifecycleModule()
        lifecycleModule.onCreate()
    }
}

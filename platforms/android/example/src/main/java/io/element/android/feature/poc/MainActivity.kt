package io.element.android.feature.poc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.element.android.feature.SampleElementExtensionProvider
import io.element.android.feature.poc.databinding.ActivityMainBinding
import io.element.extension.lifecycle.LifecycleExtension

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var lifecycleExtension: LifecycleExtension

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleExtension = SampleElementExtensionProvider().lifecycle()
        lifecycleExtension.onCreate()
    }
}

package io.element.android.feature.poc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.element.android.feature.Feature
import io.element.android.feature.poc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text.text = Feature.module.name()
    }

}

package io.element.android.feature.poc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import io.element.android.feature.SampleElementExtensionProvider
import io.element.android.feature.poc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var extensionProvider: SampleElementExtensionProvider

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        extensionProvider = SampleElementExtensionProvider()
        extensionProvider.lifecycle().onCreate()

        setContent {
            Column {
                Text("Hello world!")
                extensionProvider.login().Branding()
            }
        }
    }
}

package io.element.android.feature.poc

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.element.android.feature.SampleElementExtensionProvider


class MainActivity : AppCompatActivity() {

    private lateinit var extensionProvider: SampleElementExtensionProvider

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        extensionProvider = SampleElementExtensionProvider()
        extensionProvider.lifecycle().onCreate()

        setContent {
            val context = LocalContext.current
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                extensionProvider.login()
                    .Banner(onInteractionComplete = {
                        onInteractionComplete(context)
                    })
            }
        }
    }

    private fun onInteractionComplete(context: Context) =
        Toast.makeText(
            context,
            "Interaction complete",
            Toast.LENGTH_SHORT
        ).show()
}

package com.mozilla.testhuaweidefaultapps

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var strategy = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chooserBtn.setOnClickListener {
            Toast.makeText(this, "Choose a different strategy to query default browser", Toast.LENGTH_LONG).show()
            showDefaultAppQueryStrategy()
        }

        queryBtn.setOnClickListener {
            text.text = "Default browser:\n${getDefault()}"
        }
    }

    private fun getDefault(): String {
        return (packageManager.resolveActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("http://petru.mugurel")),
                strategy
            )?.activityInfo?.name ?: "null").also {
            Log.e("default browser: ", it)
        }
    }

    private fun showDefaultAppQueryStrategy() {
        AlertDialog.Builder(this)
            .setTitle("Choose a strategy")
            .setItems(
                arrayOf("Current implementation", "Fixed implementation")
            ) { _: DialogInterface, buttonPosition: Int ->
                run {
                    if (buttonPosition == 0) {
                        strategy = 0
                        chooserBtn.text = getString(R.string.current_default_query)
                    } else {
                        strategy = PackageManager.MATCH_DEFAULT_ONLY
                        chooserBtn.text = getString(R.string.fixed_default_query)
                    }
                }
            }
            .show()
    }
}

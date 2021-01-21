package com.example.pomodorotimer

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class SettingsFragment(private val btn: PositiveBtn) : DialogFragment() {
    interface PositiveBtn {
        fun clicked(workTime: Int?, restTime: Int?)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity).apply {
            val content = requireActivity().layoutInflater.inflate(R.layout.activity_settings, null)
            setView(content)

            setPositiveButton(R.string.ok) { _, _ ->
                val workTime = content.findViewById<EditText>(R.id.workTime).text.toString().toIntOrNull()
                val restTime = content.findViewById<EditText>(R.id.restTime).text.toString().toIntOrNull()
                btn.clicked(workTime, restTime)
            }
            setNegativeButton(R.string.cancel) { _, _ -> dialog!!.cancel() }
        }.create()
    }
}
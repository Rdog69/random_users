package com.random.example.usergenerator.views

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.random.example.usergenerator.R
import com.random.example.usergenerator.SharedViewModel
import com.random.example.usergenerator.utils.SharedViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import kotlin.math.exp


class SettingFragment : PreferenceFragmentCompat() {
    private val viewModel: SharedViewModel by lazy {
        val factory = SharedViewModelFactory(requireActivity().application)
        ViewModelProvider(this, factory).get(SharedViewModel::class.java)
    }

    private var packageName = ""
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.setTheme(R.style.CustomPreferenceTheme)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        packageName = requireActivity().packageName
        findPreference<Preference>("feedback")?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("sigmappshelp@example.com"))
                putExtra(Intent.EXTRA_SUBJECT, "App Feedback")
            }
            startActivity(Intent.createChooser(intent, "Send Feedback"))
            true
        }
        findPreference<Preference>("privacy_policy")?.setOnPreferenceClickListener {
            val url = "https://sites.google.com/view/sigmapps"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
            true
        }
        findPreference<Preference>("rate_us")?.setOnPreferenceClickListener {
            val marketUri = Uri.parse("market://details?id=$packageName")
            context?.startActivity(Intent(Intent.ACTION_VIEW,marketUri))
            true
        }
        findPreference<Preference>("share")?.setOnPreferenceClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.share,packageName))
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            true
        }
        findPreference<Preference>("export")?.setOnPreferenceClickListener {
            lifecycleScope.launch {
                val file = exportToCsv(requireContext())
                if (file != null) {
                    Toast.makeText(context, "File downloaded to: ${file.absolutePath}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "No data available to export!", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }




    }

    suspend fun exportToCsv(context: Context): File? {
        return withContext(Dispatchers.IO) {
            val personList = viewModel.personRepository.export()
            Log.d("personList",personList.toString())
            if (personList.isNotEmpty()) {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val csvFile = File(downloadsDir, "person_data.csv")
                csvFile.bufferedWriter().use { writer ->
                    writer.write("ID,First Name,Last Name,Email,Gender,Phone,Cell,Picture URL,DOB,Age,Address,Username,Password\n")
                    personList.forEach { person ->
                        writer.write(
                            "${person.uid}," +
                                    "${person.firstName}," +
                                    "${person.lastName}," +
                                    "${person.email}," +
                                    "${person.gender}," +
                                    "${person.phone}," +
                                    "${person.cell}," +
                                    "${person.pictureUrl}," +
                                    "${person.dob}," +
                                    "${person.dobAge}," +
                                    "\"${person.address}\"," +
                                    "${person.username}," +
                                    "${person.password}\n"
                        )
                    }
                }
                csvFile
            } else {
                null
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
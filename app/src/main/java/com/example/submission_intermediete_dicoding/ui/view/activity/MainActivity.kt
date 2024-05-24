package com.example.submission_intermediete_dicoding.ui.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.example.submission_intermediete_dicoding.data.retrofit.Injection
import com.example.submission_intermediete_dicoding.databinding.ActivityMainBinding
import com.example.submission_intermediete_dicoding.ui.adapter.SectionPageAdapter
import com.example.submission_intermediete_dicoding.ui.view.activity.CameraActivity.Companion.CAMERAX_RESULT
import com.example.submission_intermediete_dicoding.ui.viewmodel.MainActivityViewModel
import com.example.submission_intermediete_dicoding.ui.viewmodel.StoryViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentImageUri: Uri? = null
    private lateinit var mainViewModel : MainActivityViewModel
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private lateinit var loginData : LoginResponse
    private var loginEmail : String? = ""
    private var storyCount : Int? = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        loginData = intent.getParcelableExtra<LoginResponse>("id_login")!!
        loginEmail = intent.getStringExtra("email")
        Log.d(TAG, "email : $loginEmail")

        Log.d(TAG, "loginData : $loginData")
        Log.d(TAG, "loginData-name : ${loginData.loginResult.name}")



        val sectionPageAdapter = SectionPageAdapter(this)
        val viewPager : ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPageAdapter
        val tabs : TabLayout = binding.tabs
        TabLayoutMediator(tabs,viewPager){tab, position -> tab.text = resources.getString(
            TAB_TITLES[position]
        )}.attach()

        supportActionBar?.elevation = 0f

        val getNameString = getString(R.string.selamat_datang, loginData.loginResult.name)
        binding.toolbar.title = getNameString
        binding.btnAddStory.setOnClickListener { startCameraX()}

        val repository = Injection.provideStoryRepository(this)
        mainViewModel = ViewModelProvider(this, StoryViewModelFactory(repository)).get(MainActivityViewModel::class.java)

        mainViewModel.storyCount.observe(this) { data ->
            storyCount = data
            val getStringCount = getString(R.string.hi_total_story_anda_14_story, storyCount)
            binding.toolbar.subtitle = getStringCount
        }

        binding.toolbar.setNavigationOnClickListener{
            val intent = Intent(this@MainActivity, SettingActivity::class.java)
            intent.putExtra("email", loginEmail)
            intent.putExtra("storyCount", storyCount)
            startActivity(intent)
        }

    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            val intent = Intent(this, AddStoryActivity::class.java)
            intent.putExtra(EXTRA_CAMERAX_IMAGE, it)
            intent.putExtra("token", loginData)
            startActivity(intent)
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        private const val TAG = "MainActivity"
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"

    }
}
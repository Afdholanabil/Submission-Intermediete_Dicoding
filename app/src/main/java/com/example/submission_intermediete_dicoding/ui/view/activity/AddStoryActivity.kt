package com.example.submission_intermediete_dicoding.ui.view.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.example.submission_intermediete_dicoding.data.retrofit.Injection
import com.example.submission_intermediete_dicoding.database.myStory.MyStory
import com.example.submission_intermediete_dicoding.databinding.ActivityAddStoryBinding
import com.example.submission_intermediete_dicoding.ui.viewmodel.AddStoryViewModel
import com.example.submission_intermediete_dicoding.ui.viewmodel.StoryViewModelFactory
import com.example.submission_intermediete_dicoding.util.helper.createCustomTempFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddStoryBinding
    private lateinit var image : Uri
    private lateinit var viewModel: AddStoryViewModel
    private lateinit var myStory: MyStory
    private lateinit var loginData : LoginResponse

    private var currentImageUri: Uri? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLatUser : Double? = null
    private var currentLonUser : Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        image = intent.getParcelableExtra<Uri>(EXTRA_CAMERAX_IMAGE)!!
        loginData = intent.getParcelableExtra<LoginResponse>("token")!!
        val token = loginData.loginResult.token
        Log.d(TAG,"Token : $token")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

        val storyRepository = Injection.provideStoryRepository(this)
        viewModel = ViewModelProvider(this, StoryViewModelFactory(storyRepository)).get(AddStoryViewModel::class.java)

        binding.imgPut.setImageURI(image)
        binding.btnGallery.setOnClickListener {
            choosePhoto()
        }

        binding.btnCamera.setOnClickListener {
         startCameraX()
        }

        binding.btnAddStory.setOnClickListener {
lifecycleScope.launch {
            addStory()

}

        }

        binding.switchAccLoc.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                getLastLocation()
                Toast.makeText(this, "Lokasi dinyalakan, cerita anda terlihat di maps!",Toast.LENGTH_SHORT).show()
            }
            else{
                currentLatUser = null
                currentLonUser = null
                Toast.makeText(this, "Lokasi dimatikan, cerita anda tidak terlihat di maps !",Toast.LENGTH_SHORT).show()
            }
        }




//        viewModel.addStoryResponse.observe(this) { response ->
//            if (response.error == false) {
//                Toast.makeText(this, "Story added successfully: ${response.message}", Toast.LENGTH_LONG).show()
//                val intent = Intent(this, MainActivity::class.java)
//                intent.putExtra("id_login",loginData)
//                startActivity(intent)
//                finish()
//            } else {
//                Toast.makeText(this, "Failed to add story: ${response.message}", Toast.LENGTH_LONG).show()
//            }
//        }
//
//        viewModel.error.observe(this) { errorMessage ->
//            Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_LONG).show()
//        }
//
//        viewModel.loading.observe(this) {
//            showLoading(it)
//        }
//
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CameraActivity.CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
           currentImageUri?.let {
               binding.imgPut.setImageURI(it)
           }
        }
    }

    private  suspend fun addStory() {
        val descriptionText = binding.etDescription.text.toString()
        val description = descriptionText.toString().toRequestBody("text/plain".toMediaType())
        val img = binding.imgPut

        val photoFile2 = convertImageViewToFile(img, "photo.jpg")
        if (descriptionText.isEmpty()) {
            Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val compressedPhotoFile = compressImageFile(photoFile2)
        val photoUri = Uri.fromFile(compressedPhotoFile)

        val requestImageFile = compressedPhotoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            compressedPhotoFile.name,
            requestImageFile
        )
            try {
                val lat = if (binding.switchAccLoc.isChecked) currentLatUser else null
                val lon = if (binding.switchAccLoc.isChecked) currentLonUser else null
                myStory = MyStory(desc = binding.etDescription.text.toString(), photoUrl = photoUri.toString(), lat = lat, lon = lon)

                viewModel.postStory(description, imageMultipart, lat, lon,myStory )
                Log.d(TAG, "nilai lat: $currentLatUser dan lon: $currentLonUser")
                binding.btnAddStory.isEnabled = false

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id_login",loginData)
                startActivity(intent)
                finish()
            } catch (e : Exception) {
                binding.btnAddStory.isEnabled = true
            }



    }

    private fun choosePhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PICK_PHOTO && resultCode == Activity.RESULT_OK && data != null) {

            val photoUri = data.data
            binding.imgPut.setImageURI(photoUri)
        }
    }

    private fun convertImageViewToFile(imageView: ImageView, fileName: String): File {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val file = File(cacheDir, fileName)
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
        return file
    }


    private fun bitmapToFile(bitmap: Bitmap): File {
        val filesDir = applicationContext.filesDir
        val photoFile = File(filesDir, "photo.jpg")

        try {
            val outputStream = FileOutputStream(photoFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return photoFile
    }

    private fun compressImageFile(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var quality = 100
        val compressedFile = createCustomTempFile(application)
        do {
            val outputStream = FileOutputStream(compressedFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
            quality -= 5
        } while (compressedFile.length() > 1_000_000 && quality > 0)

        return compressedFile
    }

    private fun showLoading(a: Boolean) {
        if (a) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                currentLatUser = it.latitude
                currentLonUser = it.longitude
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        private const val TAG = "AddStoryActivity"
        private const val REQUEST_PICK_PHOTO = 1
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1

    }
}
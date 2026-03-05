package uz.gita.exportapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uz.gita.exportapp.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlin.uuid.Uuid

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            btnExport.setOnClickListener {
                val bitmap = Bitmap.createBitmap(content.width, content.height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                content.draw(canvas)
                imgResult.setImageBitmap(bitmap)

                //val uuid = UUID.randomUUID().toString()
               // val timeName = System.currentTimeMillis().toString()
                val timeName = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())

               // val file = File(filesDir, "${timeName}.png")
                try {
                    val file = File(
                        Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES
                        ),
                        "${timeName}.png"
                    )

//                    val outputStream = FileOutputStream(file)
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//                    outputStream.flush()
//                    outputStream.close()

                    FileOutputStream(file).use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    }


                }catch (e: Exception){
                    e.printStackTrace()
                }

            }
        }
    }
}
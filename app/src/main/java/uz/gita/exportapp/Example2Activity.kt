package uz.gita.exportapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Environment
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.createBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.github.serpro69.kfaker.Faker
import uz.gita.exportapp.databinding.ActivityExample2Binding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Example2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityExample2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExample2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val faker = Faker()

        val list = ArrayList<ContactData>()
        repeat(100){
            list.add(
                ContactData(
                    name = faker.name.name(),
                    phone = faker.phoneNumber.phoneNumber(),
                    address = faker.address.fullAddress()
                )
            )
        }

        binding.btnExport.setOnClickListener {
            val paint = TextPaint().apply {
                color = Color.BLACK
                textSize = 28f
                isAntiAlias = true
            }
            val layouts = list.map { item->
                val text = "${item.name} | ${item.phone} | ${item.address}"
                StaticLayout.Builder.obtain(text,0,text.length, paint,400 )
                    .build()
            }


            val height = layouts.sumOf { it.height }
            val spacing = 20
            val bitmap = createBitmap(400,height+spacing)
            val canvas = Canvas(bitmap)

            canvas.drawColor(Color.WHITE)

            var y = 0f
            layouts.forEachIndexed {index, layout->
                canvas.save()
                canvas.translate(0f, y)
                layout.draw(canvas)
                canvas.restore()

                y += layout.height + 20
            }
            val timeName = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())

            try {
                val file = File(
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                    ),
                    "${timeName}.png"
                )

                FileOutputStream(file).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }


            }catch (e: Exception){
                e.printStackTrace()
            }
            binding.imgResult.setImageBitmap(bitmap)
        }



    }
}

private data class ContactData(
    val name: String,
    val phone: String,
    val address: String
)
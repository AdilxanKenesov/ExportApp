package uz.gita.exportapp

import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.text.StaticLayout
import android.text.TextPaint
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.github.serpro69.kfaker.Faker
import uz.gita.exportapp.databinding.ActivityExample3Binding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Example3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityExample3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExample3Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val faker = Faker()
        val list = ArrayList<ContactsData>()

        repeat(100) { index ->
            list.add(
                ContactsData(
                    id = index + 1,
                    name = faker.name.name(),
                    phone = faker.phoneNumber.phoneNumber(),
                    address = faker.address.fullAddress()
                )
            )
        }

        binding.apply {
            btnExport.setOnClickListener{
                val paint = TextPaint().apply {
                    color = Color.BLACK
                    textSize = 28f
                    isAntiAlias = true
                }
                val layouts = list.map { item->
                    val text = "${item.id} | ${item.name} | ${item.phone} | ${item.address}"
                    StaticLayout.Builder.obtain(text,0,text.length, paint,400 )
                        .build()
                }


                val height = layouts.sumOf { it.height }
                val pageHeight = 1200
                val pageWidth = 400
                val spacing = 20

                val pdfDocument = PdfDocument()

                var pageNumber = 1
                var y = 0f

                var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                var page = pdfDocument.startPage(pageInfo)
                var canvas = page.canvas

                canvas.drawColor(Color.WHITE)

                layouts.forEach { layout ->

                    if (y + layout.height > pageHeight) {

                        pdfDocument.finishPage(page)

                        pageNumber++

                        pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                        page = pdfDocument.startPage(pageInfo)
                        canvas = page.canvas
                        canvas.drawColor(Color.WHITE)

                        y = 0f
                    }

                    canvas.save()
                    canvas.translate(0f, y)
                    layout.draw(canvas)
                    canvas.restore()

                    y += layout.height + spacing
                }

                pdfDocument.finishPage(page)

                val timeName = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())

                try {
                    val file = File(
                        Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOCUMENTS
                        ),
                        "${timeName}.pdf"
                    )

                    FileOutputStream(file).use { outputStream ->
                        pdfDocument.writeTo(outputStream)
                    }
                    pdfDocument.close()

                }catch (e: Exception){
                    e.printStackTrace()
                }


            }

        }
    }
}
data class ContactsData(
    val id: Int,
    val name: String,
    val phone: String,
    val address: String
)
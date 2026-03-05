package uz.gita.exportapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uz.gita.exportapp.databinding.ActivityExample4Binding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Example4Activity : AppCompatActivity() {

    private lateinit var binding: ActivityExample4Binding
    private var lastFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExample4Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnExport.setOnClickListener {
            exportImage()
        }
        binding.btnExportPdf.setOnClickListener {
            exportPdf()
        }
        binding.btnShare.setOnClickListener {
            lastFile?.let { shareFile(it) }
        }

        binding.btnOpen.setOnClickListener {
            lastFile?.let { openFile(it) }
        }
    }

    private fun getBitmap(): Bitmap {

        val bitmap = Bitmap.createBitmap(
            binding.content.width,
            binding.content.height,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)

        binding.content.draw(canvas)

        return bitmap
    }

    private fun exportImage() {

        val bitmap = getBitmap()

        val timeName = SimpleDateFormat(
            "yyyy-MM-dd_HH-mm-ss",
            Locale.getDefault()
        ).format(Date())

        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ),
            "$timeName.png"
        )

        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }

        binding.imgResult.setImageBitmap(bitmap)

        lastFile = file
    }

    private fun exportPdf() {

        val bitmap = getBitmap()

        val pdfDocument = PdfDocument()

        val pageInfo = PdfDocument.PageInfo.Builder(
            bitmap.width,
            bitmap.height,
            1
        ).create()

        val page = pdfDocument.startPage(pageInfo)

        page.canvas.drawBitmap(bitmap, 0f, 0f, null)

        pdfDocument.finishPage(page)

        val timeName = SimpleDateFormat(
            "yyyy-MM-dd_HH-mm-ss",
            Locale.getDefault()
        ).format(Date())

        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS
            ),
            "$timeName.pdf"
        )

        FileOutputStream(file).use {
            pdfDocument.writeTo(it)
        }

        pdfDocument.close()

        lastFile = file
    }

    private fun shareFile(file: File) {

        val uri = FileProvider.getUriForFile(
            this,
            "$packageName.provider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND)

        intent.type =
            if (file.extension == "pdf") "application/pdf"
            else "image/png"

        intent.putExtra(Intent.EXTRA_STREAM, uri)

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(Intent.createChooser(intent, "Share File"))
    }

    private fun openFile(file: File) {

        val uri = FileProvider.getUriForFile(
            this,
            "$packageName.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW)

        val type =
            if (file.extension == "pdf") "application/pdf"
            else "image/png"

        intent.setDataAndType(uri, type)

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(intent)
    }
}
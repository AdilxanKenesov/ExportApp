# Android Export Examples

![Platform](https://img.shields.io/badge/platform-Android-green)
![Language](https://img.shields.io/badge/language-Kotlin-blue)
![Min SDK](https://img.shields.io/badge/minSdk-21-orange)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

Bu loyiha Android’da **View, matn va ma’lumotlarni rasm (Bitmap) yoki PDF formatiga export qilish** usullarini ko‘rsatadi.

Loyihada **Canvas, Bitmap, StaticLayout va PdfDocument** qanday ishlashini amaliy misollar orqali ko‘rish mumkin.

---

# 📱 Preview

<img width="576" height="1280" alt="image" src="https://github.com/user-attachments/assets/022810ce-7f45-453e-ab9a-607a315a4d5d" />

---

# 📂 Project Examples

Loyihada 3 ta asosiy example mavjud.

---

# Example 1 — View ni Bitmap ga aylantirish

Bu misolda Android **layout Bitmap (rasm)** ga aylantiriladi va telefon xotirasiga saqlanadi.

Foydalanuvchi **Export** tugmasini bosganda:

* Layout Bitmap ga aylantiriladi
* Natija ImageView ichida ko‘rsatiladi
* Rasm `.png` formatida **Pictures** papkasiga saqlanadi

## Qanday ishlaydi

1. Layout o‘lchamiga teng **Bitmap** yaratiladi
2. Bitmap uchun **Canvas** yaratiladi
3. View Canvas ustiga chiziladi
4. Natija **ImageView** da ko‘rsatiladi
5. Bitmap `.png` formatda telefon xotirasiga saqlanadi

## Asosiy kod

```
val bitmap = Bitmap.createBitmap(content.width, content.height, Bitmap.Config.ARGB_8888)

val canvas = Canvas(bitmap)

content.draw(canvas)

imgResult.setImageBitmap(bitmap)

val timeName = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())

val file = File(
    Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_PICTURES
    ),
    "$timeName.png"
)

FileOutputStream(file).use { outputStream ->
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
}
```

---

# Example 2 — Matnni rasmga export qilish

Bu example **tasodifiy kontaktlar yaratib**, ularni **rasm ko‘rinishida export qilishni** ko‘rsatadi.

Ilova **Kotlin Faker** yordamida:

* Ism
* Telefon
* Manzil

ma’lumotlarini generatsiya qiladi.

Keyin bu ma’lumotlar **Canvas va StaticLayout** yordamida Bitmap ichiga chiziladi.

## Screenshot

<img width="576" height="1280" alt="image" src="https://github.com/user-attachments/assets/c2299aa4-13ee-4cd6-a789-d360989bbc78" />

<img width="576" height="1280" alt="image" src="https://github.com/user-attachments/assets/00584ec5-95ba-4d0c-b816-e540126ef5f1" />

## Asosiy imkoniyatlar

* Random contact generatsiya
* Matnni Canvas orqali chizish
* StaticLayout bilan text wrapping
* PNG rasmga export
* Image preview

---

# Example 3 — Kontaktlarni PDF ga export qilish

Bu example **kontaktlar ro‘yxatini PDF hujjatga export qilishni** ko‘rsatadi.

Ilova:

* 100 ta kontakt generatsiya qiladi
* Har bir kontaktga **ID** beradi
* StaticLayout orqali matnni tayyorlaydi
* PdfDocument orqali **multi-page PDF** yaratadi

Agar sahifa to‘lib qolsa **yangi sahifa avtomatik ochiladi**.

## PDF format

```
ID | Name | Phone | Address
```

## Asosiy kod

```
val pageHeight = 1200
val pageWidth = 400

val pdfDocument = PdfDocument()

var pageNumber = 1
var y = 0f

var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()

var page = pdfDocument.startPage(pageInfo)

var canvas = page.canvas

layouts.forEach { layout ->

    if (y + layout.height > pageHeight) {

        pdfDocument.finishPage(page)

        pageNumber++

        pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()

        page = pdfDocument.startPage(pageInfo)

        canvas = page.canvas

        y = 0f
    }

    canvas.save()
    canvas.translate(0f, y)
    layout.draw(canvas)
    canvas.restore()

    y += layout.height + 20
}

pdfDocument.finishPage(page)
```

---

Example 4 — Image yoki PDF ni Export & Share qilish

Bu example Android layout (ImageView) ni PNG yoki PDF formatida export qilish, preview qilish, share qilish va ochish imkonini beradi.

Foydalanuvchi:

* Export tugmasi → Image PNG formatida Pictures papkasiga saqlanadi
* Share tugmasi → Oxirgi export qilingan faylni boshqa ilovalar bilan ulashadi
* Open File tugmasi → Oxirgi export qilingan faylni ochadi
* PDF export ham qo‘shilishi mumkin (bitmap → PDF)

  <img width="576" height="1280" alt="image" src="https://github.com/user-attachments/assets/56a7dc8f-dc29-4e45-8327-9711922ac762" />
  <img width="576" height="1280" alt="image" src="https://github.com/user-attachments/assets/e7374574-1391-46fc-8942-43553d5c97b2" />

Asosiy kod (PNG va PDF export)
```
private fun getBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(content.width, content.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    content.draw(canvas)
    return bitmap
}

private fun exportImage() {
    val bitmap = getBitmap()
    val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "${SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())}.png")
    FileOutputStream(file).use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
    imgResult.setImageBitmap(bitmap)
    lastFile = file
    Toast.makeText(this, "Image muvaffaqiyatli saqlandi!", Toast.LENGTH_SHORT).show()
}

private fun exportPdf() {
    val bitmap = getBitmap()
    val pdf = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
    val page = pdf.startPage(pageInfo)
    page.canvas.drawBitmap(bitmap, 0f, 0f, null)
    pdf.finishPage(page)
    val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
        "${SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())}.pdf")
    FileOutputStream(file).use { pdf.writeTo(it) }
    pdf.close()
    lastFile = file
    Toast.makeText(this, "PDF muvaffaqiyatli saqlandi!", Toast.LENGTH_SHORT).show()
}

private fun shareFile(file: File) {
    val uri = FileProvider.getUriForFile(this, "$packageName.provider", file)
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = if (file.extension == "pdf") "application/pdf" else "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(Intent.createChooser(intent, "Share File"))
}
```


# 🧰 Technologies

* Kotlin
* Android Canvas API
* Bitmap
* StaticLayout
* PdfDocument
* ViewBinding
* Kotlin Faker
* FileProvider

---

# 👨‍💻 Author

**Adilxan Kenesov**

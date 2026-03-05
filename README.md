Bu loyiha Android da View ni Bitmap (rasm) ko‘rinishiga aylantirish va uni telefon xotirasiga saqlashni ko‘rsatadi.

Foydalanuvchi button ni bosganda:

Layout Bitmap ga aylantiriladi

Natija ImageView da ko‘rsatiladi

Rasm .png formatida telefonning Pictures papkasiga saqlanadi

<img width="576" height="1280" alt="image" src="https://github.com/user-attachments/assets/022810ce-7f45-453e-ab9a-607a315a4d5d" />

Qanday ishlaydi

Layout o‘lchamiga teng Bitmap yaratiladi
Bitmap uchun Canvas yaratiladi
View Canvas ga chiziladi
Natija ImageView da ko‘rsatiladi
Bitmap .png formatida telefon xotirasiga saqlanadi

Asosiy kod
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

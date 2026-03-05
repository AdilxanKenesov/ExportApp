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

#Example 2
Ilova **Kotlin Faker** kutubxonasi yordamida tasodifiy kontakt ma’lumotlarini (ism, telefon raqam, manzil) generatsiya qiladi. Keyin bu ma’lumotlar **Canvas va StaticLayout** yordamida Bitmap ichiga chiziladi va rasm sifatida qurilmaga saqlanadi.

## Asosiy imkoniyatlari

* Tasodifiy kontakt ma’lumotlarini generatsiya qilish
* Matnni rasmga chizish (Canvas orqali)
* Uzun matnlarni avtomatik qatorga bo‘lish (StaticLayout)
* Natijani **PNG rasm** sifatida export qilish
* Rasmni qurilmaning **Pictures** papkasiga saqlash
* Ilova ichida rasmni preview qilish

## Ishlash prinsipi

1. Ilova **Faker** yordamida 100 ta kontakt yaratadi.

2. Har bir kontakt quyidagi formatda matnga aylantiriladi:

   Ism | Telefon | Manzil

3. **StaticLayout** uzun matnlarni avtomatik yangi qatordan chiqaradi.

4. **Canvas** barcha matnlarni bitta Bitmap ichiga chizadi.

5. Bitmap **PNG formatda** qurilmaga saqlanadi.

6. Natija ilova ichida ham ko‘rsatiladi.
<img width="576" height="1280" alt="image" src="https://github.com/user-attachments/assets/c2299aa4-13ee-4cd6-a789-d360989bbc78" />
<img width="576" height="1280" alt="image" src="https://github.com/user-attachments/assets/00584ec5-95ba-4d0c-b816-e540126ef5f1" />

## Texnologiyalar

* Kotlin
* Android Canvas API
* Bitmap
* StaticLayout
* ViewBinding
* Kotlin Faker

Export qilingan rasm ichida quyidagi ko‘rinishda kontaktlar bo‘ladi:

John Doe | +1-202-555-0132 | 742 Evergreen Terrace, Springfield


```
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
```

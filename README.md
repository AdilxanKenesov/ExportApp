Bu loyiha Android'da View ni Bitmap (rasm) ko‘rinishiga aylantirishni ko‘rsatadi.

Foydalanuvchi buttonni bosganda layout rasmga aylantiriladi va natija ImageView'da ko‘rsatiladi.

<img width="576" height="1280" alt="image" src="https://github.com/user-attachments/assets/022810ce-7f45-453e-ab9a-607a315a4d5d" /><img width="576" height="1280" alt="image" src="https://github.com/user-attachments/assets/56e66a5f-3560-4fe2-9afb-2924e3e2a36e" />

Qanday ishlaydi

1. Layout o‘lchamiga teng Bitmap yaratiladi
2. Bitmap uchun Canvas yaratiladi
3. View Canvas ga chiziladi
4. Natija ImageView da ko‘rsatiladi

Asosiy kod
```
val bitmap = Bitmap.createBitmap(content.width, content.height, Bitmap.Config.ARGB_8888)
val canvas = Canvas(bitmap)
content.draw(canvas)
imgResult.setImageBitmap(bitmap)
```

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

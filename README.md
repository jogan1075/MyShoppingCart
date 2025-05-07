# Walmart MVP App

Esta aplicación es un MVP para un emprendimiento de venta de artículos de uso diario. Permite:

- Ver productos y categorías
- Ver el detalle de un producto
- Agregar productos al carrito
- Visualizar y modificar el carrito
- Guardar localmente el contenido del carrito

## 👷️ Tecnologías y herramientas

- **Lenguaje**: Kotlin
- **UI Toolkit**: Jetpack Compose
- **Inyección de dependencias**: Hilt
- **Persistencia local**: SharedPreferences + Gson
- **Consumo de API REST**: `retrofit`
- **Carga de imágenes**: Coil
- **Arquitectura**: UDF

## 📦 API utilizada

Datos obtenidos desde [FakeStore API](https://fakestoreapi.com/).

- `/products`: Todos los productos
- `/products/categories`: Todas las categorías
- `/products/category/{categoria}`: Productos por categoría
- `/products/{id}`: Detalle de producto

## 🔄 Flujo de navegación

1. **Pantalla Home**
   - Lista de productos con un producto destacado (calculado por `rating.rate * rating.count`)
   - Acceso al menú lateral con categorías
   - Acceso al carrito

2. **Detalle del producto**
   - Imagen, título, descripción, rating, precio
   - Botón para agregar al carrito

3. **Carrito**
   - Listado de productos agregados
   - Modificación de cantidades
   - Cálculo del total a pagar

## 🧪 Cómo ejecutar el proyecto

1. Clona este repositorio:
   ```bash
   git clone https://github.com/jogan1075/MyShoppingCart
   ```
2. Ábrelo en **Android Studio**.
3. Asegúrate de tener un emulador o dispositivo físico configurado.
4. Ejecuta el proyecto (`Shift + F10` o botón de play).

## 🔒 Consideraciones

- El carrito se guarda de forma local en `SharedPreferences`, serializado como JSON.
- Las imágenes se cargan desde las URLs proporcionadas por la API usando `Coil`.
- El diseño es responsivo y adaptado con Jetpack Compose.
- La arquitectura permite extender fácilmente la app para persistencia remota o nuevos features.

## ✅ Funcionalidades cubiertas

- [x] Ver productos
- [x] Ver categorías
- [x] Ver detalle/rating del producto
- [x] Agregar productos al carro
- [x] Quitar productos del carro
- [x] Guardar el carrito de forma persistente

## 📌 Notas

- Si el API falla, los datos pueden no mostrarse correctamente. Se pueden agregar mejoras como manejo de errores o loading states.

---

👨‍💻 Desarrollado como prueba técnica para Walmart Chile.


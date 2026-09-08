# App de Recetas — Android (Kotlin)

Aplicación Android nativa desarrollada en Kotlin para gestionar recetas de cocina de forma local, con una integración adicional de clima en tiempo real.

## Funcionalidades

- **Gestión de recetas (CRUD)**: crear, ver, actualizar y eliminar recetas, almacenadas localmente en el dispositivo mediante **Room** (base de datos SQLite).
- **Clima en tiempo real**: consulta la temperatura actual mediante la API pública de **Open-Meteo**, usando **Retrofit** para las llamadas HTTP.
- **Notificaciones**: la app solicita permiso de notificaciones (compatible con Android 13+).

## Tecnologías utilizadas

| Tecnología | Uso en el proyecto |
|---|---|
| Kotlin | Lenguaje principal de desarrollo |
| Room | Persistencia local de recetas (base de datos SQLite) |
| Retrofit | Cliente HTTP para consumir la API del clima |
| Open-Meteo API | Fuente de datos del clima (no requiere API key) |
| Android Jetpack | Componentes estándar de arquitectura Android |

## Arquitectura

El proyecto sigue una separación por capas dentro del paquete `data`:

- `Receta.kt` — modelo de datos de una receta.
- `RecetaDao.kt` — operaciones de base de datos (CRUD) definidas como interfaz Room.
- `RecetaDataBase.kt` — configuración de la base de datos local.
- `RecetaRepository.kt` — capa intermedia que centraliza el acceso a los datos (tanto recetas locales como el clima externo).
- `WeatherApi.kt` / `WeatherResponse.kt` / `RetrofitClient.kt` — integración con la API de Open-Meteo.

## Permisos utilizados

- `INTERNET` — para consultar la API del clima.
- `POST_NOTIFICATIONS` — para notificaciones en Android 13 o superior.
- `VIBRATE` — para retroalimentación háptica.

## Cómo ejecutar el proyecto

1. Clona o descarga este repositorio.
2. Ábrelo con **Android Studio**.
3. Deja que Gradle sincronice las dependencias automáticamente.
4. Ejecuta la app en un emulador o dispositivo físico con Android 8.0 (API 26) o superior.

---
*Proyecto académico desarrollado en Kotlin para Android.*

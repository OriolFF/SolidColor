# SolidLight

A minimalist Android app that turns your device into a customizable light source with a unique candle mode effect.

## About

SolidLight was created by prompting using Sonet 3.7. It's a simple yet elegant app that transforms your device's screen into a solid color light with an optional flickering candle effect.

## Features

- **Solid Color Mode**: Display a solid color of your choice on the full screen
- **Candle Mode**: Add a realistic flickering effect to simulate a candle
- **Custom Color Picker**: Intuitive color selection interface
- **Persistent Settings**: App remembers your color and mode preferences
- **Multilingual Support**: Available in 11 languages:
  - English
  - Spanish
  - Catalan
  - Italian
  - Greek
  - Portuguese
  - French
  - German
  - Russian
  - Chinese
  - Japanese

## How to Use

1. **Change Color**: Double-tap anywhere on the screen to open the color picker
2. **Toggle Candle Mode**: Tap the candle icon in the bottom right corner to switch between solid color and candle mode
3. **Select Color**: Use the color picker to choose your desired color - changes apply immediately

## Architecture

SolidLight follows clean architecture principles:

- **UI Layer**: Jetpack Compose for modern, declarative UI
- **Domain Layer**: Use cases for business logic
- **Data Layer**: Repository pattern for data management
- **Dependency Injection**: Manual DI for simplicity

## Technical Details

- Built with Kotlin and Jetpack Compose
- Implements MVVM architecture with ViewModel
- Uses SharedPreferences for persistent storage
- Custom animations for candle flickering effect

## Requirements

- Android 5.0 (API level 21) or higher
- Kotlin 1.8.0 or higher
- Jetpack Compose 1.4.0 or higher

## License

This project is open source and available under the MIT License.

---

Created with ❤️ using Sonet 3.7

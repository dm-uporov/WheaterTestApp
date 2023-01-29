# WheaterTestApp

This repository is a solution for the test assignment described below:

> Objectives & Requirements:
> Develop a native application containing at least the following elements
> - A list view displaying 16 days weather forecast for user’s city (required user location)
> - A detail view displaying a maximum of information for a specific day's weather
> - On the detail view, display "Hot" if temperature is > 25°C, "Cold" if temperature < 10°C
> - Users get weather updates within 15 min periods when app in background (via notification)
> - Any addition that you can think of (feature/design/etc...)
> 
> Focus:
> - We use MVVM Architecture here at HolidayPirates but you are free to implement a different
> architecture as long as you can justify why
> - Showcase at least one Unit Tests implementation
> - Justify any Build Dependencies that you might use
> - Stick to Kotlin Style Guide / Code Convention
> - Code must be written in Kotlin
> - We will actually read your code, don't hesitate to put comments if you think it's necessary
>
> Technical information:
> - Documentation: http://openweathermap.org/api http://openweathermap.org/weather-conditions
> - Content url: http://api.openweathermap.org/data/2.5/forecast?q=Paris&units=metric&appid=648a3aac37935
e5b45e09727df728ac2

Due to restriction of the OpenWeatherMap service, the app uses another endpoint — 5-days forecast on every three hours basis.

## Demo
<img src="https://github.com/dm-uporov/WheaterTestApp/blob/master/demo.gif" width="360" height="640" />

## Instruction
In order to build and run the app on your own device/emulator you must provide your OpenWeatherMap api key:
1. Create a file `apikey.properties` in the root folder of the project
2. Add a single line to the file: `API_KEY="{your_api_key}"`

Free api key registration is available on the official website "https://openweathermap.org/"

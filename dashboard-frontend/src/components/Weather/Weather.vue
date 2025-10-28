<!--
  Weather Widget
  
  Displays current weather information for configured city.
  
  Features:
  - Current temperature in Celsius
  - "Feels like" temperature
  - Weather description (clear, cloudy, etc.)
  - Humidity percentage
  - Wind speed
  - Weather icon
  
  API: OpenWeatherMap (https://openweathermap.org)
  - Called directly from frontend (no backend proxy)
  - Free tier: 1000 calls/day
  - API key required (see weatherService.js)
  
  Configuration:
  - Edit city in Weather.js (default: Boston)
  - Change API key in weatherService.js
  
  Future enhancements:
  - City selector dropdown
  - 5-day forecast
  - Weather alerts
  - Multiple locations
-->
<template>
  <div class="weather-widget">
    <h2>Weather</h2>
    
    <div v-if="loading" class="loading">Loading weather...</div>
    <div v-if="error" class="error">{{ error }}</div>
    
    <div v-if="weather" class="weather-content">
      <div class="weather-header">
        <img 
          :src="`http://openweathermap.org/img/wn/${weather.icon}@2x.png`" 
          :alt="weather.description"
        />
        <div class="temperature">
          <h1>{{ Math.round(weather.temperature) }}°C</h1>
          <p>Feels like {{ Math.round(weather.feelsLike) }}°C</p>
        </div>
      </div>
      
      <div class="weather-details">
        <p class="description">{{ weather.description }}</p>
        <p class="city">{{ weather.city }}</p>
        <div class="stats">
          <span>💧 {{ weather.humidity }}%</span>
          <span>💨 {{ weather.windSpeed }} m/s</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./Weather.js"></script>
<style src="./Weather.css" scoped></style>
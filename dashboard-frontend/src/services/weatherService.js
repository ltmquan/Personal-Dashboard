/**
 * Weather Service
 * 
 * Fetches current weather data from OpenWeatherMap API.
 * Called directly from frontend (no backend proxy needed).
 * 
 * API: https://openweathermap.org/api
 * Documentation: https://openweathermap.org/current
 * 
 * Setup:
 * 1. Sign up at openweathermap.org
 * 2. Get API key (free tier: 1000 calls/day)
 * 3. Replace API_KEY below
 * 4. Wait 10 minutes for key activation
 * 
 * Configuration:
 * - API_KEY: Your OpenWeatherMap API key
 * - units: 'metric' (Celsius) or 'imperial' (Fahrenheit)
 * 
 * Response includes:
 * - Temperature (current and feels like)
 * - Weather description
 * - Humidity, wind speed
 * - Weather icon code
 */
import axios from 'axios'

const API_KEY = 'bce0fb8adf2b522088d21fefe6028b44'
const API_URL = 'https://api.openweathermap.org/data/2.5/weather'

export default {
  getWeather(city) {
    return axios.get(API_URL, {
      params: {
        q: city,
        appid: API_KEY,
        units: 'metric'
      }
    })
  }
}
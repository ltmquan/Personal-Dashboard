import weatherService from '../../services/weatherService'

export default {
  name: 'Weather',
  data() {
    return {
      weather: null,
      loading: false,
      error: null,
      city: 'Boston'
    }
  },
  mounted() {
    this.fetchWeather()
  },
  methods: {
    async fetchWeather() {
      this.loading = true
      this.error = null
      try {
        const response = await weatherService.getWeather(this.city)
        
        // Map the API response to our format
        const data = response.data
        this.weather = {
          city: data.name,
          description: data.weather[0].description,
          temperature: data.main.temp,
          feelsLike: data.main.feels_like,
          humidity: data.main.humidity,
          windSpeed: data.wind.speed,
          icon: data.weather[0].icon
        }
      } catch (err) {
        this.error = 'Failed to load weather'
        console.error(err)
      } finally {
        this.loading = false
      }
    }
  }
}
import axios from 'axios'

const API_URL = 'http://localhost:8080/api/definitions'

export default {
  getAllDefinitions() {
    return axios.get(API_URL)
  },

  getDefinitionById(id) {
    return axios.get(`${API_URL}/${id}`)
  },

  createDefinition(definition) {
    return axios.post(API_URL, definition)
  },

  updateDefinition(id, definition) {
    return axios.put(`${API_URL}/${id}`, definition)
  },

  deleteDefinition(id) {
    return axios.delete(`${API_URL}/${id}`)
  }
}

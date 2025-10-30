import axios from 'axios'

const API_URL = 'http://localhost:8080/api/theorems'

export default {
  getAllTheorems() {
    return axios.get(API_URL)
  },

  getTheoremById(id) {
    return axios.get(`${API_URL}/${id}`)
  },

  searchTheorems(searchText) {
    const keywords = searchText.trim().split(/\s+/).join(',')
    return axios.get(`${API_URL}/search`, {
      params: { keywords, limit: 5 }
    })
  },

  getMostRecentTheorem() {
    return axios.get(`${API_URL}/recent`)
  },

  createTheorem(theorem) {
    return axios.post(API_URL, theorem)
  },

  updateTheorem(id, theorem) {
    return axios.put(`${API_URL}/${id}`, theorem)
  },

  deleteTheorem(id) {
    return axios.delete(`${API_URL}/${id}`)
  }
}

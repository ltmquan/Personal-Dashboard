import axios from 'axios'

const API_URL = 'http://localhost:8080/api/papers'

export default {
  getAllPapers() {
    return axios.get(API_URL)
  },

  getPaperById(id) {
    return axios.get(`${API_URL}/${id}`)
  },

  createPaper(paper) {
    return axios.post(API_URL, paper)
  },

  updatePaper(id, paper) {
    return axios.put(`${API_URL}/${id}`, paper)
  },

  deletePaper(id) {
    return axios.delete(`${API_URL}/${id}`)
  }
}

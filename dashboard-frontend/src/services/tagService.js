import axios from 'axios'

const API_URL = 'http://localhost:8080/api/tags'

export default {
  getAllTags() {
    return axios.get(API_URL)
  },

  getTagById(id) {
    return axios.get(`${API_URL}/${id}`)
  },

  createTag(tag) {
    return axios.post(API_URL, tag)
  },

  updateTag(id, tag) {
    return axios.put(`${API_URL}/${id}`, tag)
  },

  deleteTag(id) {
    return axios.delete(`${API_URL}/${id}`)
  }
}

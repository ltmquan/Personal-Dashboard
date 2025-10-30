import axios from 'axios'

const API_URL = 'http://localhost:8080/api/posts'

export default {
  getAllPosts() {
    return axios.get(API_URL)
  },

  getPostById(id) {
    return axios.get(`${API_URL}/${id}`)
  },

  createPost(post) {
    return axios.post(API_URL, post)
  },

  updatePost(id, post) {
    return axios.put(`${API_URL}/${id}`, post)
  },

  deletePost(id) {
    return axios.delete(`${API_URL}/${id}`)
  }
}

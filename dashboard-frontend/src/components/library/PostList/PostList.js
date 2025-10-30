import postService from '../../../services/postService'

export default {
  name: 'PostList',
  props: {
    searchQuery: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      posts: [],
      loading: false,
      error: null,
      showAddForm: false,
      newPost: {
        title: '',
        body: '',
        tagIds: []
      }
    }
  },
  computed: {
    filteredPosts() {
      if (!this.searchQuery) return this.posts
      const query = this.searchQuery.toLowerCase()
      return this.posts.filter(post =>
        post.title.toLowerCase().includes(query) ||
        post.body.toLowerCase().includes(query)
      )
    }
  },
  methods: {
    async fetchPosts() {
      this.loading = true
      this.error = null
      try {
        const response = await postService.getAllPosts()
        this.posts = response.data
      } catch (err) {
        this.error = 'Failed to load posts. Please try again.'
        console.error('Error fetching posts:', err)
      } finally {
        this.loading = false
      }
    },

    async createPost() {
      if (!this.newPost.title || !this.newPost.body) {
        alert('Title and content are required')
        return
      }

      try {
        await postService.createPost(this.newPost)
        this.newPost = { title: '', body: '', tagIds: [] }
        this.showAddForm = false
        await this.fetchPosts()
      } catch (err) {
        alert('Failed to create post')
        console.error('Error creating post:', err)
      }
    },

    async deletePost(id) {
      if (!confirm('Delete this post?')) return

      try {
        await postService.deletePost(id)
        await this.fetchPosts()
      } catch (err) {
        alert('Failed to delete post')
        console.error('Error deleting post:', err)
      }
    },

    viewPost(post) {
      // TODO: Implement view/edit modal
      console.log('View post:', post)
    },

    truncateBody(body) {
      return body.length > 150 ? body.substring(0, 150) + '...' : body
    },

    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString()
    }
  },
  mounted() {
    this.fetchPosts()
  }
}

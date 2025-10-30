import theoremService from '../../../services/theoremService'
import { EMPTY_DATABASE } from '../../../constants/error-code'

export default {
  name: 'TheoremList',
  data() {
    return {
      theorems: [],
      searchQuery: '',
      searchResults: [],
      loading: false,
      error: null,
      showAddForm: false,
      newTheorem: {
        title: '',
        body: '',
        statement: '',
        proof: '',
        field: '',
        tagIds: []
      }
    }
  },
  methods: {
    async loadMostRecentTheorem() {
      this.loading = true
      this.error = null
      try {
        const response = await theoremService.getMostRecentTheorem()
        this.theorems.push(response.data)
      } catch (err) {
        if (err.response?.data?.errorCode === EMPTY_DATABASE) {
          // do nothing, just catching the error
        } else {
          this.error = 'Failed to load theorem'
        }
        console.error('Error fetching most recent theorem:', err)
      } finally {
        this.loading = false
      }
    },

    async onSearchInput() {
      if (!this.searchQuery.trim()) {
        this.searchResults = []
        return
      }

      this.loading = true
      this.error = null
      try {
        const response = await theoremService.searchTheorems(this.searchQuery)
        this.searchResults = response.data
      } catch (err) {
        this.error = 'Search failed'
        console.error('Error searching theorems:', err)
      } finally {
        this.loading = false
      }
    },

    async selectTheorem(id) {
      this.loading = true
      this.error = null
      this.searchQuery = ''
      this.searchResults = []
      try {
        const response = await theoremService.getTheoremById(id)
        this.theorems = [response.data]
      } catch (err) {
        this.error = 'Failed to load theorem'
        console.error('Error fetching theorem:', err)
      } finally {
        this.loading = false
      }
    },

    closeTheorem() {
      this.theorems[0] = []
      this.searchQuery = ''
      this.searchResults = []
    },

    async createTheorem() {
      if (!this.newTheorem.title || !this.newTheorem.body) {
        alert('Title and explanation are required')
        return
      }

      try {
        await theoremService.createTheorem(this.newTheorem)
        this.newTheorem = {
          title: '',
          body: '',
          statement: '',
          proof: '',
          field: '',
          tagIds: []
        }
        this.showAddForm = false
        await this.loadMostRecentTheorem()
      } catch (err) {
        alert('Failed to create theorem')
        console.error('Error creating theorem:', err)
      }
    },

    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString()
    }
  },
  mounted() {
    this.loadMostRecentTheorem()
  }
}

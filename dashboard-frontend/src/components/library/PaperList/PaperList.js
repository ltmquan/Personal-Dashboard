import paperService from '../../../services/paperService'

export default {
  name: 'PaperList',
  props: {
    searchQuery: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      papers: [],
      loading: false,
      error: null,
      showAddForm: false,
      newPaper: {
        title: '',
        body: '',
        authors: '',
        venue: '',
        year: null,
        doi: '',
        citationKey: '',
        pdfUrl: '',
        tagIds: []
      }
    }
  },
  computed: {
    filteredPapers() {
      if (!this.searchQuery) return this.papers
      const query = this.searchQuery.toLowerCase()
      return this.papers.filter(paper =>
        paper.title.toLowerCase().includes(query) ||
        (paper.authors && paper.authors.toLowerCase().includes(query)) ||
        (paper.venue && paper.venue.toLowerCase().includes(query)) ||
        paper.body.toLowerCase().includes(query)
      )
    }
  },
  methods: {
    async fetchPapers() {
      this.loading = true
      this.error = null
      try {
        const response = await paperService.getAllPapers()
        this.papers = response.data
      } catch (err) {
        this.error = 'Failed to load papers. Please try again.'
        console.error('Error fetching papers:', err)
      } finally {
        this.loading = false
      }
    },

    async createPaper() {
      if (!this.newPaper.title) {
        alert('Title is required')
        return
      }

      try {
        await paperService.createPaper(this.newPaper)
        this.newPaper = {
          title: '',
          body: '',
          authors: '',
          venue: '',
          year: null,
          doi: '',
          citationKey: '',
          pdfUrl: '',
          tagIds: []
        }
        this.showAddForm = false
        await this.fetchPapers()
      } catch (err) {
        alert('Failed to create paper')
        console.error('Error creating paper:', err)
      }
    },

    async deletePaper(id) {
      if (!confirm('Delete this paper?')) return

      try {
        await paperService.deletePaper(id)
        await this.fetchPapers()
      } catch (err) {
        alert('Failed to delete paper')
        console.error('Error deleting paper:', err)
      }
    },

    viewPaper(paper) {
      // TODO: Implement view/edit modal
      console.log('View paper:', paper)
    },

    openPdf(url) {
      window.open(url, '_blank')
    },

    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString()
    }
  },
  mounted() {
    this.fetchPapers()
  }
}

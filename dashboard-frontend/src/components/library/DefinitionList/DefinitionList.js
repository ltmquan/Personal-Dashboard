import definitionService from '../../../services/definitionService'

export default {
  name: 'DefinitionList',
  props: {
    searchQuery: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      definitions: [],
      loading: false,
      error: null,
      showAddForm: false,
      newDefinition: {
        title: '',
        body: '',
        field: '',
        relatedConcepts: '',
        tagIds: []
      }
    }
  },
  computed: {
    filteredDefinitions() {
      if (!this.searchQuery) return this.definitions
      const query = this.searchQuery.toLowerCase()
      return this.definitions.filter(definition =>
        definition.title.toLowerCase().includes(query) ||
        definition.body.toLowerCase().includes(query) ||
        (definition.field && definition.field.toLowerCase().includes(query))
      )
    }
  },
  methods: {
    async fetchDefinitions() {
      this.loading = true
      this.error = null
      try {
        const response = await definitionService.getAllDefinitions()
        this.definitions = response.data
      } catch (err) {
        this.error = 'Failed to load definitions. Please try again.'
        console.error('Error fetching definitions:', err)
      } finally {
        this.loading = false
      }
    },

    async createDefinition() {
      if (!this.newDefinition.title || !this.newDefinition.body) {
        alert('Title and explanation are required')
        return
      }

      try {
        await definitionService.createDefinition(this.newDefinition)
        this.newDefinition = {
          title: '',
          body: '',
          field: '',
          relatedConcepts: '',
          tagIds: []
        }
        this.showAddForm = false
        await this.fetchDefinitions()
      } catch (err) {
        alert('Failed to create definition')
        console.error('Error creating definition:', err)
      }
    },

    async deleteDefinition(id) {
      if (!confirm('Delete this definition?')) return

      try {
        await definitionService.deleteDefinition(id)
        await this.fetchDefinitions()
      } catch (err) {
        alert('Failed to delete definition')
        console.error('Error deleting definition:', err)
      }
    },

    viewDefinition(definition) {
      // TODO: Implement view/edit modal
      console.log('View definition:', definition)
    },

    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString()
    }
  },
  mounted() {
    this.fetchDefinitions()
  }
}

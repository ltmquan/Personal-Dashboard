import PostList from '../../components/library/PostList/PostList.vue'
import TheoremList from '../../components/library/TheoremList/TheoremList.vue'
import DefinitionList from '../../components/library/DefinitionList/DefinitionList.vue'
import PaperList from '../../components/library/PaperList/PaperList.vue'

export default {
  name: 'LibraryView',
  components: {
    PostList,
    TheoremList,
    DefinitionList,
    PaperList
  },
  data() {
    return {
      activeTab: 'posts',
      searchQuery: '',
      tabs: [
        { id: 'posts', label: 'Posts' },
        { id: 'theorems', label: 'Theorems' },
        { id: 'definitions', label: 'Definitions' },
        { id: 'papers', label: 'Papers' }
      ]
    }
  },
  methods: {
    handleSearch() {
      // Search is handled by passing searchQuery to child components
    }
  }
}

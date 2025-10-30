<template>
  <div class="definition-list">
    <div class="list-header">
      <h2>Definitions</h2>
      <button @click="showAddForm = !showAddForm" class="add-btn">
        {{ showAddForm ? 'Cancel' : '+ New Definition' }}
      </button>
    </div>

    <div v-if="showAddForm" class="add-form">
      <input v-model="newDefinition.title" placeholder="Title" />
      <textarea v-model="newDefinition.body" placeholder="Explanation" rows="3"></textarea>
      <input v-model="newDefinition.field" placeholder="Field (e.g., Graph Theory)" />
      <input v-model="newDefinition.relatedConcepts" placeholder="Related concepts (comma-separated)" />
      <button @click="createDefinition" class="submit-btn">Create Definition</button>
    </div>

    <div v-if="loading" class="loading">Loading definitions...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else-if="filteredDefinitions.length === 0" class="empty">
      {{ searchQuery ? 'No definitions match your search' : 'No definitions yet. Add your first definition!' }}
    </div>

    <div v-else class="definitions">
      <div v-for="definition in filteredDefinitions" :key="definition.id" class="definition-item">
        <div class="definition-content">
          <h3><katex-element :expression="definition.title" /></h3>
          <div class="body">
            <katex-element :expression="definition.body" />
          </div>
          <div v-if="definition.relatedConcepts" class="related-concepts">
            <strong>Related Concepts:</strong>
            <katex-element :expression="definition.relatedConcepts" />
          </div>
          <p v-if="definition.field" class="field"><span class="field-badge">{{ definition.field }}</span></p>
          <div class="timestamps">
            <small>Created: {{ formatDate(definition.createdAt) }}</small>
            <small>Updated: {{ formatDate(definition.updatedAt) }}</small>
          </div>
        </div>
        <div class="definition-actions">
          <button @click="viewDefinition(definition)" class="view-btn">View</button>
          <button @click="deleteDefinition(definition.id)" class="delete-btn">×</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./DefinitionList.js"></script>
<style src="./DefinitionList.css" scoped></style>

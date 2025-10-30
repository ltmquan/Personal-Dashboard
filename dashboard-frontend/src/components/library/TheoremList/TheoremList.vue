<template>
  <div class="theorem-list">
    <div class="list-header">
      <h2>Theorems</h2>
      <button @click="showAddForm = !showAddForm" class="add-btn">
        {{ showAddForm ? 'Cancel' : '+ New Theorem' }}
      </button>
    </div>

    <div v-if="showAddForm" class="add-form">
      <input v-model="newTheorem.title" placeholder="Title" />
      <textarea v-model="newTheorem.body" placeholder="Explanation" rows="3"></textarea>
      <input v-model="newTheorem.statement" placeholder="Formal statement" />
      <textarea v-model="newTheorem.proof" placeholder="Proof" rows="4"></textarea>
      <input v-model="newTheorem.field" placeholder="Field (e.g., Complexity Theory)" />
      <button @click="createTheorem" class="submit-btn">Create Theorem</button>
    </div>

    <div class="search-section">
      <input
        v-model="searchQuery"
        @input="onSearchInput"
        type="text"
        placeholder="Search theorems..."
        class="search-bar"
      />
    </div>

    <div v-if="loading" class="loading">Loading...</div>
    <div v-else-if="error" class="error">{{ error }}</div>

    <!-- Search Results -->
    <div v-else-if="searchQuery && searchResults.length > 0" class="search-results">
      <div
        v-for="theorem in searchResults"
        :key="theorem.id"
        @click="selectTheorem(theorem.id)"
        class="search-result-item"
      >
        {{ theorem.title }}
      </div>
    </div>

    <!-- No Results Message -->
    <div v-else-if="searchQuery && searchResults.length === 0" class="empty">
      No theorems found
    </div>

    <!-- No Selection Message -->
    <div v-else-if="theorems.length == 0" class="empty">
      No theorems selected
    </div>

    <!-- Selected Theorem Display -->
    <article v-else class="theorem-display">
      <button @click="closeTheorem" class="close-btn">×</button>
      <div class="theorem-content">
        <h1 class="theorem-title"><katex-element :expression="theorems[0].title" /></h1>
        <div class="timestamps">
          <small>Created: {{ formatDate(theorems[0].createdAt) }}</small>
          <small>Updated: {{ formatDate(theorems[0].updatedAt) }}</small>
        </div>
        <div class="body">
          <katex-element :expression="theorems[0].body" />
        </div>
        <div v-if="theorems[0].statement" class="statement">
          <strong>Statement:</strong>
          <katex-element :expression="theorems[0].statement" />
        </div>
        <div v-if="theorems[0].proof" class="proof">
          <strong>Proof:</strong>
          <katex-element :expression="theorems[0].proof" />
        </div>
        <p v-if="theorems[0].field" class="field"><span class="field-badge">{{ theorems[0].field }}</span></p>
      </div>
    </article>
  </div>
</template>

<script src="./TheoremList.js"></script>
<style src="./TheoremList.css" scoped></style>

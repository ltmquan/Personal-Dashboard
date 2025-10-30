<template>
  <div class="paper-list">
    <div class="list-header">
      <h2>Papers</h2>
      <button @click="showAddForm = !showAddForm" class="add-btn">
        {{ showAddForm ? 'Cancel' : '+ New Paper' }}
      </button>
    </div>

    <div v-if="showAddForm" class="add-form">
      <input v-model="newPaper.title" placeholder="Paper title" />
      <input v-model="newPaper.authors" placeholder="Authors (comma-separated)" />
      <textarea v-model="newPaper.body" placeholder="Your notes on the paper" rows="3"></textarea>
      <input v-model="newPaper.venue" placeholder="Venue (e.g., STOC 2023)" />
      <input v-model="newPaper.year" type="number" placeholder="Year" />
      <input v-model="newPaper.doi" placeholder="DOI (optional)" />
      <input v-model="newPaper.citationKey" placeholder="Citation key (e.g., Smith2023)" />
      <input v-model="newPaper.pdfUrl" placeholder="PDF URL (optional)" />
      <button @click="createPaper" class="submit-btn">Create Paper</button>
    </div>

    <div v-if="loading" class="loading">Loading papers...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else-if="filteredPapers.length === 0" class="empty">
      {{ searchQuery ? 'No papers match your search' : 'No papers yet. Add your first paper!' }}
    </div>

    <div v-else class="papers">
      <div v-for="paper in filteredPapers" :key="paper.id" class="paper-item">
        <div class="paper-content">
          <h3><katex-element :expression="paper.title" /></h3>
          <p v-if="paper.authors" class="authors">{{ paper.authors }}</p>
          <div v-if="paper.venue || paper.year" class="publication-info">
            <span v-if="paper.venue" class="venue">{{ paper.venue }}</span>
            <span v-if="paper.year" class="year">{{ paper.year }}</span>
          </div>
          <div v-if="paper.myNotes" class="my-notes">
            <strong>My Notes:</strong>
            <katex-element :expression="paper.myNotes" />
          </div>
          <p v-if="paper.citationKey" class="citation-key"><code>{{ paper.citationKey }}</code></p>
          <div class="timestamps">
            <small>Added: {{ formatDate(paper.createdAt) }}</small>
            <small>Updated: {{ formatDate(paper.updatedAt) }}</small>
          </div>
        </div>
        <div class="paper-actions">
          <button v-if="paper.pdfUrl" @click="openPdf(paper.pdfUrl)" class="pdf-btn">PDF</button>
          <button @click="viewPaper(paper)" class="view-btn">View</button>
          <button @click="deletePaper(paper.id)" class="delete-btn">×</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./PaperList.js"></script>
<style src="./PaperList.css" scoped></style>

<template>
  <div class="library">
    <header class="library-header">
      <h1>Library</h1>
      <div class="search-bar">
        <input
          v-model="searchQuery"
          type="text"
          placeholder="Search your library..."
          @input="handleSearch"
        />
      </div>
    </header>

    <nav class="library-tabs">
      <button
        v-for="tab in tabs"
        :key="tab.id"
        :class="['tab-button', { active: activeTab === tab.id }]"
        @click="activeTab = tab.id"
      >
        {{ tab.label }}
      </button>
    </nav>

    <main class="library-content">
      <PostList v-if="activeTab === 'posts'" :searchQuery="searchQuery" />
      <TheoremList v-if="activeTab === 'theorems'" :searchQuery="searchQuery" />
      <DefinitionList v-if="activeTab === 'definitions'" :searchQuery="searchQuery" />
      <PaperList v-if="activeTab === 'papers'" :searchQuery="searchQuery" />
    </main>
  </div>
</template>

<script src="./LibraryView.js"></script>
<style src="./LibraryView.css" scoped></style>

<template>
  <div class="post-list">
    <div class="list-header">
      <h2>Posts</h2>
      <button @click="showAddForm = !showAddForm" class="add-btn">
        {{ showAddForm ? 'Cancel' : '+ New Post' }}
      </button>
    </div>

    <div v-if="showAddForm" class="add-form">
      <input v-model="newPost.title" placeholder="Title" />
      <textarea v-model="newPost.body" placeholder="Content" rows="4"></textarea>
      <button @click="createPost" class="submit-btn">Create Post</button>
    </div>

    <div v-if="loading" class="loading">Loading posts...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else-if="filteredPosts.length === 0" class="empty">
      {{ searchQuery ? 'No posts match your search' : 'No posts yet. Create your first post!' }}
    </div>

    <div v-else class="posts">
      <article v-for="post in filteredPosts" :key="post.id" class="post-item">
        <div class="post-header">
          <h1 class="post-title">{{ post.title }}</h1>
          <div class="timestamps">
            <small>Created: {{ formatDate(post.createdAt) }}</small>
            <small>Updated: {{ formatDate(post.updatedAt) }}</small>
          </div>
        </div>
        <div class="post-body">
          {{ post.body }}
        </div>
        <div class="post-actions">
          <button @click="deletePost(post.id)" class="delete-btn">Delete</button>
        </div>
      </article>
    </div>
  </div>
</template>

<script src="./PostList.js"></script>
<style src="./PostList.css" scoped></style>

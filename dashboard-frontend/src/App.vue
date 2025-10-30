<template>
  <div id="app">
    <nav class="main-nav">
      <router-link to="/">Dashboard</router-link>
      <router-link to="/library">Library</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
.main-nav {
  background: white;
  padding: 15px 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  gap: 20px;
  margin-bottom: 0;
}

.main-nav a {
  padding: 10px 20px;
  text-decoration: none;
  color: #666;
  font-weight: 500;
  border-radius: 4px;
  transition: all 0.2s;
}

.main-nav a:hover {
  background: #f5f5f5;
  color: #333;
}

.main-nav a.router-link-active {
  color: #4CAF50;
  background: #f1f8f4;
}
</style>
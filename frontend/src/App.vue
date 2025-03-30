<script setup>
import { ref } from 'vue';

let movies = ref([
  {
    title: "机动战士高达GQuuuuuuX-Beginning-",
    id: 1,
  }
])
</script>

<template>
  <h1>Movies</h1>
  <div v-for="movie in movies">
    <a :href="`movies/${movie.id}`">
      <div>
        <img
          :src="`images/${movie.id}.png`"
          :alt="movie.title"
          width="200"
        >
        <p>{{ movie.title }}</p>
      </div>
    </a>
  </div>
  <p>Test GitHub Action - 2</p>
</template>

<style scoped>
/* .logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
} */
</style>

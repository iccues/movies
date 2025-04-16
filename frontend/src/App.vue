<script setup lang="ts">
import { ref } from 'vue';
import MovieCard from './components/MovieCard.vue';
import TopUserBar from './components/TopUserBar.vue';
import axios from 'axios';
import type { MovieSummary } from './type/api/movie';

let movies = ref<MovieSummary[]>([])

axios.get<MovieSummary[]>('/api/movie/movie_list')
    .then(response => {
        movies.value = response.data;
    })
    .catch(error => {
        console.error('Error fetching:', error);
    });

</script>

<template>
    <TopUserBar />
    <div class="movie-grid">
        <MovieCard v-for="movie in movies" :key="movie.mid" :movie="movie" />
    </div>
</template>

<style scoped>
.movie-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 24px;
    padding: 0px 40px 40px;
    justify-items: center;
    box-sizing: border-box;
}
</style>
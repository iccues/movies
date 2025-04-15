<script setup lang="ts">
import { ref } from 'vue';
import MovieCard from './components/MovieCard.vue';
import TopUserBar from './components/TopUserBar.vue';
import axios from 'axios';
import type { MovieSummary } from './type/api/movie';

let movies = ref<MovieSummary[]>([])

axios.get<MovieSummary[]>('/api/movie_list')
    .then(response => {
        movies.value = response.data;
    })
    .catch(error => {
        console.error('Error fetching:', error);
    });

</script>

<template>
    <TopUserBar />
    <MovieCard
        v-for="movie in movies"
        :key="movie.mid"
        :movie="movie"
    />
</template>

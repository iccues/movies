<script setup lang="ts">
import { ref } from 'vue';
import MovieCard from './components/MovieCard.vue';
import UserCard from './components/UserCard.vue';
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
    <h1>Movies</h1>
    <UserCard />
    <MovieCard
        v-for="movie in movies"
        :key="movie.mid"
        :movie="movie"
    />
</template>

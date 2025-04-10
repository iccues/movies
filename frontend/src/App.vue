<script setup>
import { ref } from 'vue';
import MovieCard from './components/MovieCard.vue';
import UserCard from './components/UserCard.vue';
import axios from 'axios';

const movies = ref()

axios.get('/api/movie_list')
    .then(response => {
        console.log('Response:', response);
        return response;})
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
        :key="movie.id"
        :id="movie.id"
        :title="movie.title"
    />
</template>

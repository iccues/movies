<script setup>
import { ref } from 'vue';
import MovieCard from './components/MovieCard.vue'

let movies = ref([
    {
        title: "机动战士高达GQuuuuuuX-Beginning-",
        id: 1,
    }
])
</script>

<template>
    <h1>Movies</h1>
    <MovieCard
        v-for="movie in movies"
        :key="movie.id"
        :id="movie.id"
        :title="movie.title"
    />
</template>

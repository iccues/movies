<script setup lang="ts">
import { ref } from 'vue'
import { type MovieDetails } from '../../type/api/movie';
import { type Showtime } from '../../type/api/showtime';
import ShowtimeCard from '../../components/ShowtimeCard.vue';
import axios from 'axios';

let movieDetail = ref<MovieDetails | null>(null);
let showtimes = ref<Showtime[]>([]);
let mid = new URLSearchParams(window.location.search).get('mid');

axios.get<MovieDetails>(`/api/movie_info?mid=${mid}`)
    .then(response => {
        movieDetail.value = response.data;
    })
    .catch(error => {
        console.error('Error fetching movie details:', error);
    });

axios.get<Showtime[]>(`/api/showtime_list?mid=${mid}`)
    .then(response => {
        showtimes.value = response.data;
    })
    .catch(error => {
        console.error('Error fetching showtimes:', error);
    });
    
    

</script>

<template>
    <h1>Movie</h1>
    <img :src="`/images/${movieDetail?.mid}.png`" :alt="movieDetail?.title" width="200" />
    <label>{{ movieDetail?.title }}</label>
    <ShowtimeCard v-for="showtime in showtimes" :key="showtime.mid" :showtime="showtime" />
</template>
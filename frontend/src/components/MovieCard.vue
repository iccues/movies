<script setup lang="ts">
import type { MovieSummary } from '../type/api/movie';

defineProps<{
    movie: MovieSummary,
}>();
</script>

<template>
    <a :href="`pages/movie.html?mid=${movie.mid}`" class="movie-card-link">
        <div class="movie-card">
            <img :src="`images/${movie.mid}.png`" :alt="movie.title" class="movie-image" />
            <p class="movie-title">{{ movie.title }}</p>
        </div>
    </a>
</template>

<style scoped>
.movie-card-link {
    text-decoration: none;
    color: inherit;
}

.movie-card {
    width: 200px;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s ease, box-shadow 0.2s ease;
    background-color: #fff;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.movie-card:hover {
    transform: translateY(-4px);
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.movie-image {
    width: 100%;
    height: 250px;
    object-fit: cover;
    border-radius: 12px;
}

.movie-title {
    /* padding: 12px; */
    font-size: 16px;
    font-weight: 600;
    text-align: center;
    color: #333;
}
</style>

<script setup lang="ts">
import type { MovieSummary } from '../type/api/movie';

defineProps<{
    movie: MovieSummary,
}>();

</script>

<template>
    <a :href="`pages/movie.html?mid=${movie.mid}`">
        <div>
            <img
                :src="`images/${movie.mid}.png`"
                :alt="movie.title"
                width="200"
            >
            <p>{{ movie.title }}</p>
        </div>
    </a>
</template>

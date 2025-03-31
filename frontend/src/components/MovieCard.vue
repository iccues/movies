<script setup>

defineProps({
    id: Number,
    title: String,
})

</script>

<template>
    <a :href="`pages/movie.html?${id}`">
        <div>
            <img
                :src="`images/${id}.png`"
                :alt="title"
                width="200"
            >
            <p>{{ title }}</p>
        </div>
    </a>
</template>

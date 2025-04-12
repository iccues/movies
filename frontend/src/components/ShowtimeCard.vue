<script setup lang="ts">
import type { Showtime } from '../type/api/showtime';

defineProps<{
    showtime: Showtime,
}>();

</script>

<template>
    <div>
        <label>{{ showtime.startTime }}</label>
    </div>
</template>
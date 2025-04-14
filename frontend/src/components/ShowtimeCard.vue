<script setup lang="ts">
import type { Showtime } from '../type/api/showtime';

defineProps<{
    showtime: Showtime,
}>();

</script>

<template>
    <div>
        <label>{{ showtime.startTime }}</label>
        <label>{{ showtime.endTime }}</label>
        <label>{{ showtime.cinemaName }}</label>
        <label>{{ showtime.hallName }}</label>
        <label>{{ showtime.price }}</label>
        <label>{{ showtime.totalSeats - showtime.bookedSeats }} / {{ showtime.totalSeats }}</label>
        <a :href="`/pages/newOrder.html?sid=${showtime.sid}`">Book Now</a>
    </div>
</template>
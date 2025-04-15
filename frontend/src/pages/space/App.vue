<script setup lang="ts">
import { ref } from 'vue'
import { ElTable, ElTableColumn, ElButton } from 'element-plus'
import type { MovieDetails } from '../../type/api/movie';
import type { Showtime } from '../../type/api/showtime';
import axios from 'axios';
import TopUserBar from '../../components/TopUserBar.vue';

let movieDetail = ref<MovieDetails | null>(null);
let showtimes = ref<Showtime[]>([]);
let mid = new URLSearchParams(window.location.search).get('mid');

axios.get<MovieDetails>(`/api/movie_info?mid=${mid}`)
    .then(res => movieDetail.value = res.data)
    .catch(err => console.error('Movie fetch failed:', err));

axios.get<Showtime[]>(`/api/showtime_list?mid=${mid}`)
    .then(res => showtimes.value = res.data)
    .catch(err => console.error('Showtime fetch failed:', err));
</script>

<template>
    <TopUserBar />
    <div class="movie-container">
        <div class="movie-info">
            <img :src="`/images/${movieDetail?.mid}.png`" :alt="movieDetail?.title" class="movie-image" />
            <div class="movie-title">{{ movieDetail?.title }}</div>
        </div>

        <el-table :data="showtimes" style="width: 100%" stripe>
            <el-table-column prop="startTime" label="Start Time" width="150" />
            <el-table-column prop="endTime" label="End Time" width="150" />
            <el-table-column prop="cinemaName" label="Cinema" />
            <el-table-column prop="hallName" label="Hall" />
            <el-table-column prop="price" label="Price" width="100" />
            <el-table-column label="Seats" width="140">
                <template #default="{ row }">
                    {{ row.totalSeats - row.bookedSeats }} / {{ row.totalSeats }}
                </template>
            </el-table-column>
            <el-table-column label="Action" width="120">
                <template #default="{ row }">
                    <el-button size="small" :href="`/pages/newOrder.html?sid=${row.sid}`" tag="a">
                        Book Now
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<style scoped>
.movie-container {
    max-width: 960px;
    margin: 80px auto 40px;
    padding: 0 20px;
}

.movie-info {
    display: flex;
    align-items: center;
    margin-bottom: 32px;
}

.movie-image {
    width: 200px;
    height: auto;
    margin-right: 24px;
    border-radius: 8px;
}

.movie-title {
    font-size: 24px;
    font-weight: 600;
}
</style>

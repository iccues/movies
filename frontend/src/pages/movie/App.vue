<script setup lang="ts">
import { ref } from 'vue'
import { ElTable, ElTableColumn, ElButton } from 'element-plus'
import type { MovieDetail } from '../../type/api/movie';
import type { Showtime } from '../../type/api/showtime';
import axios from 'axios';
import TopUserBar from '../../components/TopUserBar/TopUserBar.vue';
import PayDialog from '../../components/PayDialog.vue'

let movieDetail = ref<MovieDetail | null>(null);
let showtimes = ref<Showtime[]>([]);
let mid = new URLSearchParams(window.location.search).get('mid');
const showPayDialog = ref(false)
const selectedSid = ref<number | null>(null)
const username = ref<string | null>(null)

axios.get<MovieDetail>(`/api/movie/movie_info?mid=${mid}`)
    .then(res => movieDetail.value = res.data)
    .catch(err => console.error('Movie fetch failed:', err));

axios.get<Showtime[]>(`/api/showtime/showtime_list?mid=${mid}`)
    .then(res => showtimes.value = res.data)
    .catch(err => console.error('Showtime fetch failed:', err));

function bookNow(sid: number) {
    selectedSid.value = sid
    showPayDialog.value = true
}
</script>

<template>
    <TopUserBar v-model:username="username" />
    <div class="movie-container">
        <div class="movie-info">
            <img :src="`/images/${movieDetail?.mid}.png`" :alt="movieDetail?.title" class="movie-image" />
            <div class="movie-text">
                <div class="movie-title">{{ movieDetail?.title }}</div>
                <p class="movie-intro">{{ movieDetail?.introduction }}</p>
            </div>
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
                    <el-button size="small" @click="bookNow(row.sid)"
                        :disabled="row.bookedSeats >= row.totalSeats || !username">
                        Book Now
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
    <PayDialog v-if="selectedSid" v-model="showPayDialog" :sid="selectedSid" />
</template>

<style scoped>
.movie-container {
    max-width: 960px;
    margin: 80px auto 40px;
    padding: 0 20px;
}

.movie-info {
    display: flex;
    align-items: flex-start;
    margin-bottom: 32px;
}

.movie-text {
    display: flex;
    flex-direction: column;
}

.movie-title {
    font-size: 24px;
    font-weight: 600;
}

.movie-intro {
    margin-top: 8px;
    line-height: 1.6;
}

.movie-image {
    width: 200px;
    height: auto;
    margin-right: 24px;
    border-radius: 8px;
}
</style>

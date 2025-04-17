<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage, ElMessageBox, ElTable, ElTableColumn, ElButton, ElSelect, ElOption } from 'element-plus';
import axios from 'axios';
import type { Showtime } from '../../type/api/showtime';
import AddEditShowtimeDialog from './AddShowtimeDialog.vue';
import type { MovieSummary } from '../../type/api/movie';

const showtimes = ref<Showtime[]>([]);

const movies = ref<MovieSummary[]>([]);
const selectedMid = ref<number>(0);

const showShowtimeDialog = ref(false);

function fetchMovies() {
    axios.get<MovieSummary[]>('/api/movie/movie_list').then(res => {
        movies.value = res.data;
        if (res.data.length > 0) {
            selectedMid.value = res.data[0].mid;
            fetchShowtimes();
        }
    });
}

function fetchShowtimes() {
    showtimes.value = [];
    axios.get(`/api/showtime/showtime_list?mid=${selectedMid.value}`).then(res => {
        showtimes.value = res.data;
    });
}

function addShowtime() {
    showShowtimeDialog.value = true;
}

function deleteShowtime(sid: string) {
    ElMessageBox.confirm('Delete this showtime?', 'Warning', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning'
    }).then(() => {
        axios.delete(`/api/showtime/delete?sid=${sid}`).then(() => {
            ElMessage.success('Showtime deleted');
            fetchShowtimes();
        });
    });
}

onMounted(() => {
    fetchMovies();
});
</script>

<template>
    <div class="admin-content">
        <h2>Showtime List</h2>
        <el-select v-model="selectedMid" placeholder="Select Movie" style="margin-bottom: 16px; width: 300px"
            @change="fetchShowtimes">
            <el-option v-for="movie in movies" :key="movie.mid" :label="movie.title" :value="movie.mid" />
        </el-select>
        <el-table :data="showtimes" style="width: 100%">
            <el-table-column prop="cinemaName" label="Cinema" />
            <el-table-column prop="startTime" label="Start" />
            <el-table-column prop="endTime" label="End" />
            <el-table-column prop="hallName" label="Hall" />
            <el-table-column prop="price" label="Price" />
            <el-table-column prop="totalSeats" label="Seats" />
            <el-table-column label="Action">
                <template #default="{ row }">
                    <el-button type="danger" size="small" @click="deleteShowtime(row.sid)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-button type="primary" @click="addShowtime">Add Showtime</el-button>

        <AddEditShowtimeDialog v-model:visible="showShowtimeDialog" :mid="selectedMid"
            @success="fetchShowtimes" />
    </div>
</template>

<style scoped>
.admin-menu {
    width: 200px;
    margin-right: 24px;
}

.admin-content {
    flex: 1;
}

.mt-4 {
    margin-top: 24px;
}
</style>

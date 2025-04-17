<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox, ElTable, ElTableColumn, ElButton, ElInput, ElForm, ElFormItem } from 'element-plus';
import axios from 'axios';
import type { Showtime } from '../../type/api/showtime';

const showtimes = ref<Showtime[]>([]);

const newShowtime = reactive({
    movieId: '',
    startTime: '',
    endTime: '',
    cinemaName: '',
    hallName: '',
    price: 0,
    totalSeats: 0
});

function fetchShowtimes() {
    axios.get('/api/admin/showtimes').then(res => {
        showtimes.value = res.data;
    });
}

function addShowtime() {
    axios.post('/api/admin/showtimes', newShowtime).then(() => {
        ElMessage.success('Showtime added');
        fetchShowtimes();
    });
}

function deleteShowtime(sid: string) {
    ElMessageBox.confirm('Delete this showtime?', 'Warning', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning'
    }).then(() => {
        axios.delete(`/api/admin/showtimes/${sid}`).then(() => {
            ElMessage.success('Showtime deleted');
            fetchShowtimes();
        });
    });
}

onMounted(() => {
    fetchShowtimes();
});
</script>

<template>
        <div class="admin-content">
            <h2>Showtime List</h2>
            <el-table :data="showtimes" style="width: 100%">
                <el-table-column prop="movieTitle" label="Movie" />
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

            <h3 class="mt-4">Add New Showtime</h3>
            <el-form :model="newShowtime" label-width="120px">
                <el-form-item label="Movie ID">
                    <el-input v-model="newShowtime.movieId" />
                </el-form-item>
                <el-form-item label="Start Time">
                    <el-input v-model="newShowtime.startTime" />
                </el-form-item>
                <el-form-item label="End Time">
                    <el-input v-model="newShowtime.endTime" />
                </el-form-item>
                <el-form-item label="Cinema">
                    <el-input v-model="newShowtime.cinemaName" />
                </el-form-item>
                <el-form-item label="Hall">
                    <el-input v-model="newShowtime.hallName" />
                </el-form-item>
                <el-form-item label="Price">
                    <el-input type="number" v-model="newShowtime.price" />
                </el-form-item>
                <el-form-item label="Total Seats">
                    <el-input type="number" v-model="newShowtime.totalSeats" />
                </el-form-item>
                <el-button type="primary" @click="addShowtime">Add Showtime</el-button>
            </el-form>
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

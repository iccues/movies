<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage, ElMessageBox, ElTable, ElTableColumn, ElButton } from 'element-plus';
import axios from 'axios';
import type { MovieDetail } from '../../type/api/movie';
import AddMovieDialog from './AddMovieDialog.vue';


const movies = ref<MovieDetail[]>([]);

const editingMid = ref<MovieDetail | null>(null);

const showAddMovieDialog = ref(false);

function fetchMovies() {
    axios.get('/api/movie/movie_detail_list').then(res => {
        movies.value = res.data;
    });
}

function addMovie() {
    showAddMovieDialog.value = true;
    editingMid.value = null;
}

function editMovie(movie: MovieDetail) {
    showAddMovieDialog.value = true;
    editingMid.value = movie;
}

function deleteMovie(mid: string) {
    ElMessageBox.confirm('Delete this movie?', 'Warning', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning'
    }).then(() => {
        axios.delete(`/api/movies/delete?mid=${mid}`).then(() => {
            ElMessage.success('Movie deleted');
            fetchMovies();
        });
    });
}


onMounted(() => {
    fetchMovies();
});
</script>

<template>
    <div class="admin-content">
        <h2>Movie List</h2>
        <el-table :data="movies" style="width: 100%">
            <el-table-column prop="title" label="Title" width="250" />
            <el-table-column prop="introduction" label="Intro" />
            <el-table-column label="Action" width="200">
                <template #default="{ row }">
                    <el-button size="small" @click="editMovie(row)">Edit</el-button>
                    <el-button type="danger" size="small" @click="deleteMovie(row.mid)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-button type="primary" @click="addMovie">Add Movie</el-button>
        <AddMovieDialog :visible="showAddMovieDialog" :movie="editingMid" @update:visible="showAddMovieDialog = $event"
            @success="fetchMovies" />
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

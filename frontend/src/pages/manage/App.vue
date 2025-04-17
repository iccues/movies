<script setup lang="ts">
import { ref } from 'vue';
import { ElMenu, ElMenuItem } from 'element-plus';
import MovieManager from '../../components/manager/MovieManager.vue';
import ShowtimeManager from '../../components/manager/ShowtimeManager.vue';
import TopUserBar from '../../components/TopUserBar/TopUserBar.vue';

const activeMenu = ref<'movies' | 'showtimes'>('movies');

</script>

<template>
    <TopUserBar />
    <div class="admin-container">
        <el-menu class="admin-menu" default-active="movies" @select="key => activeMenu = key as 'movies' | 'showtimes'">
            <el-menu-item index="movies">Manage Movies</el-menu-item>
            <el-menu-item index="showtimes">Manage Showtimes</el-menu-item>
        </el-menu>

        <div class="admin-content">
            <MovieManager v-if="activeMenu === 'movies'" />
            <ShowtimeManager v-else-if="activeMenu === 'showtimes'" />
        </div>
    </div>
</template>

<style scoped>
.admin-container {
    display: flex;
    padding: 24px;
}

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

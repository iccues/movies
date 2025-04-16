<script setup lang="ts">
import { ref, onMounted } from "vue";
import axios from "axios";
import { type Result } from "../type/result";
import AuthDialog from "./AuthDialog.vue";
import TicketListDialog from "./TicketListDialog.vue";
import { ElButton } from "element-plus";

let username = ref<string | null>(null);
let showAuthDialog = ref(false);
let showTicketListDialog = ref(false);

defineProps<{
    username?: string | null
}>();
const emit = defineEmits<{
    (e: 'update:username', value: string | null): void
}>();

onMounted(() => {
    axios.get<Result<string>>("/api/user/username")
        .then((res) => {
            if (res.data.ok) {
                emit('update:username', res.data.value);
                username.value = res.data.value;
            }
        })
        .catch((err) => {
            console.error("Failed to fetch username:", err);
        });
});
</script>

<template>
    <div class="top-user-bar">
        <div class="top-user-bar-content">
            <div class="left">
                <span class="app-name">Movies</span>
            </div>
            <div class="right">
                <template v-if="username">
                    <el-button>{{ username }}</el-button>
                    <el-button @click="showTicketListDialog = true">My Tickets</el-button>
                </template>
                <template v-else>
                    <el-button @click="showAuthDialog = true">Log In / Sign Up</el-button>
                </template>
            </div>
        </div>
        <AuthDialog v-model:dialogVisible="showAuthDialog" />
        <TicketListDialog v-model="showTicketListDialog" />
    </div>
    <div :style="{ height: '60px' }"></div>
</template>

<style scoped>
.top-user-bar {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    height: 60px;
    background-color: transparent;
    /* 完全透明背景 */
    box-shadow: none;
    /* 无阴影 */
    z-index: 1000;
}

.top-user-bar-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100%;
    padding: 0 24px;
}

.app-name {
    font-size: 18px;
    font-weight: 600;
    color: #333;
}

.right>* {
    margin-left: 12px;
}
</style>

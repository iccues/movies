<script setup lang="ts">
import { ref, onMounted } from "vue";
import axios from "axios";
import { type Result } from "../../type/result";
import { ElButton } from "element-plus";
import AuthDialog from "./AuthDialog.vue";
import TicketListDialog from "./TicketListDialog.vue";
import UserSettingsDialog from "./UserSettingsDialog.vue";
import type { UserInfo } from "../../type/api/user";

let userInfo = ref<UserInfo | null>(null);
let showAuthDialog = ref(false);
let showTicketListDialog = ref(false);
let showUserSettingsDialog = ref(false);

defineProps<{
    userInfo?: UserInfo | null
}>();
const emit = defineEmits<{
    (e: 'update:userInfo', value: UserInfo | null): void
}>();

onMounted(() => {
    axios.get<Result<UserInfo>>("/api/user/user_info")
        .then((res) => {
            if (res.data.ok) {
                emit('update:userInfo', res.data.value);
                userInfo.value = res.data.value;
            }
        })
        .catch((err) => {
            console.error("Failed to fetch userInfo:", err);
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
                <template v-if="userInfo">
                    <el-button @click="showUserSettingsDialog = true">{{ userInfo.username }}</el-button>
                    <el-button @click="showTicketListDialog = true">My Tickets</el-button>
                    <el-button v-if="userInfo.isAdmin" href="/pages/manage.html" tag="a">Management</el-button>
                </template>
                <template v-else>
                    <el-button @click="showAuthDialog = true">Log In / Sign Up</el-button>
                </template>
            </div>
        </div>
        <AuthDialog v-model:dialogVisible="showAuthDialog" />
        <TicketListDialog v-model="showTicketListDialog" />
        <UserSettingsDialog v-model:visible="showUserSettingsDialog" />
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

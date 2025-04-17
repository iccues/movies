<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue';
import axios from 'axios';
import type { Result } from '../../type/result';
import { ElMessage, ElMessageBox, ElDialog, ElTabs, ElTabPane, ElInput, ElButton } from 'element-plus';

const props = defineProps<{
    visible: boolean
}>();
const emit = defineEmits<{
    (e: 'update:visible', value: boolean): void
}>();

const activeTab = ref('rename');

// 双向绑定控制
watch(() => props.visible, (val) => {
    dialogVisible.value = val;
});

const dialogVisible = ref(props.visible);
watch(dialogVisible, (val) => {
    emit('update:visible', val);
});

// Rename
const newUsername = ref('');

// Change password
const oldPassword = ref('');
const newPassword = ref('');
const confirmPassword = ref('');

function rename() {
    axios.post<Result<null>>('/api/user/rename', {
        username: newUsername.value,
    }).then(res => {
        if (res.data.ok) {
            ElMessage.success('Username updated');
            window.location.reload();
        } else {
            ElMessage.error(res.data.error);
        }
    }).catch(() => {
        ElMessage.error('Rename failed');
    });
}

function changePassword() {
    if (newPassword.value !== confirmPassword.value) {
        ElMessage.warning("Passwords do not match");
        return;
    }

    axios.post<Result<null>>('/api/user/change_password', {
        oldPassword: oldPassword.value,
        newPassword: newPassword.value,
    }).then(res => {
        if (res.data.ok) {
            ElMessage.success('Password changed');
        } else {
            ElMessage.error(res.data.error);
        }
    }).catch(() => {
        ElMessage.error('Password change failed');
    });
}

function logout() {
    axios.post('/api/user/logout').then(() => {
        window.location.reload();
    });
}

function deleteAccount() {
    ElMessageBox.confirm(
        'Are you sure you want to delete your account? This action cannot be undone.',
        'Warning',
        {
            confirmButtonText: 'Yes',
            cancelButtonText: 'No',
            type: 'warning',
        }
    ).then(() => {
        axios.delete<Result<null>>('/api/user/delete_account').then(res => {
            if (res.data.ok) {
                ElMessage.success('Account deleted');
                window.location.reload();
            } else {
                ElMessage.error(res.data.error);
            }
        });
    }).catch(() => {
        ElMessage.info('Cancelled');
    });
}
</script>

<template>
    <el-dialog v-model="dialogVisible" title="User Settings" width="500px">
        <el-tabs v-model="activeTab">
            <el-tab-pane label="Rename" name="rename">
                <el-input v-model="newUsername" placeholder="New Username" />
                <el-button type="primary" class="mt-2" @click="rename">Rename</el-button>
            </el-tab-pane>

            <el-tab-pane label="Change Password" name="password">
                <el-input v-model="oldPassword" placeholder="Old Password" show-password />
                <el-input v-model="newPassword" placeholder="New Password" show-password class="mt-2" />
                <el-input v-model="confirmPassword" placeholder="Confirm New Password" show-password class="mt-2" />
                <el-button type="primary" class="mt-2" @click="changePassword">Change Password</el-button>
            </el-tab-pane>

            <el-tab-pane label="Account" name="account">
                <el-button type="warning" plain @click="logout">Logout</el-button>
                <el-button type="danger" plain class="ml-2" @click="deleteAccount">Delete Account</el-button>
            </el-tab-pane>
        </el-tabs>
    </el-dialog>
</template>

<style scoped>
.mt-2 {
    margin-top: 12px;
}
.ml-2 {
    margin-left: 12px;
}
</style>

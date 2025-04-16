<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage, ElDialog, ElTable, ElTableColumn, ElButton } from 'element-plus'
import type { TicketDetail } from '../type/api/ticketDetail'

defineProps<{
    modelValue: boolean
}>()

const emit = defineEmits<{
    (e: 'update:modelValue', value: boolean): void
}>()

const tickets = ref<TicketDetail[]>([])
const loading = ref(false)

function loadTickets() {
    loading.value = true
    axios
        .get<TicketDetail[]>('/api/ticket/ticket_detail_list')
        .then(res => {
            tickets.value = res.data
        })
        .catch(err => {
            console.error('Failed to load tickets:', err)
            ElMessage.error('Failed to load tickets')
        })
        .finally(() => {
            loading.value = false
        })
}

onMounted(loadTickets)
</script>

<template>
    <el-dialog title="My Tickets" width="750px" :model-value="modelValue"
        @update:model-value="emit('update:modelValue', $event)">
        <el-table :data="tickets" v-loading="loading" style="width: 100%" stripe>
            <el-table-column prop="movieTitle" label="Movie" />
            <el-table-column prop="cinemaName" label="Cinema" width="100" />
            <el-table-column prop="hallName" label="Hall" width="100" />
            <el-table-column prop="startTime" label="Start Time" width="160">
                <template #default="{ row }">
                    {{ new Date(row.startTime).toLocaleString() }}
                </template>
            </el-table-column>
            <el-table-column prop="endTime" label="End Time" width="160">
                <template #default="{ row }">
                    {{ new Date(row.endTime).toLocaleString() }}
                </template>
            </el-table-column>
        </el-table>

        <template #footer>
            <el-button @click="emit('update:modelValue', false)">Close</el-button>
        </template>
    </el-dialog>
</template>

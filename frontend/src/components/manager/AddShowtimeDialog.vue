<script setup lang="ts">
import { ref, watch } from 'vue';
import { ElMessage, ElDialog, ElButton, ElForm, ElFormItem, ElInput, ElDatePicker, ElInputNumber } from 'element-plus';
import axios from 'axios';
import type { Showtime } from '../../type/api/showtime';
import type { Result } from '../../type/result';

const props = defineProps<{
    visible: boolean;
    mid: number;
}>();

const emit = defineEmits<{
    (e: 'update:visible', value: boolean): void;
    (e: 'success'): void;
}>();

const formRef = ref();
const showtime = ref<Showtime>({
    cinemaName: '',
    hallName: '',
    startTime: '',
    endTime: '',
    price: 0,
    totalSeats: 0,
    mid: props.mid
});

watch(() => props.visible, (val) => {
    if (val) {
        showtime.value = {
            cinemaName: '',
            hallName: '',
            startTime: '',
            endTime: '',
            price: 0,
            totalSeats: 0,
            mid: props.mid
        };
    }
});

function submit() {
    formRef.value?.validate((valid: boolean) => {
        if (!valid) return;

        const api = '/api/showtime/add_showtime';

        console.log(showtime.value.startTime, showtime.value.endTime);

        axios.post<Result<null>>(api, {
            ...showtime.value,
            startTime: (showtime.value.startTime as Date).toJSON().slice(0, -1),
            endTime: (showtime.value.endTime as Date).toJSON().slice(0, -1)
        })
            .then(res => {
                if (res.data.ok) {
                    ElMessage.success('Showtime added!');
                    emit('success');
                    emit('update:visible', false);
                } else {
                    ElMessage.error(res.data.error);
                }
            })
            .catch(err => {
                console.error(err);
                ElMessage.error('Request failed');
            });
    });
}
</script>

<template>
    <el-dialog :title="'Add Showtime'" :model-value="visible" @close="emit('update:visible', false)" width="600px">
        <el-form :model="showtime" label-width="100px" ref="formRef">
            <el-form-item label="Cinema" prop="cinemaName" required>
                <el-input v-model="showtime.cinemaName" />
            </el-form-item>
            <el-form-item label="Hall" prop="hallName" required>
                <el-input v-model="showtime.hallName" />
            </el-form-item>
            <el-form-item label="Start Time" prop="startTime" required>
                <el-date-picker v-model="showtime.startTime" type="datetime" />
            </el-form-item>
            <el-form-item label="End Time" prop="endTime" required>
                <el-date-picker v-model="showtime.endTime" type="datetime" />
            </el-form-item>
            <el-form-item label="Price" prop="price" required>
                <el-input-number v-model="showtime.price" :min="0" />
            </el-form-item>
            <el-form-item label="Total Seats" prop="totalSeats" required>
                <el-input-number v-model="showtime.totalSeats" :min="1" />
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button @click="emit('update:visible', false)">Cancel</el-button>
            <el-button type="primary" @click="submit">
                {{ 'Add' }}
            </el-button>
        </template>
    </el-dialog>
</template>

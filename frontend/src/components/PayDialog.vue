<script setup lang="ts">
import { ref, watch } from 'vue'
import axios from 'axios'
import { ElMessage, ElDialog, ElButton } from 'element-plus'
import type { Result } from '../type/result'

const props = defineProps<{
    sid: number
    modelValue: boolean
}>()

const emit = defineEmits(['update:modelValue'])

const dialogVisible = ref(props.modelValue)
watch(() => props.modelValue, val => dialogVisible.value = val)
watch(dialogVisible, val => emit('update:modelValue', val))

function pay() {
    axios.get<Result<null>>(`/api/ticket/buy_ticket?sid=${props.sid}`)
        .then(res => {
            if (res.data.ok) {
                ElMessage.success('Payment successful!')
                dialogVisible.value = false
                window.location.reload()
            } else {
                ElMessage.error(res.data.error || 'Payment failed.')
            }
        })
        .catch(err => {
            console.error(err)
            ElMessage.error('An error occurred during payment.')
        })
}
</script>

<template>
    <el-dialog v-model="dialogVisible" title="Confirm Booking" width="400px">
        <p>Are you sure you want to book this ticket?</p>
        <template #footer>
            <el-button @click="dialogVisible = false">Cancel</el-button>
            <el-button type="primary" @click="pay">Pay</el-button>
        </template>
    </el-dialog>
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue';
import { ElMessage, ElDialog, ElButton, ElForm, ElFormItem, ElInput } from 'element-plus';
import axios from 'axios';
import type { Result } from '../../type/result';
import type { MovieDetail } from '../../type/api/movie';

const props = defineProps<{
  visible: boolean;
  movie?: MovieDetail | null; // 如果有值，则为编辑模式
}>();

const emit = defineEmits<{
  (e: 'update:visible', value: boolean): void;
  (e: 'success'): void;
}>();

const form = ref({
  title: '',
  introduction: ''
});

const formRef = ref();
const isEditMode = ref(false);

watch(() => props.visible, (val) => {
  if (val) {
    if (props.movie) {
      form.value = {
        title: props.movie.title,
        introduction: props.movie.introduction,
      };
      isEditMode.value = true;
    } else {
      form.value = {
        title: '',
        introduction: ''
      };
      isEditMode.value = false;
    }
  }
});

function submit() {
  formRef.value?.validate((valid: boolean) => {
    if (!valid) return;

    const api = isEditMode.value
      ? '/api/movie/edit_movie'
      : '/api/movie/add_movie';

    const payload = isEditMode.value
      ? { mid: props.movie?.mid, ...form.value }
      : form.value;

    axios.post<Result<null>>(api, payload)
      .then(res => {
        if (res.data.ok) {
          ElMessage.success(isEditMode.value ? 'Movie updated!' : 'Movie added!');
          emit('success');
          emit('update:visible', false);
        } else {
          ElMessage.error(res.data.error);
        }
      })
      .catch(err => {
        console.error(err);
        ElMessage.error('Request failed.');
      });
  });
}
</script>

<template>
  <el-dialog
    :title="isEditMode ? 'Edit Movie' : 'Add Movie'"
    :model-value="visible"
    @close="emit('update:visible', false)"
    width="500px"
  >
    <el-form :model="form" label-width="100px" ref="formRef">
      <el-form-item label="Title" prop="title" required>
        <el-input v-model="form.title" />
      </el-form-item>
      <el-form-item label="Introduction" prop="introduction">
        <el-input type="textarea" v-model="form.introduction" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="emit('update:visible', false)">Cancel</el-button>
      <el-button type="primary" @click="submit">
        {{ isEditMode ? 'Update' : 'Add' }}
      </el-button>
    </template>
  </el-dialog>
</template>

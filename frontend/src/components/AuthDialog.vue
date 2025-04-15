<script setup lang="ts">
import { ref } from "vue";
import axios from "axios";
import {
    ElDialog,
    ElTabs,
    ElTabPane,
    ElForm,
    ElFormItem,
    ElInput,
    ElButton,
    ElMessage,
    type FormInstance,
    type FormRules
} from "element-plus";
import { type Result } from "../type/result";

interface LoginForm {
    username: string;
    password: string;
}

interface SignupForm extends LoginForm {
    confirmPassword: string;
}

const props = defineProps<{
    dialogVisible: boolean;
}>();
const emit = defineEmits<{
    (e: 'update:dialogVisible', value: boolean): void;
}>();

const activeTab = ref("login");

const loginForm = ref<LoginForm>({
    username: "",
    password: ""
});

const signupForm = ref<SignupForm>({
    username: "",
    password: "",
    confirmPassword: ""
});

const loginRules: FormRules = {
    username: [{ required: true, message: "Username is required", trigger: "blur" }],
    password: [{ required: true, message: "Password is required", trigger: "blur" }]
};

const signupRules: FormRules = {
    username: [{ required: true, message: "Username is required", trigger: "blur" }],
    password: [{ required: true, message: "Password is required", trigger: "blur" }],
    confirmPassword: [
        { required: true, message: "Please confirm your password", trigger: "blur" },
        {
            validator: (_rule, value, callback) => {
                if (value !== signupForm.value.password) {
                    callback(new Error("Passwords do not match"));
                } else {
                    callback();
                }
            },
            trigger: "blur"
        }
    ]
};

const loginFormRef = ref<FormInstance>();
const signupFormRef = ref<FormInstance>();

const handleLogin = async () => {
    if (!loginFormRef.value) return;
    await loginFormRef.value.validate((valid) => {
        if (!valid) return;
        axios.post<Result<null>>("/api/login", {
            username: loginForm.value.username,
            password: loginForm.value.password
        }).then((response) => {
            if (response.data.ok === true) {
                ElMessage.success("Login successful");
                emit('update:dialogVisible', false);
                window.location.reload();
            } else {
                ElMessage.error("Login failed: " + response.data.error);
            }
        }).catch((error) => {
            console.error("Login error:", error);
            ElMessage.error("An error occurred during login. Please try again.");
        });
    });
};

const handleSignup = async () => {
    if (!signupFormRef.value) return;
    await signupFormRef.value.validate((valid) => {
        if (!valid) return;
        axios.post<Result<null>>("/api/signup", {
            username: signupForm.value.username,
            password: signupForm.value.password
        }).then((response) => {
            if (response.data.ok === true) {
                ElMessage.success("Signup successful");
                emit('update:dialogVisible', false);
                window.location.reload();
            } else {
                ElMessage.error("Signup failed: " + response.data.error);
            }
        }).catch((error) => {
            console.error("Signup error:", error);
            ElMessage.error("An error occurred during signup. Please try again.");
        });
    });
};
</script>

<template>
    <el-dialog :model-value="props.dialogVisible" title="Log In / Sign Up" width="30%"
        @update:model-value="emit('update:dialogVisible', $event)">
        <el-tabs v-model="activeTab">
            <el-tab-pane label="Log In" name="login">
                <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef" label-width="100px">
                    <el-form-item label="Username" prop="username">
                        <el-input v-model="loginForm.username" />
                    </el-form-item>
                    <el-form-item label="Password" prop="password">
                        <el-input v-model="loginForm.password" type="password" />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="handleLogin">Log In</el-button>
                    </el-form-item>
                </el-form>
            </el-tab-pane>

            <el-tab-pane label="Sign Up" name="signup">
                <el-form :model="signupForm" :rules="signupRules" ref="signupFormRef" label-width="100px">
                    <el-form-item label="Username" prop="username">
                        <el-input v-model="signupForm.username" />
                    </el-form-item>
                    <el-form-item label="Password" prop="password">
                        <el-input v-model="signupForm.password" type="password" />
                    </el-form-item>
                    <el-form-item label="Confirm" prop="confirmPassword">
                        <el-input v-model="signupForm.confirmPassword" type="password" />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="handleSignup">Sign Up</el-button>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
        </el-tabs>
    </el-dialog>
</template>

<script setup lang="ts">
import { ref } from "vue";
import axios from "axios";
import type { Result } from "../../type/result";

let username = ref("");
let password = ref("");
let confirmPassword = ref("");

function signup() {
    if (password.value !== confirmPassword.value) {
        alert("Passwords do not match!");
        return;
    }
    axios.post<Result<null>>("/api/signup", {
        username: username.value,
        password: password.value,
    })
    .then((response) => {
        if(response.data.ok === true) {
            window.location.href = "/";
        } else {
            alert("Login failed: " + response.data.error);
        }
    })
    .catch((error) => {
        console.error("Login error:", error);
        alert("An error occurred during login. Please try again.");
    });
}
</script>

<template>
    <h1>Login</h1>
    <div>
        <label>username</label>
        <input type="text" v-model="username" placeholder="username" />
    </div>
    <div>
        <label>password</label>
        <input type="password" v-model="password" placeholder="password" />
    </div>
    <div>
        <label>confirm password</label>
        <input type="password" v-model="confirmPassword" placeholder="confirm password" />
    </div>
    <div>
        <button @click="signup">Login</button>
    </div>
</template>
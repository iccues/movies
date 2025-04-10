<script setup>
import { ref } from "vue";
import axios from "axios";

const username = ref("");
const password = ref("");

function login() {
    axios.post("/api/login", {
        username: username.value,
        password: password.value,
    })
    .then((response) => {
        if(response.data.status === "SUCCESS") {
            window.location.href = "/";
        } else {
            alert("Login failed: " + response.data.message);
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
        <button @click="login">Login</button>
    </div>
</template>
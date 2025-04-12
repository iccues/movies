<script setup lang="ts">
import { ref } from "vue";
import axios from "axios";

let username = ref(null);

axios.get("/api/user_info/username")
    .then((response) => {
        if (response.data.status === "SUCCESS") {
            username.value = response.data.data;
        }
    })
    .catch((error) => {
        console.error("Error fetching user info:", error);
        alert("An error occurred while fetching user info. Please try again.");
    });

</script>

<template>
    <div>
        <div v-if="username">
            <p>{{ username }}</p>
        </div>
        <div v-else>
            <a href="/pages/login.html">Log In</a>
            <a href="/pages/signup.html">Sign Up</a>
        </div>
    </div>
</template>

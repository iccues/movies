<script setup lang="ts">
import axios from 'axios';
import { type Result } from '../../type/result';

let sid = new URLSearchParams(window.location.search).get('sid');

function pay() {

    axios.get<Result<null>>(`/api/user_info/buy_ticket?sid=${sid}`)
        .then((res) => {
            console.log(res.data);
            if (res.data.ok === true) {
                alert('Payment successful!');
            } else {
                alert(res.data.error);
            }
        })
        .catch((err) => {
            console.error(err);
            alert('Payment failed!');
        })
}
</script>

<template>
    <button @click="pay">Pay</button>
</template>
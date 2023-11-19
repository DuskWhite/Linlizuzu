<script setup>
import Navigation from '../components/navigation.vue'
</script>
<template>
   <Navigation></Navigation>

</template>

<script>

   export default {
    setup(){
        let title = "首页";
        return{
            title,
        }        
    }

    };
</script>

<style scope>

</style>
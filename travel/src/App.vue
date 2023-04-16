<template>
  <router-view />
</template>
<script setup>
import { onMounted } from 'vue'
 
onMounted(() => {
  // document.body.style.setProperty('--el-color-primary', '#56AB2F');
  // document.body.style.setProperty('--el-color-primary-light-9', '#a6cdb0');
  // document.body.style.setProperty('--el-color-primary-light-3', '#a6cdb0');
})

</script>

<style lang="less">

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
*{
  padding: 0;
  margin: 0;
}

html,
body {
  padding: 0;
  margin: 0;
  height: 100vh;
}
ul{
  list-style: none;
}
a{
  text-decoration: none;
}
</style>

<template>
  <div class="index" clearfix>
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" background-color="#000000" 
    text-color="#ebebe0" active-text-color="#ff9900" @select="handleSelect">
        <el-menu-item index="1" style="display:inline"><router-link to="/index/schoolcard" style="text-decoration:none">校园验证审核</router-link></el-menu-item>
        <el-menu-item index="2" style="display:inline"><router-link to="/index/post" style="text-decoration:none">贴文审核</router-link></el-menu-item>
        <el-menu-item index="3" style="display:inline"><router-link to="/index/party" style="text-decoration:none">组局审核</router-link></el-menu-item>
        <el-menu-item index="4" style="display:inline">登出</el-menu-item>
    </el-menu>
    <div class = "Main">
      <router-view/>
    </div>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
    };
  },
  created() {},
  methods: {
    handleSelect(key, keyPath) {
    }
    }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.index{
  min-height: 100%;
}

.main{
  min-height: 100%;
}
    
</style>

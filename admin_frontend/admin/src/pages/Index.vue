<template>
  <div class="index" clearfix>
    <el-menu default-active="1" class="el-menu-demo" mode="horizontal" background-color="#000000" 
    text-color="#ebebe0" active-text-color="#ff9900" @select="handleSelect">
        <el-menu-item index="1" style="display:inline"><router-link to="/index/schoolcard" style="text-decoration:none">校园验证审核</router-link></el-menu-item>
        <el-menu-item index="2" style="display:inline"><router-link to="/index/post" style="text-decoration:none">贴文审核</router-link></el-menu-item>
        <el-menu-item index="3" style="display:inline"><router-link to="/index/party" style="text-decoration:none">组局审核</router-link></el-menu-item>
        <el-menu-item index="4" style="display:inline" class="layout-menu" @click="loginOut">登出</el-menu-item>
    </el-menu>
    <!-- 占位 -->
    <div class = "Main">
      <router-view/>
    </div>
    <!-- 占位 -->
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      activeIndex: "1"
    };
  },
  created() {
    this.$router.push("/index/schoolcard")
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key);
      console.log(keyPath);
    },
    loginOut(){
      let that = this;
      // console.log("登出");
      this.$axios.post('/logout')
            .then(function (response) {
              // console.log(response);
            })
            .catch(function (error) {
              console.log(error);
            });
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

.layout-menu{
  margin-left: 65%;
}
    
</style>

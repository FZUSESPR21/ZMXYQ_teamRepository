<template>
  <div class="page" clearfix>
    <div class="logoImage">    
      <el-image
      style="width: 20%;"
      :src="require('../assets/images/logo1.png')"
      fit="contain"></el-image>
    </div>
    <div class="main">
      <div class="login-wrap">
      <el-row type="flex" justify="center">
        <el-form ref="loginForm" :model="user" status-icon label-width="80px">
          <h3>修改密码</h3>
          <hr>
          <el-form-item prop="username" label="用户名">
            <el-input v-model="user.username" placeholder="请输入用户名" prefix-icon></el-input>
          </el-form-item>
          <el-form-item id="password" prop="oriPassword" label="原密码">
            <el-input v-model="user.oriPassword" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item id="password" prop="laterPassword" label="现密码">
            <el-input v-model="user.laterPassword" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <div style="padding-left: 80%"><span><router-link to="/login" ><span id="change-password">去登录</span></router-link></span></div>
          <el-form-item>
            <el-button type="primary" icon="el-icon-upload" @click="changePassword()">修改</el-button>
          </el-form-item>
        </el-form>
      </el-row>
    </div>
    </div>
    <div class="footer">
      <span style="text-align: center;display:block;">逐梦校友圈 版权所有</span>
      <span style="text-align: center;display:block;">Copyright 2021 All Rights Reserved</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      user: {
        username: "",
        oriPassword: "",
        laterPassword: ""
      }
    };
  },
  created() {},
  methods: {
    changePassword() {
      if (!this.user.username) {
        this.showMessageBox("请输入用户名！", "提示");
        return;
      }
      else if (!this.user.oriPassword || !this.user.laterPassword) {
        this.showMessageBox("请输入密码！", "提示");
        return;
      }
      else if(this.user.oriPassword !== this.user.laterPassword){
        this.showMessageBox("两次输入密码不匹配！", "提示");
      }
      else{
        let that = this;
        this.$axios.post('/changepsw',
        {
          adminId: that.user.username,
          oldPassword: that.user.oriPassword,
          newPassword: that.user.laterPassword
        })
          .then(function (response) {
            console.log(response);
            if(response.data.code == 0){
              that.$alert("修改密码成功，前往登陆！", "提示", {
                confirmButtonText: '确定',
                closeOnClickModal: false
              })
              .then(() => {
                that.$router.push("/login");
              })
              .catch(() => {
                ;
              });
            }
            else{
              this.showMessageBox("请检查账号、原密码是否填写正确！", "提示");
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    }
  }
};
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.page {
  padding:0px;
  margin:0px;
  position:absolute;
  top:0px;
  left:0px;
  width:100%;
  height:100%;
  border:hidden;
  background-color:rgb(214, 214, 194);
}


.main{
  position: relative;
  height: 60%;
  width: 100%;
  top: 10%;
  background: url("../assets/images/fzu1.jpg") no-repeat;
  background-size: 100%;
  /* padding-top: 5%; */
}

.footer{
  position: relative;
  height: 5%;
  top: 20%;
}

.login-wrap {
  position: relative;
  background-color: 	rgb(245, 245, 240, 0.8);
  top: 2%;
  width: 28%;
  height: 90%;
  /* margin-top: 20%; */
  margin-left: 60%;
  overflow: hidden;
  padding-top: 20px;
  line-height: 40px;
  border-style: inset;
}

.logoImage{
  position: relative;
  top: 5%;
  padding-left: 2%;
}

#password {
  margin-bottom: 5px;
}

#change-password{
  color: blue;
  text-decoration: underline;
}

h3 {
  color: #0babeab8;
  font-size: 24px;
}

hr {
  background-color: #444;
  margin: 20px auto;
}


a {
  text-decoration: none;
  color: #aaa;
  font-size: 15px;
}

a:hover {
  color: coral;
}
</style>


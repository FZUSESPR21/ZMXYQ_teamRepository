<template>
  <div class="change-password" clearfix>
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
.login {
  width: 100%;
  height: 100%;
  /*background: url("../assets/images/bg1.png") no-repeat;*/
  /*background-size: cover;*/
  /*overflow: hidden;*/
}
.login-wrap {
  /*background: url("../assets/images/login_bg.png") no-repeat;*/
  /*background-size: cover;*/
  width: 40%;
  height: 30%;
  margin: 5% auto;
  overflow: hidden;
  padding-top: 10px;
  line-height: 40px;
  border-style: inset;
}
#password {
  margin-top: 5px;
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

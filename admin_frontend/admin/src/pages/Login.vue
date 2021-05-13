<template>
  <div class="login" clearfix>
    <div class="login-wrap">
      <el-row type="flex" justify="center">
        <el-form ref="loginForm" :model="user"  status-icon label-width="80px">
          <h3>登录</h3>
          <hr>
          <el-form-item prop="username" label="用户名">
            <el-input v-model="user.username" placeholder="请输入用户名" prefix-icon></el-input>
          </el-form-item>
          <el-form-item id="password" prop="password" label="密码">
            <el-input v-model="user.password" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <div style="padding-left: 80%"><span><router-link to="/changepsw" ><span id="change-password">修改密码</span></router-link></span></div>
          <el-form-item>
            <el-button type="primary" icon="el-icon-upload" @click="doLogin()">登 录</el-button>
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
        password: ""
      }
    };
  },
  created() {},
  methods: {
    doLogin() {
      if (!this.user.username) {
        this.$message.error("用户名不能为空！");
        return;
      }
      else if (!this.user.password) {
        this.$message.error("密码不能为空！");
        return;
      }
      else
      {
        let that = this;
        this.$axios
          .post('/login',{
            adminId: that.user.username,
            password: that.user.password
          }
          )
          .then(function(response){
            console.log(response);
            if(response.status != 200){
              that.showMessageBox("网络错误，请稍后重试！", "提示"); 
            }
            else if(response.data.code == 1){
              that.showMessageBox("用户不存在！", "提示");
            }
            else if(response.data.code == 2){
              that.showMessageBox("密码错误！", "提示");
            }
            else{
              // sessionStorage.setItem('token',response.data.token);
              that.$router.push({name: 'Index'});  //跳转页面
            }
          })
          .catch(function (error) { // 请求失败处理
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
  margin: 9% auto;
  overflow: hidden;
  padding-top: 10px;
  line-height: 40px;
  border-style: inset;
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

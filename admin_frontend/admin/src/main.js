// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.config.productionTip = false
Vue.use(ElementUI);
axios.defaults.baseURL = "http://ccreater.top:61112/api/admin";
axios.defaults.withCredentials = true; // 允许携带cookie
Vue.prototype.$axios = axios;
// 定义全局函数，弹出弹出框
Vue.prototype.showMessageBox = function(message, title){
  this.$alert(message, title, {
    confirmButtonText: '确定',
    callback: action => {
      this.$message({
        type: 'info',
        message: `action: ${ action }`
      });
    }
  });
}


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

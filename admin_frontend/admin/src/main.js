// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App'
import router from './router'
import axios from 'axios'
import { Message } from "element-ui";
import VueAxios from 'vue-axios'

Vue.config.productionTip = false
Vue.use(ElementUI);
axios.defaults.baseURL = "http://ccreater.top:61112/api/admin";
axios.defaults.withCredentials = true; // 允许携带cookie
//设置未登录跳转
// http response 响应拦截器
axios.interceptors.response.use(response => {
  return response;
},error => {
  if (error.response) {
      switch (error.response.status) {
          // 返回401，清除token信息并跳转到登录页面
          case 401:
            Message({
              showClose: true,
              message: "尚未登录,请先登录!",
              type: "error"
            });
            router.push({path: "/login"});
      }
      // 返回接口返回的错误信息
      return Promise.reject(error.response.data);
  }
});

Vue.prototype.$axios = axios;
// 定义全局函数，弹出弹出框
Vue.prototype.showMessageBox = function(message, title){
  this.$alert(message, title, {
    confirmButtonText: '确定',
    closeOnClickModal: false
  });
};

Vue.prototype.formatDate = function(date) {
    var d = new Date(date);
    let month = '' + (d.getMonth() + 1);
    let day = '' + d.getDate();
    let year = d.getFullYear();
    let hour = '' + d.getHours();
    let minute = '' + d.getMinutes();
    let second = '' + d.getSeconds();


    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    if (hour.length < 2) hour = '0' + hour;
    if (minute.length < 2) minute = '0' + minute;
    if (second.length < 2) second = '0' + second;

    return [year, month, day].join('-') + " " + [hour, minute].join(':');
};


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

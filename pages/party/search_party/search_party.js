// pages/party/my_party/my_party.js
const app = getApp();
import {request} from "../../../utils/request"
const timeago = require("timeago.js");
import Toast from '@vant/weapp/toast/toast';
Page({
  data: {
    partyList: [],
    value: "",
    show: false,
    partyType:[
      "自习",
      "电影",
      "聚餐",
      "拼车",
      "拼单",
      "运动",
      "游戏",
      "旅行",
      "其他"
    ]
},

/*搜索按钮句柄函数*/
search:function(){
  if(this.data.value != null && this.data.value.length != 0){
    this.data.value = this.data.value.trim();
    this.getData(this.data.value);
  }
  else{
    Toast('搜索条件为空');
  }
},

/*获取数据函数（调用接口）*/
getData:function(searchContent){
  let that = this;
  let baseUrl = app.globalData.baseUrl;
  request({
    url:  baseUrl + '/api/party/search?massage=' + searchContent,
    Header: {
      "Content-Type": "application/json"
    },
    method:'POST',
    success:function(res)
    {
    console.log(searchContent);
    //  console.log(res);
     let partyList = res.data.data;
     if(partyList != null){
      for(let i = 0; i < partyList.length; i++){
        partyList[i].gmtCreate = timeago.format(new Date(partyList[i].gmtCreate),'zh_CN');
        if(partyList[i].username.length > 6){
          partyList[i].username = partyList[i].username.substr(0, 6) + "...";
        }
        if(partyList[i].description.length > 35){
          partyList[i].description = partyList[i].description.substr(0, 35) + "...";
        }
      }
      /*提示结果为空 */
      if(partyList.length == 0)
        Toast('搜索结果为空');
    }
    else{
      Toast('搜索结果为空');
    }
      that.setData({
       partyList    
     })
    },
    fail:function(res)
    {
      console.log(res);
    }
  });
}
})
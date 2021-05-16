// pages/party/my_party/my_party.js
const app = getApp();
import {request} from "../../../utils/request"
const timeago = require("timeago.js");
Page({
  data: {
    partyList: [],
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

   getData: function(){
    let that = this;
    let baseUrl = app.globalData.baseUrl;
    request({
      url:  baseUrl + '/api/party/myparty',
      method:'GET',
      success:function(res)
      {
       let partyList = res.data.data;
       console.log("获取我的组局信息");
       console.log(res);
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
          /*设置显示无信息标签的可见与不可见 */
          if(partyList.length == 0)
            that.setData({show: true});
       }
       else{
        that.setData({show: true});
       }
        that.setData({
         partyList    
       });
       console.log(partyList);
      },
      fail:function(res)
      {
        console.log("获取我的组局信息失败！")
        console.log(res);
      }
    });
   },

  onLoad: function (options) {
      this.getData();
  },
})
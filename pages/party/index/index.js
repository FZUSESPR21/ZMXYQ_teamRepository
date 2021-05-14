// pages/party/index/index.js

const app = getApp();
const timeago = require("timeago.js")
Page({

  /**
   * 页面的初始数据
   */
  data: {
    partyType:{
      0 : "自习",
      1 : "电影",
      2 : "聚餐",
      3 : "拼车",
      4 : "拼单",
      5 : "运动",
      6 : "游戏",
      7 : "旅行",
      8 : "其他"
    },
    option1: [
      { text: '所有组局', value: -2 },
      { text: '非空组局', value: -1 },
      { text: '自习', value: 0 },
      { text: '电影', value: 1 },
      { text: '聚餐', value: 2 },
      { text: '拼车', value: 3 },
      { text: '拼单', value: 4 },
      { text: '运动', value: 5 },
      { text: '游戏', value: 6 },
      { text: '旅行', value: 7 },
      { text: '其他', value: 8 },
    ],
    value1: -2,
    partyList: [],
    zIndex: -1
  },
  goToMyParty: function(){
    wx.navigateTo({
      url: '../my_party/my_party'
    })
  },
  createParty:function(){
    wx.navigateTo({
      url: '../create_party/createparty'
    })
  },

  searchParty:function(){
    wx.navigateTo({
      url: '../search_party/search_party'
    });
  },

  jjj:function(){
    console.log("jjj");
  },

  onChange: function ({detail}) {
      ;
      // 调用接口
   },

   onClose: function () {
    // console.log("关闭");
    this.setData({
      zIndex: -1
    });
   },

   onOpen: function () {
    //  console.log("打开");
    this.setData({
      zIndex: 2
    });
   },

    /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this;
    let baseUrl = app.globalData.baseUrl;
    wx.request({
      // url: app.globalData.baseUrl + "api/party-type/getparty",
      url:  baseUrl + '/api/party-type/getparty',
      method:'GET',
      data:{
        partyTypeID: 1
      },
      success:function(res)
      {
       let partyList = res.data.data;
        for(let i = 0; i < partyList.length; i++){
          partyList[i].gmtCreate = timeago.format(new Date(partyList[i].gmtCreate),'zh_CN');
        }
       console.log(res);
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
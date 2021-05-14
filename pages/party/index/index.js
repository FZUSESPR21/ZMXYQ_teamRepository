// pages/party/index/index.js

const app = getApp();
const timeago = require("timeago.js");
Page({

  /**
   * 页面的初始数据
   */
  data: {
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
    ],
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
    zIndex: -1,
    show: false
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

  onChange: function ({detail}) {
      this.getData(detail + 0);
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

   /*调用接口，获取数据函数，传入参数为组局的类型*/
   getData: function(typeID){
    let that = this;
    let baseUrl = app.globalData.baseUrl;
    wx.request({
      url:  baseUrl + '/api/party-type/getparty',
      method:'GET',
      data:{
        partyTypeID: typeID
      },
      success:function(res)
      {
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
        if(partyList.length == 0)
          that.setData({show: true});
        else
          that.setData({show: false});
      }
      else{
        that.setData({show: true});
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
   },
    /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getData(-2);
  }
})
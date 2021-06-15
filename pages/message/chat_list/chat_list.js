// pages/message/index/chat_list.js

import {request} from "../../../utils/request"
const timeago = require("timeago.js");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    history_list: [],
    url: '../../../static/icons/avatar_default.png'
  },

  handleTap: function(e) {
    let paramStr = Object.keys(e.currentTarget.dataset).map(function(key) {
      return key + '=' + e.currentTarget.dataset[key];
    }).join('&');
    wx.navigateTo({
      url: `../chat/chat?${paramStr}`
    })

  },

  /**
   * 生命周期函数--监听页面加载
   */

  onLoad: function (options) {
    
    // this.setData({
    //   history_list:[
    //     {
    //       dialogUserId:"111",
    //       iconUrl:"../../../static/icons/search.png",
    //       dialogUserName:"test2",
    //       latestMessage:"这是测试",
    //       message_count:666,
    //       latestTime:new Date().toDateString()
    //     },
    //     {
    //       dialogUserId:"222",
    //       iconUrl:"../../../static/icons/search.png",
    //       dialogUserName:"test2",
    //       latestMessage:"这是测试",
    //       message_count:666,
    //       latestTime:new Date().toDateString()
    //     },
    //   ]
    // })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this
    request({
      url:  getApp().globalData.baseUrl + '/api/message/chat/list',
        method:'POST',
        Headers: {
          'content-type': 'application/json'
        },
        data: {},
        success:function(res)
        {
          console.log(res)
          if(res.data.code==200){
            let historys = res.data.data
            historys.forEach(history => {
              history.latestTime = timeago.format(new Date(history.latestTime),'zh_CN');
            });
            that.setData({
              history_list:historys
            })
            
          }else{
            wx.showToast({ // 显示Toast
              title: '获取数据失败',
              icon: 'error',
              duration: 1500
            })
          }
        
        },
        fail:function(res)
        {
          console.log(res);
        }
      });
  },
  add_history_record(record){
    this.setData({
      ["history_list[" + this.data.history_list.length + "]"]: record
    })

  }
  ,

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    
    
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
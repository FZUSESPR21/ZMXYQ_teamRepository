// pages/party/index/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },
  goToMyParty: function(){
    wx.navigateTo({
      url: '../my_party/my_party'
    })
  },
  createparty:function(){
    wx.navigateTo({
      url: '../create_party/createparty.wxml'
    })
  }
})
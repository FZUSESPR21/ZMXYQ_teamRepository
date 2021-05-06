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
  createParty:function(){
    wx.navigateTo({
      url: '../create_party/createparty'
    })
  },

  searchParty:function(){
    console.log(1);
    wx.navigateTo({
      url: '../search_party/search_party'
    });
  },

  jjj:function(){
    console.log("jjj");
  }
})
// pages/my/index/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  goToMyPost(){
    wx.navigateTo({
      url: '../my_post/my_post'
    })
  },

  goToMyCollection(){
    wx.navigateTo({
      url: '../my_collection/my_collection'
    })
  },

  goToMyComment(){
    wx.navigateTo({
      url: '../my_comment/my_comment'
    })
  },

  goToTreeHole(){
    wx.navigateTo({
      url: '../tree_hole/tree_hole'
    })
  },

  goToMyInfo(){
    wx.navigateTo({
      url: '../my_info/my_info'
    })
  },

  goToBlacklist(){
    wx.navigateTo({
      url: '../blacklist/blacklist'
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    console.log(测试);
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

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

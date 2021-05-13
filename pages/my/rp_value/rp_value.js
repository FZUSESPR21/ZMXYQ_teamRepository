// pages/my/rp_value/rp_value.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    partyList: [
      {
        "partyID" : 123,
        "description": "晚上十点，玫瑰园，王者荣耀五黑，不见不散，带你上王者，我就是阿伟！",
        "publisher": {
          "username": "张三",
          "sex":"男"
        },
        "peopleCnt": 6,
        "nowPeopleCnt": 3,
        "partyType": "组局",
        "gmtCreate": "1h前"
      }],
    one: [
      {
        "partyID" : 123,
        "description": "晚上十点，玫瑰园，王者荣耀五黑，不见不散，带你上王者，我就是阿伟！",
        "publisher": {
          "username": "张三",
          "sex":"男"
        },
        "peopleCnt": 6,
        "nowPeopleCnt": 3,
        "partyType": "组局",
        "gmtCreate": "1h前"
      }]
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
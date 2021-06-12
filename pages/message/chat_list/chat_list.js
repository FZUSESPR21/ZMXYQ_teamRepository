// pages/message/index/chat_list.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list: [{
      id: 0,
      value: '同学甲'
    }, {
      id: 1,
      value: '同学乙'
    }, {
      id: 2,
      value: '同学丁'
    }],
    url: '../../../static/icons/avatar_default.png'
  },

  handleTap: function(e) {
    wx.navigateTo({
      url: '../chat/chat'
    })
  },

  setAvatarUrl: function(e) {
    wx.getUserProfile({
      desc: '获取用户头像等信息',
      success: res => {
        this.setData({
          url: res.userInfo.avatarUrl
        })
        console.log('url------' , res.userInfo.avatarUrl)
      },
      fail: err => {
        wx.showToast({
          title: '失败了，淦',
          icon: 'error'
        })
      }
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
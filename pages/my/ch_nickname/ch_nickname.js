// pages/my/ch_nickname/ch_nickname.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    nickname: '',
    currentId:0
  },

  //修改昵称
  updateNickname(){
    let that = this;
    let id = this.data.currentId - 0
    // console.log(id)
    wx.request({
      method: 'POST',
      url: `http://localhost:8088/api/user/data/update`,
      data: {
        username: that.data.nickname
      },
      success(res){
        // console.log(res)
        wx.navigateBack({
          delta:1
        })

      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      nickname:options.nickname,
      currentId:options.id - 0,
    })
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
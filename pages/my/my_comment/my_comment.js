// pages/my/my_comment/my_comment.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    postCommentsList:[],
    partyCommentsList:[],
  },


  getPostComments(){
    let that = this;
    wx.request({
      url:"http://localhost:8088/api/user/postcomment/list",
      success(res){
        console.log(res);
        if(res.data.code === 200){
          that.setData({
            postCommentsList:res.data.data
          })
        }
      }
    })
  },

  getPartyComments(){
    let that = this;
    wx.request({
      url:"http://localhost:8088/api/user/partycomment/list",
      success(res){
        console.log(res);
        if(res.data.code === 200){
          that.setData({
            partyCommentsList:res.data.data
          })
        }
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getPostComments()
    this.getPartyComments()
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
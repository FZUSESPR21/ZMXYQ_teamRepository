// pages/my/hole_detail/hole_detail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentId: '',
    content: '',
    fun:'',
    func:'',
  },

  //更新树洞
  update(){
    let that = this;
    let id = this.data.currentId - 0
    // console.log(id)
    wx.request({
      method: 'POST',
      url: `http://localhost:8088/api/user/treehole/update`,

      data: {
        id: that.data.currentId-0,
        message: that.data.content
      },
      success(res){
        wx.navigateBack({
          delta:1

        })
        // console.log(res)
      }
    })
  },

  //新建树洞
  create(){
    let that = this;
    wx.request({
      method: 'POST',
      url: `http://localhost:8088/api/user/treehole/new`,

      data:
        that.data.content,

      success(res){
        wx.navigateBack({
          delta:1
        })
        // console.log(res)
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // console.log(options.id)
    this.setData({
      currentId: options.id,
      content: options.content,
      fun:options.fun,
      func:options.func,
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
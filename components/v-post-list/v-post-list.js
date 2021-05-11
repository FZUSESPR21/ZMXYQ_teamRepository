// pages/post_list/post_list.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

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

  },
  goto_postdetail:function(param){
    wx.navigateTo({
      url: '/pages/post_detail/post_detail',
      })
  },
  like:function(param){
    wx.request({
      url: 'http://xx.com/api/alumnicycle/posts/like',
      method: 'POST',
      data: {
        postId:""
      },
      header: {
        'content-type':'application/x-www-form-urlencoded',
        'Accept': 'application/json'
      },
      success: function (res) {
        console.log(res.data)
        that.setData({
          "code": 0,//返回服务器结果代码
          "message":"",//返回服务器接口结果信息
          "data":""
        })
      }
    })
  },
  onTap: function (e) {
    // 获取按钮元素的坐标信息
    this.popover = this.selectComponent('#popover');
    var id = 'morebutton2';// 或者 e.target.id 获取点击元素的 ID 值
    this.createSelectorQuery().select('#' + id).boundingClientRect(res => {
      // 调用自定义组件 popover 中的 onDisplay 方法
      console.log(res);
      this.popover.onDisplay(res);
    }).exec();
  },
})
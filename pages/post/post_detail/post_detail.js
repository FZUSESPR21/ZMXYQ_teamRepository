// pages/post_detail/post_detail.js
import Notify from '../../../miniprogram_npm/@vant/weapp/notify/notify';
import Toast from '../../../miniprogram_npm/@vant/weapp/toast/toast';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    commentMessage:{},
    commentInputText:"",
    postId:0,
    showRewardBox:false,
    popularityNum:0,
    hasMark:false,
    hasLike:true,
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
    this.popover = this.selectComponent('#popover');
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
  onTap: function (e) {
    // 获取按钮元素的坐标信息
    var id = 'morebutton' // 或者 e.target.id 获取点击元素的 ID 值
    wx.createSelectorQuery().select('#' + id).boundingClientRect(res => {
      // 调用自定义组件 popover 中的 onDisplay 方法
      this.popover.onDisplay(res);
    }).exec();
  },
  getCommentBox:function(e)
  {
    this.setData({
      commentMessage:e.detail
    })
    console.log(this.data.commentMessage);
    console.log(10);
  },
  bindTextAreaBlur:function(e)
  {
    this.setData({
      commentInputText:e.detail.value
    })
    console.log(this.data.commentInputText)
  },
  sendComment:function(e)
  {
    wx.request({
      url: app.globalData.baseUrl+'/api/alumnicycle/party-comment/comment',
      method:"POST",
      data:{
        content:"",
        userId:this.data.userId,
        partyId:this.data.partyID,
        preId:this.commentPreId
      },
      success:function(res)
      {
        Notify({ type: 'success', message: '评论成功' });
      }

    })
  },
  postMark:function(e)
  {
   this.setData({
     hasMark:!this.data.hasMark
   })
  },
  postLike:function(e)
  {
    wx.request({
      url: 'http://xx.com/api/alumnicycle/posts/like',
      method:"POST",
      data:{
        postId:this.data.postId
      },
      success:function(e)
      {
        Toast('点赞成功');
      }
    })
  },
  postCollect:function(e)
  {
    wx.request({
      url: 'http://xx.com/api/alumnicycle/posts/collect',
      method:"POST",
      data:{
        postId:this.data.postId
      },
      success:function(e)
      {
        Notify({ type: 'success', message: '收藏成功' });
      }
    })
  },
  postReward:function(e)
  {
    wx.request({
      url: 'http://xx.com/api/alumnicycle/posts/reward',
      method:"POST",
      data:{
        postId:this.data.postId,
        amount:0
      }
    })
  },
  getRewardBox:function(e)
  {
    this.setData({
      showRewardBox:true
    })
  },
  onClose() {
    this.setData({ showRewardBox: false });
  },
})
// pages/my/my_post/my_post.js
import Dialog from "../../../miniprogram_npm/@vant/weapp/dialog/dialog";
const app = getApp();
const timeago = require("timeago.js");
import {request} from "../../../utils/request"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    show: false,
    actions: [
      { name: '删除帖子' },
    ],
    currentId:0,
    postList:[],
  },

  //获取帖子列表
  getPostList(){
    let that = this;
    let baseUrl = app.globalData.baseUrl;
    request({
      url: baseUrl + '/api/user/post/list',
      method:'GET',
      success(res){
        console.log(res);
        if(res.data.code === 200){
          let tempData = res.data.data.reverse();
          if(tempData!= null) {
            for (let i = 0; i < tempData.length; i++) {
              tempData[i].gmtCreate = timeago.format(new Date(tempData[i].gmtCreate), 'zh_CN');
            }
          }
          that.setData({
            postList:tempData,
          })
        }
      }
    })
  },

  //点击计时器
  handleTouchStart:function(e){
    this.startTime=e.timeStamp;
    //console.log(" startTime="+e.timeStamp);
  },

//touchend
  handleTouchEnd:function(e){
    this.endTime=e.timeStamp;
    //console.log(" endTime="+e.timeStamp);
  },

  //单击
  handleClick:function(e){
    //console.log("endTime-startTime="+(this.endTime-this.startTime));
    if(this.endTime-this.startTime<350){
      console.log("点击");
      let id = e.currentTarget.dataset.id;
      wx.navigateTo({
        url:`../../post/post_detail/post_detail?postId=${id}`
      })
    }
  },

  //长按
  handleLongPress:function(event){
//console.log("endTime-startTime="+(this.endTime-this.startTime));
    let id = event.currentTarget.dataset.id
    this.setData({
      show: true,
      currentId:id,
    });
    // console.log(this.data.currentId);
  },

  onClose() {
    this.setData({ show: false });
  },

  //删除我的帖子
  deleteMyPost(ID){
    let that = this;
    let id = this.data.currentId - 0
    let baseUrl = app.globalData.baseUrl;
    // console.log(id)
    request({
      url: baseUrl + '/api/user/post/deleted',
      method: 'POST',
      data:id,
      // header: {
      // 'content-type': 'application/x-www-form-urlencoded'
      // },
      success(res){
        // console.log(res)
        that.getPostList();
      }

    })
  },

  //删除帖子确认框
  deletePostDialog:function(e)
  {
    Dialog.confirm({
      message: '确定要删除帖子吗',
    })
        .then(() => {
          this.deleteMyPost();
        })
        .catch(() => {
          // on cancel
        });
  },

  onSelect(event){
    this.deletePostDialog();
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getPostList();
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
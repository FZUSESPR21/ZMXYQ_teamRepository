import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
const app = getApp();
import {request} from "../../../utils/request"
const timeago = require("timeago.js");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    show: false,
    actions: [
      { name: '取消收藏' },
    ],
    currentId:0,
    collectionList:[],
    collectionReverse:[],
  },

//touchstart
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
      let pid = e.currentTarget.dataset.pid;
      console.log(pid)
      wx.navigateTo({
        url:`../../post/post_detail/post_detail?postId=${pid}`
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

  getCollectionList(){
    let that = this;
    let baseUrl = app.globalData.baseUrl;
    request({
      url: baseUrl + '/api/user/collect/list',
      method: 'GET',
      success(res){
        console.log(res);
        if(res.data.code === 200){
          let tempData = res.data.data.reverse();
          if(tempData!= null) {
            for (let i = 0; i < tempData.length; i++) {
              tempData[i].postTime = timeago.format(new Date(tempData[i].postTime), 'zh_CN');
            }
            for(let imageIndex = 0; imageIndex < tempData.length; imageIndex++){
              tempData[imageIndex].iconUrl = baseUrl + "/static/" + tempData[imageIndex].iconUrl;
            }
          }
          that.setData({
            collectionList:tempData,
          })
          console.log(tempData);
        }
      }
    })
  },


  getPostList(){
    let that = this;
    let baseUrl = app.globalData.baseUrl;
    let jsonStr = '{"pageSize": 10,"pageNum": 1}';
    let jsonValue = JSON.parse(jsonStr);
    request({
      url: baseUrl + '/api/posts/all',
      method: 'POST',
      data:jsonValue,
      Headers: {
        'content-type': 'application/json'
      },
      success(res){
        console.log(res);
        // that.setData({
        //   collectionList:res.data.data.reverse(),
        // })
      }
    })
  },

  onClose() {
    this.setData({ show: false });
  },

  deleteCollection(ID){
    let that = this;
    let id = this.data.currentId - 0
    let baseUrl = app.globalData.baseUrl;
    console.log(id)
    request({
      url: baseUrl + '/api/user/collect/deleted',
      method: 'POST',
      data:id,
      // header: {
      // 'content-type': 'application/x-www-form-urlencoded'
      // },
      success(res){
        console.log(res)
        that.getCollectionList();
      }

    })
  },

  deleteCollectionDialog:function(e)
  {
    Dialog.confirm({
      message: '确定要取消收藏吗',
    })
        .then(() => {
          this.deleteCollection();
        })
        .catch(() => {
          // on cancel
        });
  },

  onSelect(event){
    this.deleteCollectionDialog();
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getCollectionList();
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
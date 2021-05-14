import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
// import { format } from '../../../miniprogram_npm/timeago.js';

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
    wx.request({
      url:"http://localhost:8088/api/user/collect/list",
      success(res){
        console.log(res);
        that.setData({
          collectionList:res.data.data,
        })
      }
    })
  },

  onClose() {
    this.setData({ show: false });
  },

  deleteCollection(ID){
    let that = this;
    let id = this.data.currentId - 0
    console.log(id)
    wx.request({
      method: 'POST',
      url: 'http://localhost:8088/api/user/collect/deleted',

      data:id,
      // header: {
      // 'content-type': 'application/x-www-form-urlencoded'
      // },
      success(res){
        // console.log(res)
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
          // wx.request({
          //   url: 'http://xx.com/api/alumnicycle/party/delete',
          //   method:"POST",
          //   data:{
          //     partyId:0
          //   },
          //   success:function(res)
          //   {
          //     console.log(res);
          //   }
          // })
          // // on confirm
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
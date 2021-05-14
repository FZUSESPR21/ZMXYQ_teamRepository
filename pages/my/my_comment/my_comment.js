// pages/my/my_comment/my_comment.js
import Dialog from "../../../miniprogram_npm/@vant/weapp/dialog/dialog";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    show: false,
    actions: [
      { name: '删除评论' },
    ],
    currentId:0,
    postCommentsList:[],
    partyCommentsList:[],
    topic:"",
  },

  //获取贴文评论
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

  //获取组局评论
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
    }
  },

  //长按
  handleLongPress:function(event){
//console.log("endTime-startTime="+(this.endTime-this.startTime));
    let id = event.currentTarget.dataset.id;
    let topic = event.currentTarget.dataset.topic;

    this.setData({
      show: true,
      currentId:id,
      topic:topic,
    });
    // console.log(this.data.currentId);
  },

  //关闭dialog
  onClose() {
    this.setData({ show: false });
  },

  onSelect(event){
    this.deleteCommentDialog();
  },

  //删除校友圈评论
  deletePostComment(ID){
    let that = this;
    let id = this.data.currentId - 0
    // console.log(id)
    wx.request({
      method: 'POST',
      url: 'http://localhost:8088/api/user/postcomment/deleted',

      data:id,
      // header: {
      // 'content-type': 'application/x-www-form-urlencoded'
      // },
      success(res){
        // console.log(res)
        that.getPostComments();
      }

    })
  },

  //删除组局评论
  deletePartyComment(ID){
    let that = this;
    let id = this.data.currentId - 0
    // console.log(id)
    wx.request({
      method: 'POST',
      url: 'http://localhost:8088/api/user/partycomment/deleted',

      data:id,
      // header: {
      // 'content-type': 'application/x-www-form-urlencoded'
      // },
      success(res){
        // console.log(res)
        that.getPartyComments();
      }

    })
  },

  //删除帖子确认框
  deleteCommentDialog:function(e)
  {
    let that = this;
    let topic = this.data.topic;
    // console.log(topic);
    Dialog.confirm({
      message: '确定要删除评论吗',
    })
        .then(() => {
          if (topic === "post"){
            this.deletePostComment();
          }
          else if(topic === "party"){
            this.deletePartyComment();
          }
          // // on confirm
        })
        .catch(() => {
          // on cancel
        });
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
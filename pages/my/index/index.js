import {request} from "../../../utils/request"
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    fileList:[],
    imgUrls:[],
    nickname: "",
    headSrc: "",
    certSrc:"",
    currentId: "",
    school: "",
    rpValue:"",
    postCount:-1,
    collectionCount:-1,
    commentCount:-1,
  },

  getUserInfo(){
    let that = this;
    let baseUrl = app.globalData.baseUrl;
    request({
      url: baseUrl + '/api/user/data/select',
      success(res){
        // console.log(res);
        if (res.data.code === 200){
          let tempData = res.data.data;
          tempData.user.userIconUrl = baseUrl + "/static/" + tempData.user.userIconUrl;
          that.setData({
            UserInfo:res.data.data.user,
            headSrc:tempData.user.userIconUrl,
            certSrc:res.data.data.user.certificateImageUrl,
            nickname:res.data.data.user.username,
            currentId:res.data.data.user.id,
            school:res.data.data.user.school,
            rpValue:res.data.data.user.rpValue,
            postCount:res.data.data.numberList.postNumber - 0,
            collectionCount:res.data.data.numberList.collectionNumber - 0,
            commentCount:res.data.data.numberList.postCommentNumber + res.data.data.numberList.partyCommentNumber - 0,
          })
        }
        // console.log(that.data.UserInfo.username);
      }
    })
  },

  goToMyPost(){
    wx.navigateTo({
      url: '../my_post/my_post'
    })
  },

  goToRegulation(){
    wx.navigateTo({
      url: '../norm/norm'
    })
  },

  goToMyCollection(){
    wx.navigateTo({
      url: '../my_collection/my_collection'
    })
  },

  goToMyComment(){
    wx.navigateTo({
      url: '../my_comment/my_comment'
    })
  },

  goToTreeHole(){
    wx.navigateTo({
      url: '../tree_hole/tree_hole'
    })
  },

  goToCertification(){
    wx.navigateTo({
      url: '../certification/certification'
    })
  },

  goToMyInfo(){
    wx.navigateTo({
      url: '../my_info/my_info'
    })
  },

  goToBlacklist(){
    wx.navigateTo({
      url: '../blacklist/blacklist'
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getUserInfo();
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
    this.getUserInfo();
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

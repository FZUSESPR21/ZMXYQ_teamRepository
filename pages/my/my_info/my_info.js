const app = getApp();
const timeago = require("timeago.js");
import {request} from "../../../utils/request"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    UserInfo:[],
    fileList: [],
    imgUrls:[],
    headSrc:"",
    nickname:"",
    currentId:0
  },

  //获取用户资料
  getUserInfo(){
    let that = this;
    // console.log(getApp().globalData.userInfo)
    let baseUrl = app.globalData.baseUrl;
    request({
      url: baseUrl + '/api/user/data/select',
      method:'GET',
      success(res){
        console.log(res);
        if (res.data.code === 200){
          let tempData = res.data.data;
          // wconsole.log(tempData);
          tempData.user.userIconUrl = baseUrl + "/static/" + tempData.user.userIconUrl;
          that.setData({
            UserInfo:res.data.data.user,
            headSrc:tempData.user.userIconUrl,
            nickname:res.data.data.user.username,
            currentId:res.data.data.user.id
          })

        }
        // console.log(that.data.UserInfo.username);
      }
    })
  },

  changeNickname(){
    let that = this;
    let nickname = this.data.nickname;
    let id = this.data.id;
    wx.navigateTo({
      url: `../ch_nickname/ch_nickname?id=${id}&nickname=${nickname}`
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

  },

  afterRead: function (event) {
    const that = this;
    console.log(event.detail.file.url);
    this.setData({
      fileList: [event.detail.file],
      // headSrc:event.detail.file
    });
    console.log(this.data.fileList)

    this.submitImage();
  },

  submitImage:function (e) {
    let that=this;
    const file = that.data.fileList;
    let promiseArr=[];
    let imgServerUrls=new Array();
    console.log(that.data.fileList);
    file.forEach(function (e) {
      var FSM = wx.getFileSystemManager();
      let imageType=getApp().getImageType(e.url);
      promiseArr.push(
          new Promise(function (resolve,reject) {
            FSM.readFile({
              filePath: e.url,
              encoding: "base64",
              success: function (data) {
                wx.request({
                  url: app.globalData.baseUrl+'/api/posts/imgupload',
                  method: "POST",
                  data: {
                    base64Str: imageType + data.data,
                    filename: "111"
                  },
                  success: function (res) {
                    console.log(res);
                    console.log("上传图片成功");
                    if(res.data.code==200)
                    {
                      return resolve(res);
                    }
                    else{
                      return reject(res.data.message);
                    }
                  },
                  fail: function (e) {
                    console.log(e);
                    console.log("上传图片失败");
                    return reject(e)
                  },
                  complete: function (complete) {
                    return complete;
                  }
                })
              }
            });
          })
      )
    })
    Promise.all(promiseArr).then(function (values) {
      console.log(values);
      values.forEach(function (e) {
        console.log(e);
        imgServerUrls.push(e.data.data)
      })
      // console.log(imgServerUrls);
      that.setData({
        imgUrls:imgServerUrls,
      })
      let baseUrl = app.globalData.baseUrl;
      request({
        url: baseUrl + '/api/user/data/update',
        method: 'POST',
        data: {
          userIconUrl:that.data.imgUrls[0],
        },
        success(res){
          that.getUserInfo();
          console.log(res);
        }
      })
      console.log(that.data.imgUrls[0]);
    }).catch(
        reason=>{
          console.log(reason)
        }
    )
  },
})


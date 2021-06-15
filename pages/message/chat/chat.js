const app = getApp();
var inputVal = '';
var msgList = [];
var windowWidth = wx.getSystemInfoSync().windowWidth;
var windowHeight = wx.getSystemInfoSync().windowHeight;
var keyHeight = 0;
import {request} from "../../../utils/request"


/**
 * 初始化数据
 */


/**
 * 计算msg总高度
 */
// function calScrollHeight(that, keyHeight) {
//   var query = wx.createSelectorQuery();
//   query.select('.scrollMsg').boundingClientRect(function(rect) {
//   }).exec();
// }

Page({

  /**
   * 页面的初始数据
   */
  data: {
    scrollHeight: '100vh',
    inputBottom: 0,
    myicon:"",
    targeticon:"",
    waitTimes:0,//超时变量
    timeList:[]//定时器列表
  },
  userid:"",

  /**
   * 生命周期函数--监听页面加载
   */
  onUnload:function(){
    clearTimeout()
  },
  processHistorys:function(res){

    if(res.data.code==200){
      // console.log(res.data.data)
      msgList = res.data.data
      this.setData({
        msgList:msgList.slice().reverse(),
      })

    }else{
      wx.showToast({ // 显示Toast
        title: `获取数据失败:`+res.data.message==""?"未知错误":res.data.message,
        icon: 'warn',
        duration: 1500
      })
    }
  },
  loadHistorys:function(page){
    let that = this
    request({
      url:  getApp().globalData.baseUrl + '/api/message/chat/receive',
        method:'POST',
        Headers: {'content-type': 'application/json'},
        data: {userIdFrom:parseInt(that.userid),pageNum:page,pageSize:50},
        success:that.processHistorys,
        fail:(res)=>{console.log(res)}
      });

  },
  onLoad: function(options) {
    console.log(options)    
    this.userid = options.userid
    
    this.loadHistorys(0)
    console.log(app.globalData)
    this.setData({
      myicon:app.globalData.userInfo.data.userIconUrl,
      targeticon:options.iconurl
    });
      console.log(app.globalData.userInfo.avatarUrl,options.iconurl)
      this.startWaiting()
    

    },
    myUpdate() {
      var time = setTimeout(this.myUpdate, 1000)
      this.data.timeList.push(time) // 存储定时器
      this.loadHistorys(0)

  },
  startWaiting() {
      setTimeout(this.myUpdate, 1000)
  },
  stopWaiting() {
      console.log("clear all")
      for (var i = 0; i < this.data.timeList.length; i++) {
          clearTimeout(this.data.timeList[i]); //清除了所有定时器
      }
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.getScollBottom()
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },
  onHide:function(){
    console.log("oh fuck")
    clearTimeout()
    
  },

  

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 获取聚焦
   */
  focus: function(e) {
    keyHeight = e.detail.height;
    this.setData({
      scrollHeight: (windowHeight - keyHeight) + 'px'
    });
    this.getScollBottom()
    //计算msg高度
    // calScrollHeight(this, keyHeight);

  },

  //失去聚焦(软键盘消失)
  blur: function(e) {
    this.setData({
      scrollHeight: '100vh',
      inputBottom: 0
    })
    this.getScollBottom()

  },
  getScollBottom() {
    this.setData({
      toView: 'msg-' + (msgList.length - 1)
    })
  },

  /**
   * 发送点击监听
   */
  processSend:function(res){
    console.log(res)
    if(res.data.code == 200){
//do nothing
      console.log("success")
    }else{
      msgList.pop()
      this.setData({msgList:msgList.slice().reverse()})
      wx.showToast({ // 显示Toast
        title: res.message,
        icon: 'error',
        duration: 1500
      })

    }
  },
  sendClick: function(e) {
    msgList = [{
      isFromMe: 1,
      content: e.detail.value.value
    }].concat(msgList)
    inputVal = '';
    this.setData({
      msgList:msgList.slice().reverse(),
      inputVal
    });
    let that = this
    this.getScollBottom()
    request({
      url:  getApp().globalData.baseUrl + '/api/message/chat/send',
        method:'POST',
        Headers: {'content-type': 'application/json'},
        data: {
          userIdTo:parseInt(that.userid),
          content: e.detail.value.value
        },
        success:that.processSend,
        fail:(res)=>{console.log(res)}
      });


  },

  /**
   * 退回上一页
   */
  toBackClick: function() {
    clearTimeout(time)
    wx.navigateBack({})
    
  }

})

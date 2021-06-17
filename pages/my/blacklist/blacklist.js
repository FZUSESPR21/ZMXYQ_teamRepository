// pages/my/blacklist/blacklist.js
import Dialog from "../../../miniprogram_npm/@vant/weapp/dialog/dialog";

const app = getApp();
import {request} from "../../../utils/request";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    blackList:[],
    currentId:0,
  },

  getBlackList() {
    let that = this;
    let baseUrl = app.globalData.baseUrl;
    request({
      url: baseUrl + '/api/user/black/list',
      method: 'GET',
      success(res) {
        if (res.data.code === 200){
          let tempData = res.data.data.reverse();
          if(tempData != null){
            for(let imageIndex = 0; imageIndex < tempData.length; imageIndex++){
              tempData[imageIndex].iconUrl = baseUrl + "/static/" + tempData[imageIndex].iconUrl;
            }
          }
          that.setData({
            blackList: tempData,
          })
        }
      }
    })
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
  handleClick:function(event){
    //console.log("endTime-startTime="+(this.endTime-this.startTime));
    let id = event.currentTarget.dataset.id
    if(this.endTime-this.startTime<350){
      this.setData({
        currentId:id,
      });
      this.showDialog();
    }
  },

  //长按
  handleLongPress:function(event){
//console.log("endTime-startTime="+(this.endTime-this.startTime));
//     let id = event.currentTarget.dataset.id
//     this.setData({
//       show: true,
//       currentId:id,
//     });
    // console.log(this.data.currentId);
  },

  deleteBlack(ID){
    let that = this;
    let id = this.data.currentId - 0;
    let baseUrl = app.globalData.baseUrl;
    // console.log(id)
    request({
      url: baseUrl + '/api/user/black/deleted',
      method: 'POST',
      data:id,
      // header: {
      // 'content-type': 'application/x-www-form-urlencoded'
      // },
      success(res){
        // console.log(res)
        that.getBlackList();
      }
    })
  },

  showDialog:function(e)
  {
    Dialog.confirm({
      message: '确定移出黑名单吗？',
    })
        .then(() => {
          this.deleteBlack();
        })
        .catch(() => {
          // on cancel
        });
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getBlackList()
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
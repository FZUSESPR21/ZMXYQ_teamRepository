// pages/party/create_party/createparty.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    option1: [
      { text: '全部商品', value: 0 },
      { text: '新款商品', value: 1 },
      { text: '活动商品', value: 2 },
    ],
    themeArray:["重庆分店","东莞南城分店","东莞总店","东莞总店","东莞总店"],
    value1: 0,
    select: false,
    tihuoWay: '门店自提',
    memberNum:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  bindShowMsg() {
    this.setData({
        select:!this.data.select
    })
},
  addmemberOp(e)
  {
    if(this.data.memberNum<12)
    {
      this.setData({
        memberNum:this.data.memberNum+1
      })
    }
   
  }
  ,
  delmemberOp(e)
  {
    if(this.data.memberNum>0)
    {
      this.setData({
        memberNum:this.data.memberNum-1
      })
    }
  }
  ,
  mySelect(e) {
   var name = e.currentTarget.dataset.name
   this.setData({
       tihuoWay: name,
       select: false
   })
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
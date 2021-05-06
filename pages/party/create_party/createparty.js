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
    memberNum:0,
    fileList: [],
    partyDetailContent:"1111",
    buttonOperation:"创建组局(消耗50人品)"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // console.log(options.partyDetailContent)
    this.setData({
      partyDetailContent:options.partyDetailContent,
      memberNum:options.partyMemberCnt,
      buttonOperation:options.operation
    })
  },
  bindShowMsg() {
    this.setData({
        select:!this.data.select
    })
},

afterRead(event) {
  const { file } = event.detail;
  // 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
  wx.uploadFile({
    url: 'https://example.weixin.qq.com/upload', // 仅为示例，非真实的接口地址
    filePath: file.url,
    name: 'file',
    formData: { user: 'test' },
    success(res) {
      // 上传完成需要更新 fileList
      const { fileList = [] } = this.data;
      fileList.push({ ...file, url: res.data });
      this.setData({ fileList });
    },
  });
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
createparty:function(e){
  
  //  wx.request({
  //    url: 'http://xx.com/api//alumnicycle/party/add',
  //    method:"POST",
  //    data:{
  //      userId:0,
  //      description:"",
  //      images:[],
  //      peopleCnt:0,
  //      partyTypeID:0
  //    },
  //    success(res)
  //    {
  //     Notify({ type: 'success', message: '创建拼局成功' });
  //    },

  //  })
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
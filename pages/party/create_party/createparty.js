// pages/party/create_party/createparty.js
import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    option1: [{
        text: '全部商品',
        value: 0
      },
      {
        text: '新款商品',
        value: 1
      },
      {
        text: '活动商品',
        value: 2
      },
    ],
    themeArray: ["重庆分店", "东莞南城分店", "东莞总店", "东莞总店", "东莞总店"],
    value1: 0,
    select: false,
    memNum:1,
    tihuoWay: '门店自提',
    fileList: [{
        url: 'http://iph.href.lu/60x60?text=default',
        name: '图片2',
        isImage: true,
        deletable: true,
      },


    ],
    base64fileList: [],
    partyDetailContent: "1111",
    buttonOperation: "创建组局(消耗50人品)"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // console.log(options.partyDetailContent)
    this.setData({
      partyDetailContent: options.partyDetailContent,
      memberNum: options.partyMemberCnt,
      buttonOperation: options.operation
    })
  },
  bindShowMsg: function () {
    this.setData({
      select: !this.data.select
    })
  },

  afterRead: function (event) {
    const _this = this;
    console.log(event.detail.file[0].url);
    this.setData({
      fileList: _this.data.fileList.concat(event.detail.file)
    });

  },
  deleteImage: function (e) {
    const index = e.detail.index; //获取到点击要删除的图片的下标
    const deletImageList = this.data.fileList //用一个变量将本地的图片数组保存起来
    deletImageList.splice(index, 1) //删除下标为index的元素，splice的返回值是被删除的元素
    this.setData({
      fileList: deletImageList
    })
  },
  addmemberOp(e) {
    if (this.data.memNum < 12) {
      this.setData({
        memNum: this.data.memNum + 1
      })
    }

  },
  delmemberOp(e) {
    if (this.data.memNum > 0) {
      this.setData({
        memNum: this.data.memNum - 1
      })
    }
  },
  mySelect(e) {
    var name = e.currentTarget.dataset.name
    this.setData({
      tihuoWay: name,
      select: false
    })
  },
  createParty: function (e) {
  if(this.data.partyDetailContent=="")//判断拼局内容是否为空
  {
    Dialog.alert({
      message: '活动详情不能为空',
    }).then(() => {
      // on close
    });
  }
  else if(this.data.memNum==0)//判断拼局人数是否为空
  {
    Dialog.alert({
      message: '拼局人数不能为空',
    }).then(() => {
      // on close
    });
  }
  else{
    getApp().submitImage(this.data.fileList);
  }
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
// pages/party/create_party/createparty.js
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
    tihuoWay: '门店自提',
    memberNum: 0,
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
    console.log(event);
    this.setData({
      fileList: _this.data.fileList.concat(event.detail.file)
    });

  },
  getImageType: function (src) {
    let imageType = "";
    let srcArray = src.split('.');
    // console.log(srcArray);
    if (srcArray[srcArray.length - 1] == 'jpg' || srcArray[srcArray.length - 1] == 'jpeg') {
      imageType = "data:image/jpeg;base64,";
    } else if (srcArray[srcArray.length - 1] == 'png') {
      imageType = "data:image/png;base64,";
    } else if (srcArray[srcArray.length - 1] == 'gif') {
      imageType = "data:image/gif;base64,";
    }else if (srcArray[srcArray.length - 1] == 'ico') {
      imageType = "data:image/x-icon;base64,";
    }else if (srcArray[srcArray.length - 1] == 'bmp') {
      imageType = "data:image/bmp;base64,";
    }
    
    return imageType
  },
  submitImage: function (e) {
    let _this = this;
    const file = this.data.fileList;
    file.forEach(function (e) {
      //FileSystemManager().readFile()
      var FSM = wx.getFileSystemManager();
      //获取图片
      wx.chooseImage({
        count: 9,
        success: function (res) {
          //循环将得到的图片转换为Base64
          for (let r in res.tempFilePaths) {
            console.log(res.tempFilePaths[r]);
            let imageType = _this.getImageType(res.tempFilePaths[r]);
            FSM.readFile({
              filePath: res.tempFilePaths[r],
              encoding: "base64",
              success: function (data) {
                console.log(data.data)
                wx.request({
                  url: 'http://192.168.50.167:8088/api/post/image',
                  data: {
                    base64Str: imageType + data.data,
                    filename: "111"
                  },
                  method: "POST",
                  success: function (e) {
                    console.log(e);
                  },
                  fail: function (e) {
                    console.log(e);
                  }
                })
              }
            });
          }
        },
      })
    })
    console.log(this.data.base64fileList);
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
    if (this.data.memberNum < 12) {
      this.setData({
        memberNum: this.data.memberNum + 1
      })
    }

  },
  delmemberOp(e) {
    if (this.data.memberNum > 0) {
      this.setData({
        memberNum: this.data.memberNum - 1
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
  createparty: function (e) {

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
    this.submitImage();
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
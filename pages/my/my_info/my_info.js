// pages/my/my_info/my_info.js
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
  },

  //获取用户资料
  getUserInfo(){
    let that = this;
    wx.request({
      url:"http://localhost:8088/api/user/data/select",
      success(res){
        // console.log(res);
        that.setData({
          UserInfo:res.data.data,
          headSrc:res.data.data.userIconUrl,
          nickname:res.data.data.username,
        })
        // console.log(that.data.UserInfo.username);
      }
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
    const _this = this;
    // console.log(event.detail.file[0].url);
    this.setData({
      fileList: [event.detail.file],
      headSrc:event.detail.file
    });
    // console.log(this.data.fileList)

    this.submitImage();
  },

  submitImage:function (e) {
    let _this=this;
    const file = _this.data.fileList;
    let promiseArr=[];
    let imgServerUrls=new Array();
    console.log(_this.data.fileList);
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
                  url: 'http://192.168.5.219:8088/api/posts/imgupload',
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
      _this.setData({
        imgUrls:imgServerUrls,
        headSrc:_this.data.fileList[0].url
      })
      console.log(_this.data.imgUrls)
    }).catch(
        reason=>{
          console.log(reason)
        }
    )
  }
})
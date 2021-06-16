// pages/my/certification/certification.js
const app = getApp();
import {request} from "../../../utils/request"
const timeago = require("timeago.js");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: "",
    fileList:[],
    imgUrls:[],
    finalImageUrls:[],
    imageUrls: { type:Array, value:[] },
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this;
    let baseUrl = app.globalData.baseUrl;
    request({
      url: baseUrl + '/api/user/data/select',
      success(res){
        // console.log(res);
        let mid = [];
        if (res.data.code === 200){
          let tempData = res.data.data;
          tempData.user.certificateImageUrl = baseUrl + "/static/" + tempData.user.certificateImageUrl;
          mid.push(tempData.user.certificateImageUrl);
          that.setData({
            finalImageUrls: mid
          })
        }
        // console.log(that.data.UserInfo.username);
      }
    })
  },

  afterRead: function (event) {
    const that = this;
    console.log(event.detail.file.url);
    this.setData({
      fileList: [event.detail.file],
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
      that.setData({
        imgUrls:imgServerUrls,
      })
      let baseUrl = app.globalData.baseUrl;
      request({
        url: baseUrl + '/api/user/data/update',
        method: 'POST',
        data: {
          certificateImageUrl:that.data.imgUrls[0],
        },
        success(res){
          let imageUrl = app.globalData.baseUrl + "/static/" + that.data.imgUrls[0];
          let mid = [];
          mid.push(imageUrl);
          that.setData({finalImageUrls: mid});
          // that.getUserInfo();
          console.log(that.data.imgUrls[0]);
        }
      })
    }).catch(
        reason=>{
          console.log(reason)
        }
    )
  },

  //预览图片
  previewImage: function(e) {
    wx.previewImage({
      urls: this.data.imageUrls,
      showmenu: true,
      current: e.currentTarget.dataset.currenturl,
      success(res){
        // console.log(e.currentTarget.dataset.currenturl);
      },
      fail(err){
        // console.log(e);
        // console.log(e.currentTarget.dataset.currenturl);
        // Dialog.alert({
        //   message: '图片预览失败\n' + 'wx.previewImage调用失败'
        // }).then(() => {

        // })
        // console.log('调用失败原因', err.errMsg)
      }
    })
  }
})
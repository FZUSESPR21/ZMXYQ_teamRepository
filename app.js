// app.js
import {request} from "./utils/request"
App({
  /**
   * 小程序生命周期之一，初始化完成时调用
   */
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    //初始化，获取openid，存储到缓存中
    this.init()
  },
  globalData: {
    userInfo: null,
    baseUrl1:"http://ccreater.top:61112",
    // baseUrl:"http://localhost:8088"
    baseUrl:"https://test.childfly.cn",
    // baseUrl:"http://192.168.50.167:8088"
    userId: -1
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
  submitImage:function(fileList)//上传图片函数
  {
    let _this=this;
    const file = fileList;
    let promiseArr=[];
    let imgServerUrls=new Array();
    console.log(fileList);
    file.forEach(function (e) {
      var FSM = wx.getFileSystemManager();
      let imageType=_this.getImageType(e.url);
      promiseArr.push(
        new Promise(function (resolve,reject) {
          FSM.readFile({
            filePath: e.url,
            encoding: "base64",
            success: function (data) {
                  wx.request({
                    // url: 'http://192.168.5.219:8088/api/posts/imgupload',
                    url: app.globalData.baseUrl +"/api/posts/imgupload",
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
       
        console.log(imgServerUrls);
      }).catch(
        reason=>{
          console.log(reason)
        }
      )
  },
  userLogin:function (e) {
    let that=this;
    let promise=new Promise(function (resolve,reject) {
      let _that = that;
      wx.login({
        success: res => {
          // 发送 res.code 到后台换取 openId, sessionKey, unionId
           console.log(res.code);
           console.log("登录" + that.globalData.baseUrl);
           request({
             url:  that.globalData.baseUrl + '/api/user/login',
             method: 'POST',
             data:
               res.code,
             success:function(respond)
             {
               that.globalData.userInfo=respond.data;
               console.log("登录成功");
               console.log(respond);
               that.globalData.userId = respond.data.data.id;
               resolve(respond);
             },
             fail:function(respond)
             {
              //  this.globalData.userInfo=res.data;
              console.log("登录失败");
              console.log(respond);
               reject(respond);
             }
           })
        }
      })
    })
    return promise;    
  },

/**
 * 初始化，获取openid，存储到缓存中
 */
  init:function() {
    wx.login({
      success: res => {
        wx.request({
          url: this.globalData.baseUrl + '/api/user/login',
          method: 'POST',
          data: res.code,
          success: res => {
            wx.setStorage({
              key: "openid",
              data: res.data.data
            })
          },
          fail:(err) => {
            console.log('失败')
          }
        })
      }
    })
  }
})

// pages/party/create_party/createparty.js
import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
import { request } from "../../../utils/request"
const timeago = require("timeago.js");
const app = getApp()
const baseUrl = app.globalData.baseUrl;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userId: 123456,
    partyId: 0,
    partyTypeId: -2,
    option1: [
      { text: '自习', value: 0 },
      { text: '电影', value: 1 },
      { text: '聚餐', value: 2 },
      { text: '拼车', value: 3 },
      { text: '拼单', value: 4 },
      { text: '运动', value: 5 },
      { text: '游戏', value: 6 },
      { text: '旅行', value: 7 },
      { text: '其他', value: 8 },
    ],
    // 下拉菜单选项列表
    themeArray: ["自习", "电影", "聚餐", "拼车", "拼单", "运动", "游戏", "旅行", "其他"],
    // 还没有用到
    value1: 0,
    select: false,
    memNum: 1,
    // 当前选中项
    tihuoWay: '全部主题',
    // 图片文件列表
    fileList: [],
    // 
    base64fileList: [],
    partyDetailContent: "",
    buttonOperation: "创建组局(消耗50人品)",
    //
    buttonOperationValue: 1,
    imgUrls: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (Object.getOwnPropertyNames(options).length != 0) {
      // console.log(options.partyDetailContent)
      this.setData({
        partyDetailContent: options.partyDetailContent,
        memNum: parseInt(options.partyMemberCnt),
        buttonOperation: options.operation,
        partyId: parseInt(options.partyID),
        fileList: JSON.parse(options.fileList),
        partyTypeId: options.partyTypeId
      })
      // partyTypeId：从组局详情传过来的组局类型id。这里将它映射成具体的类型名称
      let { option1 } = this.data;
      let { partyTypeId } = this.data;
      option1.forEach((item) => {
        if (item.value == partyTypeId) {
          this.setData({
            tihuoWay: item.text
          })
        }
      })
    }
  },

  onReady: function (e) {
    if (this.data.buttonOperation == "修改拼局") {
      this.setData({
        buttonOperationValue: 2
      })
    }
  },
  bindShowMsg: function () {
    this.setData({
      select: !this.data.select
    })
  },

  afterRead: function (event) {
    const _this = this;
    // console.log(event.detail.file[0].url);
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
    else {
      wx.showToast({
        title: '人数限制',
        icon: 'error',
        duration: 1000
      })
    }
  },
  delmemberOp(e) {
    if (this.data.memNum > 1) {
      this.setData({
        memNum: this.data.memNum - 1
      })
    }
    else {
      wx.showToast({
        title: '人数不能少于1',
        icon: 'error',
        duration: 1000
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
    if (this.data.partyDetailContent == "")//判断拼局内容是否为空
    {
      Dialog.alert({
        message: '活动详情不能为空',
      }).then(() => {
        // on close
      });
    }
    else if (this.data.memNum == 0)//判断拼局人数是否为空
    {
      Dialog.alert({
        message: '拼局人数不能为空',
      }).then(() => {
        // on close
      });
    }
    else {
      // 新增代码：传递用户openid；传递正确partType
      this.data.option1.forEach((i) => {
        if (i.text == this.data.tihuoWay) {
          this.setData({
            value1: i.value
          })
        }
      })
      // 新增代码结束

      let _this = this;
      const file = _this.data.fileList;
      let promiseArr = [];
      let imgServerUrls = new Array();
      //  console.log(_this.data.fileList);
      file.forEach(function (e) {
        var FSM = wx.getFileSystemManager();
        let imageType = getApp().getImageType(e.url);
        promiseArr.push(
          new Promise(function (resolve, reject) {
            FSM.readFile({
              filePath: e.url,
              encoding: "base64",
              success: function (data) {
                wx.request({
                  url: app.globalData.baseUrl + '/api/posts/imgupload',
                  method: "POST",
                  data: {
                    base64Str: imageType + data.data,
                    filename: "111"
                  },
                  success: function (res) {
                    console.log(res);
                    console.log("上传图片成功");
                    if (res.data.code == 200) {
                      return resolve(res);
                    }
                    else {
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
        // console.log(values);
        values.forEach(function (e) {
          // console.log(e);
          imgServerUrls.push(e.data.data)
        })

        // console.log(imgServerUrls);
        _this.setData({
          imgUrls: imgServerUrls
        })
        // console.log(_this.data.imgUrls)
        if (_this.data.buttonOperationValue == 1) {
          request({
            url: app.globalData.baseUrl + '/api/party/insert',
            method: "POST",
            data: {
              userId: _this.data.userId,
              description: _this.data.partyDetailContent.toString(),
              images: _this.data.imgUrls,
              peopleCnt: _this.data.memNum,
              partyTypeID: _this.data.value1
            },
            success(res) {
              console.log(res);
              if (res.data.code == 200) {
                Dialog.alert({
                  message: '发布成功',
                }).then(() => {
                  // on close
                  wx.switchTab({
                    url: '../index/index',
                  })
                });
              }
              else {
                Dialog.alert({
                  message: '发布失败\n' + res.data.message,
                }).then(() => {
                  // on close
                });
              }
            },
            fail: function (res) {
              console.log(res);
            }

          })
        }
        else {
          _this.editParty();
        }
      }).catch(
        reason => {
          console.log(reason)
        }
      )


    }

  },
  /**
   * 修改组局
   */
  editParty: function (e) {
    let editData = {
      partyId: parseInt(this.data.partyId),
      description: this.data.partyDetailContent,
      images: this.data.imgUrls,
      peopleCnt: this.data.memNum,
      partyTypeID: this.data.value1
    }
    request({
      url: app.globalData.baseUrl + '/api/party/update',
      method: "POST",
      data: editData,
      success(res) {
        console.log(res);
        if (res.statusCode == 200) {
          Dialog.alert({
            message: '修改拼局成功',
          }).then(() => {
            // on close
          });
        }
        else {
          Dialog.alert({
            message: '修改拼局失败\n' + res.data.message,
          }).then(() => {
            // on close
          });
        }

      },
      fail: function (res) {
        console.log(res);
      }

    })
  },
  getValue: function (e) {
    var _this = this;
    this.setData({
      partyDetailContent: e.detail.value
    })
  }
})
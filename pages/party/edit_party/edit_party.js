// pages/party/edit_party/edit_party.js
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
    // 图片文件列表，从详情页传过来的
    fileList: [],
    // 图片文件初始列表
    originalFileList: [],

    partyDetailContent: "",
    buttonOperation: "创建组局(消耗50人品)",
    //
    buttonOperationValue: 2,
    //party表中的image_urls
    imgUrls: [],
    //是否编辑过
    isEdited: false,
    isImageEdited: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      this.setData({
        partyDetailContent: options.partyDetailContent,
        memNum: parseInt(options.partyMemberCnt),
        buttonOperation: options.operation,
        partyId: parseInt(options.partyID),
        fileList: JSON.parse(options.fileList),
        originalFileList: JSON.parse(options.fileList),
        partyTypeId: options.partyTypeId
      })
      console.log('fileList--------', this.data.fileList)
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

    // if(isEdited) {
    //   wx.enableAlertBeforeUnload({
    //     message: '还未保存，是否返回',
    //     success(res) {
    //       console.log('成功成功')
    //     },
    //     fail(res) {
    //       console.log('失败失败')
    //     }
    //   })
    // }
  },

  onReady: function (e) {

  },
  /**
   * 下拉框显示/隐藏切换
   */
  bindShowMsg: function () {
    this.setData({
      select: !this.data.select
    })
  },

  afterRead: function (event) {
    const _this = this;
    // console.log(event.detail.file[0].url);
    this.setData({
      fileList: _this.data.fileList.concat(event.detail.file),
      isEdited: true,
      isImageEdited: true
    });
    console.log('new fileList=---------\n', this.data.fileList)
  },
  deleteImage: function (e) {
    const index = e.detail.index; //获取到点击要删除的图片的下标
    const deletImageList = this.data.fileList //用一个变量将本地的图片数组保存起来
    Dialog.confirm({
      message: '确定要删除吗？'
    }).then( () => {
      deletImageList.splice(index, 1) //删除下标为index的元素，splice的返回值是被删除的元素
      this.setData({
        fileList: deletImageList,
        isImageEdited: true,
        isEdited: true
      })
    }).catch( () => {
      //close
    })
  },
  /**
   * 修改组局
   */
  editParty: function (e) {
    let {isEdited} = this.data
    let {isImageEdited} = this.data
    //判断：如果没有修改过，发出提示
    if (!isEdited) {
      Dialog.confirm({
        message: '没有改动，是否返回？'
      }).then( () => {
        //回到前一个页面
        wx.navigateBack({
          delta: 0,
        })
      }).catch( () => {
        //close
      })
    }
    // 判断：有修改过组局
    else {
      // 判断:拼局内容是否为空
      if (this.data.partyDetailContent == "")
      {
        Dialog.alert({
          message: '活动详情不能为空',
        }).then(() => {
          // on close
        });
      }
      // 判断:内容不为空
      else {
        // 判断:图片有修改过
        if (isImageEdited) {
          let _this = this
          let promiseArray = []
          const FSM = wx.getFileSystemManager();
          let imgUrls = []
          let {fileList} = this.data;
          let {originalFileList} = this.data;
          // 待处理的图片列表
          let pendingList = []
          // 最终图片列表与初始图片列表相交的图片
          let intersectingList = []
          // 存储以上两个列表
          let pendingAndIntersectingList = []
            pendingAndIntersectingList = processFileList(originalFileList, fileList)
            intersectingList = pendingAndIntersectingList[0]
            pendingList = pendingAndIntersectingList[1]
            // 获取初始图片列表相交的图片的url的后缀
            if(intersectingList.length != 0) {
              imgUrls = getSuffix(intersectingList);
              console.log('intersectingList---------', intersectingList)
              console.log('suffix---------', imgUrls)
            }
            console.log('函数返回值为-------\n', pendingAndIntersectingList)
            //判断：如果图片列表为空
          if(pendingList.length == 0 && intersectingList.length == 0) {
            Dialog.alert({
              message: '图片不能为空！'
            }).then(() => {
              //关闭
            })
          }
          //判断：图片列表不为空
          else {
            pendingList.forEach( (file, index) => {
              let promise = new Promise(function(resolve, reject) {
                let imageType = getApp().getImageType(file.url);
                FSM.readFile({
                  filePath: file.url,
                  encoding: 'base64',
                  success(res) {
                    wx.request({
                      url: baseUrl + '/api/posts/imgupload',
                      method: 'POST',
                      data: {
                        base64Str: imageType +  res.data,
                        filename: "111"
                      },
                      success(res) {
                        imgUrls.push(res.data.data)
                        resolve()
                      },
                      fail(err) {
                        Dialog.alert({
                          message: '请求失败,图片上传失败'
                        }).then( () => {
                          //close
                        })
                      }
                    })
                  },
                  fail(err) {
                    Dialog.alert({
                      message: '接口readFile调用失败' + err.errMsg
                    }).then(()=> {
                    })
                  }
                })
              })
              promiseArray.push(promise)
            })
          }
          // 获得了所有图片的url后缀后,将party修改提交
          Promise.all(promiseArray).then(() => {
            console.log('------------有没有进来啊')
            _this.setData({
              imgUrls: imgUrls
            })
            let editData = {
              partyId: parseInt(this.data.partyId),
              description: this.data.partyDetailContent,
              images: this.data.imgUrls,
              peopleCnt: parseInt(this.data.memNum),
              partyTypeID: parseInt(this.data.partyTypeId)
            }
            console.log('editData---------------\n', editData)
            wx.request({
              url: baseUrl + '/api/party/update',
              method: 'POST',
              data: editData,
              success(res) {
                console.log('修改提交到服务器返回res---------', res);
              console.log('editData---------------\n', editData)
                if(res.statusCode == 200) {
                  Dialog.alert({
                    message: '修改拼局成功'
                  }).then(() =>{
                    wx.navigateBack({
                      delta: 0,
                    })
                  })
                }
                else {
                  Dialog.alert({
                    message: '修改拼局失败\n' + res.statusCode
                  }).then(() => {
  
                  })
                }
              },
              fail(err){
                console.log('组局修改失败--------', err)
                Dialog.alert({
                  message: '组局修改失败'
                }).then(() => {
  
                })
              }
            })
          })
        }
        // 判断:图片没有修改过,直接将原来的图片url的后缀作为参数传递
        else {
          let suffixList = getSuffix(this.data.originalFileList)
          this.setData({
            imgUrls: suffixList
          })
          let editData = {
            partyId: parseInt(this.data.partyId),
            description: this.data.partyDetailContent,
            images: this.data.imgUrls,
            peopleCnt: parseInt(this.data.memNum),
            partyTypeID: parseInt(this.data.partyTypeId)
          }
          console.log('editData=----------\n', JSON.stringify(editData))
          wx.request({
            url: baseUrl + '/api/party/update',
            method: 'POST',
            data: editData,
            success(res) {
              console.log('修改提交到服务器返回res---------', res);
              console.log('editData---------------\n', editData)
              if(res.statusCode == 200) {
                Dialog.alert({
                  message: '修改拼局成功'
                }).then(() =>{
                  wx.navigateBack({
                    delta: 0,
                  })
                })
              }
              else {
                Dialog.alert({
                  message: '修改拼局失败\n' + res.statusCode
                }).then(() => {

                })
              }
            },
            fail(err){
              console.log('组局修改失败--------', err)
              Dialog.alert({
                message: '组局修改失败'
              }).then(() => {

              })
            }
          })
        }
      }
    }
  },
  /**
   * 获取活动详情输入框的值
   */
  getValue: function (e) {
    this.setData({
      partyDetailContent: e.detail.value,
      isEdited: true
    })
  },

  addmemberOp(e) {
    if (this.data.memNum < 12) {
      this.setData({
        memNum: this.data.memNum + 1,
        isEdited: true
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
        memNum: this.data.memNum - 1,
        isEdited: true
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
    let {option1} = this.data
    this.setData({
      tihuoWay: name,
      select: false,
      isEdited: true
    })
    option1.forEach( (item) => {
      if(item.text == name) {
        this.setData({
          partyTypeId: item.value
        })
      }
    } )
  }
})
/**
 * 图片列表处理函数，最开始的就有的图片不用处理，新增的图片要进行处理
 * @param {'图片的最终列表'} final 
 * @param {'图片的初始列表'} original 
 */
function processFileList(original, final) {
  //待处理的 和 与初始列表相交的图片列表
  let pendingAndIntersectingList = [];
  let pendingList = [];
  let intersectingList = []
  final.forEach((item) => {
    let flag = true
    original.forEach((i) => {
      if(item.url == i.url) {
        intersectingList.push(item)
        flag = false
      }
    })
    if(flag) {
      pendingList.push(item)
    }
  })
  pendingAndIntersectingList.push(intersectingList);
  pendingAndIntersectingList.push(pendingList);
  return pendingAndIntersectingList;
}
/**
 * 
 * @param {'获得url的后缀'} 
 */
function getSuffix(interList) {
  let interUrls = []
  interList.forEach((item, index) => {
    let array = item.url.split('/')
    let suffix = array[array.length - 1]
    console.log(index , '-----------\n',  item)
    interUrls.push(suffix)
  })
  console.log('interUrls---------', interUrls)
  return interUrls;
}
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
    // 图片文件列表
    fileList: [],
    // 
    base64fileList: [],
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

  },
  deleteImage: function (e) {
    this.setData({
      isEdited: true,
      isImageEdited: true
    })
    const index = e.detail.index; //获取到点击要删除的图片的下标
    const deletImageList = this.data.fileList //用一个变量将本地的图片数组保存起来
    Dialog.confirm({
      message: '确定要删除吗？'
    }).then( () => {
      deletImageList.splice(index, 1) //删除下标为index的元素，splice的返回值是被删除的元素
      this.setData({
        fileList: deletImageList
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
    else {
      wx.showToast({
        title: 'hhhhhh'
      })
      if (this.data.partyDetailContent == "")//判断拼局内容是否为空
      {
        Dialog.alert({
          message: '活动详情不能为空',
        }).then(() => {
          // on close
        });
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
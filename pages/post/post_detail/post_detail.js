// pages/post_detail/post_detail.js
import Notify from '../../../miniprogram_npm/@vant/weapp/notify/notify';
import Toast from '../../../miniprogram_npm/@vant/weapp/toast/toast';
import {request} from "../../../utils/request"
const app = getApp();
const timeago = require("timeago.js");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    commentInputText:"",
    postId:0,
    publisherId:0,
    postType:"",
    publisherName:"",
    message:"",
    item:null,
    showRewardBox:false,
    popularityNum:0,
    hasMark:false,
    hasLike:true,
    userId:123457,

    postCommentList:[],
    commentMessage: {
      publisherName:"楼主"
    },
    // 是否是回复。
    // false：在父评论中新增评论(preId使用-1)
    // true：在子评论中增加评论(preId使用组件中传来的preId，某个父评论的Id
    isReply: false,
  },

  getDetail(){
    let that = this;
    let baseUrl = app.globalData.baseUrl;
    request({
      url: baseUrl + '/api/posts/getById',
      method: 'POST',
      data:{
        "postId":that.data.postId,
      },
      success(res){
        if(res.data.code === 200){
          console.log(res)
          let midPostsData = res.data.data[0];
          console.log(midPostsData);
          if(midPostsData != null){
            let imageStr = midPostsData.imageUrls;
            if(imageStr === ""){
              midPostsData.imageUrls = [];
            }
            else {
              midPostsData.imageUrls = imageStr.split(';');
            }
            for(let imageIndex = 0; imageIndex < midPostsData.imageUrls.length; imageIndex++){
              midPostsData.imageUrls[imageIndex] = baseUrl + "/static/" + midPostsData.imageUrls[imageIndex];
            }
            midPostsData.gmtCreate = timeago.format(new Date(midPostsData.gmtCreate), 'zh_CN');
          }
          that.setData({
            item:midPostsData,
          })
        }

      }
    })
  },

  // 获取评论列表函数
  getPostCommentList: function (e) {
    let that=this;

    request({
      url: app.globalData.baseUrl+'/api/alumnicycle/posts/commentlist',
      method: 'POST',
      // data: jsonValue,
      data:
      that.data.postId,
      // Headers: {
      //   'content-type': 'application/json'
      // },
      success: function (res) {
        if(res.data.code === 200){
          console.log(res);
          that.setData({
            postCommentList:res.data.data
          })
          console.log(that.data.postCommentList)
        }
      },
      fail:function (res) {
        console.log(res);
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      postId:options.postId,
    })
    console.log(this.data.postId)
    this.getDetail();
    this.getPostCommentList();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.popover = this.selectComponent('#popover');
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  onTap: function (e) {
    // 获取按钮元素的坐标信息
    var id = 'morebutton' // 或者 e.target.id 获取点击元素的 ID 值
    wx.createSelectorQuery().select('#' + id).boundingClientRect(res => {
      // 调用自定义组件 popover 中的 onDisplay 方法
      this.popover.onDisplay(res);
    }).exec();
  },

  bindTextAreaBlur: function (e) {
    this.setData({
      commentInputText: e.detail.value
    })
    console.log(this.data.commentInputText)
  },

  getValue:function (e) {
    let that=this;
    this.setData({
      commentInputText:e.detail.value
    })
  },

  getCommentBox: function (e) {
    this.setData({
      showCommentBox: true,
      commentMessage: e.detail,
      isReply: true
    })
    console.log(this.data.commentMessage);
    console.log(10);
  },

  // 发送评论函数
  sendComment: function (e) {
    let that=this;
    let {isReply} = this.data
    let preId = -1
    if(isReply) {
      preId = parseInt(this.data.commentMessage.preId)
      console.log('preId------------', preId)
    }
    request({
      url: app.globalData.baseUrl+"/api/alumnicycle/posts/comment",
      method: "POST",
      data: {
        message: that.data.commentInputText.toString(),
        postId:parseInt (that.data.postId),
        preId: preId,
        idTo: parseInt (that.data.userId),
      },
      success: function (res) {
        console.log(res);
        Notify({
          type: 'success',
          message: '评论成功'
        });
        that.setData({
          commentInputText:""
        })
        that.getPostCommentList()
      },
      fail:function (res) {
        console.log(res);
      }
    })
  },

  postMark:function(e)
  {
   this.setData({
     hasMark:!this.data.hasMark
   })
  },

  getRewardBox:function(e)
  {
    this.setData({
      showRewardBox:true
    })
  },

  onClose() {
    this.setData({ showRewardBox: false });
  },
})
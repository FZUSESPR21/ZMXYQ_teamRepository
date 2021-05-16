// components/v-post-item/v-post-component.js
const app = getApp();
import {request} from "../../utils/request"
const timeago = require("timeago.js");
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    eyeOnNum: Number,
    gmtCreate: String,
    imageUrls: { type:Array, value:[] },
    isEyeOn: Number,
    isLike: Number,
    likeNum: Number,
    message: String,
    postId: Number,
    postType: String,
    publisherId: Number,
    pubulisherName: String,
    rewardNum: Number
  },

  /**
   * 组件的初始数据
   */
  data: {
    isReward: 0
  },

  /**
   * 组件的方法列表
   */
  methods: {
    //点赞处理函数
    handleLike: function(event){
      let id = event.target.dataset.id;
      let that = this;
      let baseUrl = app.globalData.baseUrl;
      let jsonStr = '{"postId":' + id + '}';
      let jsonValue = JSON.parse(jsonStr);
      console.log(jsonValue);
      request({
        url:  baseUrl + '/api/alumnicycle/posts/like',
        method:'POST',
        Headers: {
          'content-type': 'application/json'
        },
        data: jsonValue,
        success:function(res)
        {
          console.log(res);
          let newLikeNum = that.data.likeNum + 1; 
          console.log(newLikeNum);
          that.setData({
            isLike: 1,
            likeNum: newLikeNum
          });   //设置为喜欢
        },
        fail:function(res)
        {
          console.log(res);
        }
      });
    },
    //赞赏处理函数
    handleReward: function(event){
      console.log(event.target.dataset.id);
      console.log(event.target.dataset.id);
      let id = event.target.dataset.id;
      let that = this;
      let baseUrl = app.globalData.baseUrl;
      let jsonStr = '{"postId":' + id + ', "amount": 2' + '}';
      let jsonValue = JSON.parse(jsonStr);
      console.log(jsonValue);
      request({
        url:  baseUrl + '/api/alumnicycle/posts/reward',
        method:'POST',
        Headers: {
          'content-type': 'application/json'
        },
        data: jsonValue,
        success:function(res)
        {
          console.log(res);
          if(res.data.code != 200){
            wx.showToast({
              title: '人品值不足！',
              icon: 'error',
              duration: 1000
            })
          }
          else{
            let newRewardNum = that.data.rewardNum + 2; 
            that.setData({
              isReward: 0,
              rewardNum: newRewardNum
            });   //设置为喜欢
          }
        },
        fail:function(res)
        {
          console.log(res);
        }
      });
    },
    //收藏处理函数
    handleMark(event){
      console.log(event.target.dataset.id);
      let id = event.target.dataset.id;
      let that = this;
      let baseUrl = app.globalData.baseUrl;
      let jsonStr = '{"postId":' + id + '}';
      let jsonValue = JSON.parse(jsonStr);
      console.log(jsonValue);
      request({
        url:  baseUrl + '/api/alumnicycle/posts/collect',
        method:'POST',
        Headers: {
          'content-type': 'application/json'
        },
        data: jsonValue,
        success:function(res)
        {
          console.log(res);
          let newEyeOnNum = that.data.eyeOnNum + 1; 
          that.setData({
            isEyeOn: 1,
            eyeOnNum: newEyeOnNum
          });   //设置为喜欢
        },
        fail:function(res)
        {
          console.log(res);
        }
      });
    },

    //跳转至详情界面函数
    goToDetail: function(event){
      let id = event.currentTarget.dataset.id;
      console.log(event);
      console.log(id);
      wx.navigateTo({
        url: '../post_detail/post_detail?postId=' + id,
      })
    },

    //举报处理函数
    handleReport: function(event){
      let id = event.currentTarget.dataset.id;
      console.log(event);
      console.log("举报" + id);
      let that = this;
      let baseUrl = app.globalData.baseUrl;
      let jsonStr = '{"postId":' + id + '}';
      let jsonValue = JSON.parse(jsonStr);
      console.log(jsonValue);
      request({
        url:  baseUrl + '/api/alumnicycle/posts/tipoff',
        method:'POST',
        Headers: {
          'content-type': 'application/json'
        },
        data: jsonValue,
        success:function(res)
        {
          console.log(res);
          if(res.data.code == 200){
            wx.showToast({
              title: '举报成功!',
              icon: 'success',
              duration: 1000
            })
          }
        },
        fail:function(res)
        {
          console.log(res);
        }
      });

    },

    onTap: function (e) {
      // 获取按钮元素的坐标信息
      this.popover = this.selectComponent('#popover');
      var id = e.currentTarget.dataset.idname;// 或者 e.target.id 获取点击元素的 ID 值
      this.createSelectorQuery().select('#' + id).boundingClientRect(res => {
        // 调用自定义组件 popover 中的 onDisplay 方法
        // console.log(res);
        this.popover.onDisplay(res);
      }).exec();
    },
    
  }
})

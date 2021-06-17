// components/v-name-card.js
const app=getApp();
import {request} from "../../utils/request"
const timeago = require("timeago.js");
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    publisherMsg:{
      type:Object,
      value:{}
    },
    publisherTime:{
      type:String,
      value:""
    },
    userId:{
      type:Number,
      value:0
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    publisherName:"",
    publisherSchool:"",
    publisherGender:"",
    publisherHeadUrl:"../../../../static/icons/smile.png",
    showPopUp:false,
  },

  /**
   * 组件的方法列表
   */
  methods: {
    Popup() {
      this.setData({ showPopUp: !this.data.showPopUp });
    },
    getPublisherMessage:function(e)
    {
      this.setData({ showPopUp: !this.data.showPopUp });
      let data={
        publisherId:this.data.userId
      };
      let _this=this;
      request({
        url: app.globalData.baseUrl+"/api/posts/publishermsg",
        method:"POST",
        data:data,
        success:function (res) {
          if (res.data.code === 200) {
            let tempData = res.data.data;
            // console.log(tempData);
            tempData.iconUrl = app.globalData.baseUrl + "/static/" + tempData.iconUrl;
            _this.setData({
              publisherMsg:tempData,
              publisherHeadUrl: tempData.iconUrl
            })
          }
        }
      })
    },
    onClose() {
      this.setData({ showPopUp: false });
    },
    noPopUpGetPublisherMessage:function(e)
    {
      console.log(this.data.userId)
      let data={
        publisherId:this.data.userId
      };

      let _this=this;
      request({
        url: app.globalData.baseUrl+"/api/posts/publishermsg",
        method:"POST",
        data:data,
        success:function (res) {
          if (res.data.code === 200){

            let tempData = res.data.data;
            // console.log(tempData);
            tempData.iconUrl = app.globalData.baseUrl + "/static/" + tempData.iconUrl;
            _this.setData({
              publisherMsg:tempData,
              publisherHeadUrl: tempData.iconUrl
            })
            if(_this.data.publisherTime){
              _this.setData({
                publisherTime:timeago.format(new Date(_this.data.publisherTime),'zh_CN'),
              })
            }
          }
        }
      })
    },
    blackUser:function(e){
      /**
       * 拉黑用户
       * 
       */
      console.log(this.data.publisherMsg)
      let uid = this.data.userId
      wx.showModal({
        title: '提示',
        content: 'QAQ您确定要拉黑该用户吗',
        success (res) {
          if (res.confirm) {
            let jsonStr = '{"userIdTo":' + uid + ',"isBlock":'+ 1 +'}';
            let jsonValue = JSON.parse(jsonStr);
            request({
              url : app.globalData.baseUrl + "/api/block",
              method : "post",
              Headers : {
                'content-type': 'application/json'
              },
              data : jsonValue,
              success:function(res){
                wx.showToast({
                  title: '成功拉黑',
                  icon:'success',
                  duration:1000
                })
              },
              fail:function(res){
                wx.showToast({
                  title: '拉黑失败',
                  icon:'error',
                  duration:1000
                })
              }
            })
          } else if (res.cancel) {
          }
        }
      })
    },
    goToChat:function(e){
      /**
       * 
       * 用户私聊
       * 
       */
      wx.navigateTo({
        url: '/pages/message/chat/chat?userid='+this.data.userId+'&iconurl='+this.data.publisherMsg.iconUrl
      })
    }
  },
  lifetimes:{
    ready(){
      this.noPopUpGetPublisherMessage();
    }
  },
})

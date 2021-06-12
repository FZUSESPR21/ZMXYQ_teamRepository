// components/v-name-card.js
const app=getApp();
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
    publisherHeadUrl:"",
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
      wx.request({
        url: app.globalData.baseUrl+"/api/posts/publishermsg",
        method:"POST",
        data:data,
        success:function (res) {
          // console.log(res.data.data)
          _this.setData({
            publisherMsg:res.data.data
          })
        }
      })
    },
    onClose() {
      this.setData({ showPopUp: false });
    },
    noPopUpGetPublisherMessage:function(e)
    {
      let data={
        publisherId:this.data.userId
      };
      let _this=this;
      wx.request({
        url: app.globalData.baseUrl+"/api/posts/publishermsg",
        method:"POST",
        data:data,
        success:function (res) {
          console.log(res.data.data)
          _this.setData({
            publisherMsg:res.data.data
          })
          _this.setData({
            publisherTime:timeago.format(new Date(_this.data.publisherTime),'zh_CN'),
          })
        }
      })
    },
  },
  lifetimes:{
    ready(){
      console.log(this.data.userId)
      this.noPopUpGetPublisherMessage();
      console.log(this.data.userId)
    },
    // attached: function() {
    //   console.log(this.data.userId)
    //   // 在组件实例进入页面节点树时执行
    //   this.noPopUpGetPublisherMessage();
    // },
  },
})

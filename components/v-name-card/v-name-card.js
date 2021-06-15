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
          if (res.data.code === 200) {
            let tempData = res.data.data;
            // console.log(tempData);
            tempData.iconUrl = app.globalData.baseUrl + "/static/" + tempData.iconUrl;
            _this.setData({
              publisherMsg:tempData,
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
      wx.request({
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
  },
  lifetimes:{
    ready(){
      this.noPopUpGetPublisherMessage();
    }
  },
})

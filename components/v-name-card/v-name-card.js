// components/v-name-card.js
const app=getApp();
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
          console.log(res.data.data)
          _this.setData({
            partyPublisherMsg:res.data.data
          })
        }
      })
    },
  }
})

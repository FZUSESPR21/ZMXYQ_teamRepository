// components/v-name-card.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
     publisherId:{
      type:Number,
      value:0,
     },
     publisherName:{
       type:String,
       value:""
     },
     publisherSchool:{
      type:String,
      value:""
    },
    publisherGender:{
      type:String,
      value:""
    },
    publisherHeadUrl:{
      type:String,
      value:""
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
     wx.request({
       url: 'http://xx.com/api/alumnicycle/posts/publishermsg',
       method:"POST",
       data:{
         publisherId:0
       },
       success:function(res){

       }
     })
    },
  }
})

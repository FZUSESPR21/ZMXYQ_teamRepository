// components/v-name-card.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
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

  }
})

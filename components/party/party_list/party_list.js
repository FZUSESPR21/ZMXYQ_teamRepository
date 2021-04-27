// components/party_list/party_list.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  /**
   * 页面的初始数据
   */
  data: {
    partyType:{
      0 : "自习",
      1 : "电影",
      2 : "聚餐",
      3 : "拼车",
      4 : "拼单",
      5 : "运动",
      6 : "游戏",
      7 : "旅行",
      8 : "其他"
    },
    option1: [
      { text: '所有组局', value: -2 },
      { text: '非空组局', value: -1 },
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
    value1: -2
 },

 /*监听数据变化*/
 observers:  {
   'value1': function(){
    //选择发生了变化，改变列表展示得内容
   } 
 },

  /**
   * 组件的方法列表
   */
  methods: {
    onChange: function ({detail}) {
      this.setData({
        value1 : detail
      });
      // console.log(this.data.value1);
     }
  }
})

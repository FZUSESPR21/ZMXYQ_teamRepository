// components/party_list/party_list.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

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
    value1: -2,
    partyList: [],
},

    attached: function () {
    for(let i = 0; i < 10; i++)
    {
      let partyList = this.data.partyList;
      partyList.push({
        "description": "晚上十点，玫瑰园，王者荣耀五黑，不见不散，带你上王者，我就是阿伟！",
        "publisher": {
            "username": "张三",
            "sex":"男"
        },
        "peopleCnt": 6,
        "nowPeopleCnt": 3,
        "partyType": "组局",
        "gmtCreate": "1h前"
      });
      this.setData({
        partyList
      })
     }    
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onChange: function ({detail}) {
      let partyList = this.data.partyList;
      partyList.push({
        "description": "晚上十点，玫瑰园，王者荣耀五黑，不见不散，带你上王者，我就是阿伟！",
        "publisher": {
            "username": "张三",
            "sex":"男"
        },
        "peopleCnt": 6,
        "nowPeopleCnt": 3,
        "partyType": "组局",
        "gmtCreate": "1h前"
      });
      this.setData({
        partyList
      });
     }
  }
})

// pages/party/my_party/my_party.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    partyList: [],
    value: ""
},

getData:function(){
  console.log(this.data.value);
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
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getData();
  }

})
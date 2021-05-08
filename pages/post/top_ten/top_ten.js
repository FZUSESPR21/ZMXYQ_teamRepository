// pages/post/top_ten/top_ten.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
      topTen:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      let topTen = this.data.topTen;
      for(let i = 0; i < 10; i++)
      {
        topTen.push(i.toString());
      }
      this.setData({
        topTen
      });
  }
})

Component({

  /**
   * 页面的初始数据
   */
  data: {
      topTen:[]
  },

  pageLifetimes: {
    show: function () {
        let topTen = this.data.topTen;
        for(let i = 0; i < 10; i++)
        {
          topTen.push(i.toString());
        }
        this.setData({
          topTen
        });
    }
}
  })
import {request} from "../../utils/request"
const app = getApp();
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
        let that = this;
        let baseUrl = app.globalData.baseUrl;
        request({
          url:  baseUrl + '/api/posts/heatposts',
          method:'POST',
          data: {
             userId: 1 
          },
          success:function(res)
          {
          //  console.log(res);
           let resData = res.data.data;
           if(resData != null){
            for(let i = 0; i < resData.length; i++){
              if(resData[i].message.length > 40)
                resData[i].message = resData[i].message.substr(0, 40) + "...";
              topTen.push(resData[i].message);
            }
          }
            that.setData({
             topTen    
           })
          },
          fail:function(res)
          {
            console.log(res);
          }
        });
        this.setData({
          topTen
        });
    }
}
})
// pages/post_list/post_list.js
const app = getApp();
import {request} from "../../../utils/request"
const timeago = require("timeago.js");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    postsData: [],
  },

    onShow: function() {
      if(!app.globalData.userInfo)
      {
        let postsData = this.data.postsData;
        let that = this;
        app.userLogin().then(function (res) {
          let baseUrl = app.globalData.baseUrl;
          let jsonStr = '{"userId": 123456}';
          let jsonValue = JSON.parse(jsonStr);
          console.log(jsonValue);
          request({
            url:  baseUrl + '/api/posts/heatposts',
            method:'POST',
            Headers: {
              'content-type': 'application/json'
            },
            data: jsonValue,
            success:function(res)
            {
             console.log("top-10获取数据");
             console.log(res);
             let midPostsData = res.data.data;
             if(midPostsData!= null){
                for(let i = 0; i < midPostsData.length; i++){
                  midPostsData[i].gmtCreate = timeago.format(new Date(midPostsData[i].gmtCreate),'zh_CN');
                  let midImageUrls = midPostsData[i].imageUrls;
                  if(midPostsData[i].imageUrls != "" && midPostsData[i].imageUrls != null){
                    midPostsData[i].imageUrls = midPostsData[i].imageUrls.split(';');
                    if(midPostsData[i].imageUrls.length == 0)
                      midPostsData[i].imageUrls.push(midImageUrls);
                  }
                  //图片最终url
                  for(let imageIndex = 0; imageIndex < midPostsData[i].imageUrls.length; imageIndex++){
                    midPostsData[i].imageUrls[imageIndex] = baseUrl + "/static/" + midPostsData[i].imageUrls[imageIndex];
                  }
                }
                for(var m in midPostsData)
                 postsData.push(midPostsData[m]);
                 that.setData({
                   postsData  
                });
                console.log("that.data.postsData[0].imageUrls");
                console.log(that.data.postsData[0].imageUrls);
             }
            },
            fail:function(res)
            {
              console.log(res);
            }
          });
        }).catch(
        reason=>{
          console.log(reason)
        }
      )
      }
    }

})
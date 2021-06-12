// pages/post/search_post/search_post.js
const app = getApp();
import {request} from "../../../utils/request"
const timeago = require("timeago.js");
import Toast from '@vant/weapp/toast/toast';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    postsData: [],
    value: ""
  },

  searchPost: function(){
    if(this.data.value != null && this.data.value.length != 0){
      let content = this.data.value.trim();
      let postsData = [];
      let that = this;
      console.log(this);
      app.userLogin().then(function (res) {
        let baseUrl = app.globalData.baseUrl;
        let jsonStr = '{"pageSize": 100,"pageNum": 1,"content": "' + content +  '"}';
        let jsonValue = JSON.parse(jsonStr);
        console.log(jsonValue);
        request({
          url:  baseUrl + '/api/posts/search',
          method:'POST',
          Headers: {
            'content-type': 'application/json'
          },
          data: jsonValue,
          success:function(res)
          {
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
              console.log(that.data.postsData);
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
    else{
      Toast('搜索条件为空');
    }
  }

})
// pages/party/partydetailpage/partydetailpage.js
import Notify from '../../../miniprogram_npm/@vant/weapp/notify/notify';
Page({

  /**
   * 页面的初始数据
   */
  data: {
     partyMemberList:[
       {
         userName:"222",
         state:true,
       },
       {
        userName:"222",
        state:true,
      },
      {
        userName:"222",
        state:false,
      },
   
     ],
     partyCommentList:[],
  },
  joinparty:function(e)
  {
    Notify({ type: 'success', message: '加入拼局成功' });
    // wx.request({
    //   url: 'http://xx.com/api/alumnicycle/party/partymes',
    //   methods:"get",
    //   data:{
    //     'partyId':1
    //   },
    //   success:function(res)
    //   {
    //     Notify({ type: 'success', message: '加入拼局成功' });
    //   }
      
    // })
  },
  onReady: function () {
   wx.request({
     url: 'http://xx.com/api/alumnicycle/party/partymes',
     method:'GET',
     data:{
       id:1
     },
     success:function(res)
     {

     }

   });
   wx.request({
     url: 'http://xx.com/api/alumnicycle/party-comment/commentlsit',
     method:'POST',
     data:{
       'partyId':'1'
     }
     ,
     success:function(res)
     {
       this.data.partyCommentList=res.data;
     }
   })
  },

 
})
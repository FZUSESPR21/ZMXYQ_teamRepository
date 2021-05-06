// pages/party/partydetailpage/partydetailpage.js
import Notify from '../../../miniprogram_npm/@vant/weapp/notify/notify';
Page({

  /**
   * 页面的初始数据
   */
  data: {
     partyDetailContent:"你们好",
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
     partyMemmberCnt:2
  },
  joinParty:function(e)
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
  getPartyDetail:function(e){
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
  },
  getPartyCommentList:function(e){
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
  onReady: function () {
    this.getPartyDetail ();
    this.getPartyCommentList();
    this.popover = this.selectComponent('#popover');
  },
  sendComment:function(e)
  {
    wx.request({
      url: 'http://xx.com/api/alumnicycle/party-comment/comment',
      method:"POST",
      data:{
        content:"",
        userId:0,
        partyId:0,
        preId:""
      },
      success:function(res)
      {
        Notify({ type: 'success', message: '评论成功' });
      }

    })
  },
  onTap: function (e) {
    // 获取按钮元素的坐标信息
    var id = 'morebutton' // 或者 e.target.id 获取点击元素的 ID 值
    wx.createSelectorQuery().select('#' + id).boundingClientRect(res => {
      // 调用自定义组件 popover 中的 onDisplay 方法
      this.popover.onDisplay(res);
    }).exec();
  },

  // 响应popover组件中的子元素点击事件
  editParty: function (e) {
    wx.navigateTo({
      url: '../create_party/createparty?partyDetailContent='+this.data.partyDetailContent+'&partyMemberCnt='+this.data.partyMemmberCnt+'&operation=修改拼局',
    });
    // 调用自定义组件 popover 中的 onHide 方法
    this.popover.onHide();
  }

 
})
// pages/party/partydetailpage/partydetailpage.js
import Notify from '../../../miniprogram_npm/@vant/weapp/notify/notify';
import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    partyID:0,
     partyDetailContent:"你们好",
     partyMemberList:[
       {
        participantsID:0,
        inTheParty:true
       },
       {
        participantsID:0,
        inTheParty:true
      },
      {
        participantsID:0,
        inTheParty:true
      },
   
     ],
     partyCommentList:[],
     partyMemmberCntNow:1,
     partyMemmberCnt:2,
     hasjoined:false,
     buttonContent:"加入拼局"
  },

  /*
  生命周期函数
  */
 onLoad: function (options) {
  // console.log(options.partyID);
  this.setData({
    partyID: options.partyID
  });
  // console.log(this.data.partyID);
  
},

  // 加入或退出组局函数
  joinParty:function(e)
  {
    
    if(this.data.hasjoined==false)
    {
      if(this.data.partyMemmberCntNow<this.data.partyMemmberCnt)
      {
        this.setData({
          buttonContent:"退出组局",
          hasjoined:true
        });
        Notify({ type: 'success', message: '加入拼局成功' });
        this.setData({
          partyMemberList:this.data.partyMemberList.concat({
            userName:"222",
            state:true
          }),
          partyMemmberCntNow:this.data.partyMemmberCntNow+1
        })
      }
      else{
        Dialog.alert({
          message: '组局人数已经达到上限',
        }).then(() => {
          // on close
        });
      }
     
    }
    else{
      this.setData({
        buttonContent:"加入组局",
        hasjoined:false
      })
      
      Notify({ type: 'success', message: '退出拼局成功' });
    }
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
     // wx.request({
    //   url: 'http://xx.com/api/alumnicycle/party/exit',
    //   methods:"POST",
    //   data:{
    //     'partyId':1
    //   },
    //   success:function(res)
    //   {
    //     Notify({ type: 'success', message: '加入拼局成功' });
    //   }
      
    // })
  },
  // 获取组局详情
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
  // 获取评论列表函数
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
  // 发送评论函数
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
  //气泡小组件获取位置函数
  onTap: function (e) {
    // 获取按钮元素的坐标信息
    var id = 'morebutton' // 或者 e.target.id 获取点击元素的 ID 值
    wx.createSelectorQuery().select('#' + id).boundingClientRect(res => {
      // 调用自定义组件 popover 中的 onDisplay 方法
      this.popover.onDisplay(res);
    }).exec();
  },

  // 编辑拼局函数
  editParty: function (e) {
    wx.navigateTo({
      url: '../create_party/createparty?partyDetailContent='+this.data.partyDetailContent+'&partyMemberCnt='+this.data.partyMemmberCnt+'&operation=修改拼局',
    });
    // 调用自定义组件 popover 中的 onHide 方法
    this.popover.onHide();
  },
  // 解散组局函数
  disbandParty:function(e)
  {
    Dialog.confirm({
      message: '确定要解散拼局吗',
    })
      .then(() => {
        wx.request({
          url: 'http://xx.com/api/alumnicycle/party/delete',
          method:"POST",
          data:{
            partyId:0
          },
          success:function(res)
          {

          }
        })
        // on confirm
      })
      .catch(() => {
        // on cancel
      });
      this.popover.onHide();
  },
  // 将参与者移除组局函数
  moveOffMember:function(e)
  {
    
    Dialog.confirm({
      message: '确定要移除该成员吗',
    })
      .then(() => {
        let deleteIndex=e.currentTarget.dataset.index;
        this.moveOff(deleteIndex);
      })
      .catch(() => {
        // on cancel
      });
  },
  moveOff:function(deleteIndex)
  {
     // wx.request({
        //   url: 'http://xx.com/api/alumnicycle/party-participants/moveoff',
        //   method:"POST",
        //   data:{
        //     partyId:0,
        //     userId:0
        //   },
        //   success:function(res)
        //   {

        //   }
        // })
        // // on confirm
        console.log(deleteIndex);
    let newMemberList=this.data.partyMemberList;
    newMemberList.splice(deleteIndex,1);
   this.setData({
     partyMemberList:newMemberList
   });
  }
 
})
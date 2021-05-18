// pages/party/partydetailpage/partydetailpage.js
import Notify from '../../../miniprogram_npm/@vant/weapp/notify/notify';
import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
import {
  request
} from "../../../utils/request"
const timeago = require("timeago.js");
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userId: 123456,
    partyID: "",
    partyDetailContent: "你们好",
    partyDetailImageUrls: ["https://www.fzu.edu.cn/attach/2021/04/29/419363.JPG","https://www.fzu.edu.cn/attach/2021/04/29/419363.JPG","https://www.fzu.edu.cn/attach/2021/04/29/419363.JPG","https://www.fzu.edu.cn/attach/2021/04/29/419363.JPG"],
    partyPublisherID: 0,
    partyPublisherMsg:{},
    partyCreateTime: "",
    partyParticipantsId:[],
    partyMemberList: [{
        participantsID: 0,
      },
      {
        participantsID: 0,
      
      },
      {
        participantsID: 0,
      },

    ],
    partyCommentList: [],
    partyMemmberCntNow: 1,
    partyMemmberCnt: 2,
    partyOwner:true,
    hasjoined: false,
    buttonContent: "加入拼局",
    commentMessage: {
     publisherName:"楼主"
    },
    commentInputText: "",
    moveOffButtonText:"移除成员"
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
  joinParty: function (e) {
    //  
    console.log(app.globalData.userInfo)
    let _this = this;
    if (this.data.hasjoined == false) {
      if (this.data.partyMemmberCntNow < this.data.partyMemmberCnt) {

        request({
          url: app.globalData.baseUrl +"/api/party/join",
          method: "post",
          data: {
            partyId: 106,
          },
          header:{
            'content-type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            console.log(res);
            _this.setData({
              buttonContent: "退出组局",
              hasjoined: true
            });
            Notify({
              type: 'success',
              message: '加入拼局成功'
            });
          },
          fail:function (e) {
            console.log(e);
          }
        })
        _this.getPartyDetail();

      } else {
        Dialog.alert({
          message: '组局人数已经达到上限',
        }).then(() => {
          // on close
        });
      }

    } else {
      request({
        url: app.globalData.baseUrl + "/api/party/exit",
        method: "POST",
        data: {
          partyId: 107,
        },
        header:{
          'content-type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          console.log(res);
          _this.setData({
            buttonContent: "加入组局",
            hasjoined: false
          })

          Notify({
            type: 'success',
            message: '退出拼局成功'
          });
          _this.getPartyDetail();   

        } 
      })
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
    //     Notify({ type: 'success', message: '退出拼局成功' });
    //   }

    // })
  },
  // 获取组局详情
  getPartyDetail: function (e) {
    let _this = this;
    console.log(1);
    request({
      url: 'http://ccreater.top:61112/api/party/partymes',
      method: 'GET',
      data: {
        partyId: 106
      },
      success: function (res) {
        let data = res.data.data;
        console.log(res);
        console.log(data);
        if (data != null) {
          _this.setData({
            partyID: data.partyID,
            partyDetailContent: data.context,
            partyPublisherID:data.publisherID,
            partyMemmberCnt: data.peopleCnt,
            partyCreateTime: timeago.format(new Date(data.gmtCreate),'zh_CN'),
            partyParticipantsId: data.participantsID,
            partyDetailImageUrls: data.images,
            partyMemmberCntNow:data.nowPeopleCnt
          })
        }
        console.log(_this.data.partyDetailImageUrls)
        _this.getPublisherMessage();
      },
      fail: function (res) {
        console.log(res);
      }

    });
  },
  // 获取评论列表函数
  getPartyCommentList: function (e) {
    let _this=this;
    console.log(typeof(_this.data.partyID))
    request({
      url: app.globalData.baseUrl+'/api/party-comment/commentlsit',
      method: 'POST',
      data: {
        partyId: "106",
      },
      header:{
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
       console.log(res);
       _this.setData({
         partyCommentList:res.data.data
       })
      },
      fail:function (res) {
        console.log(res);
      }
    })
  },
  onReady: function () {
    this.getPartyDetail();//获取组局详情
    this.getPartyCommentList();//获取组局评论列表
    this.popover = this.selectComponent('#popover');
  },
  // 发送评论函数
  sendComment: function (e) {
    let commentData={
      information: this.data.commentInputText,
        userId: this.data.userId,
        partyId: this.data.partyID,
        preId: this.data.partyPublisherID
    }
    request({
      url: "http://192.168.50.167:8088/api/party-comment/comment",
      method: "POST",
      data: commentData,
      success: function (res) {
        console.log(res);
        Notify({
          type: 'success',
          message: '评论成功'
        });

      },
      fail:function (res) {
        
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
      url: '../create_party/createparty?partyDetailContent=' + this.data.partyDetailContent + '&partyMemberCnt=' + this.data.partyMemmberCnt + '&operation=修改拼局'+'&partyID='+this.data.partyID,
    });
    // 调用自定义组件 popover 中的 onHide 方法
    this.popover.onHide();
  },
  // 解散组局函数
  disbandParty: function (e) {
    let _this=this;
    Dialog.confirm({
        message: '确定要解散拼局吗',
      })
      .then(() => {
        request({
          url: app.globalData.baseUrl+'/api/party/delete',
          method: "POST",
          data: {
            partyId: "106",
          },
          header:{
            'content-type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            console.log(res)
            if(res.data.status=200){
              Dialog.alert({
                message: res.data.data.message,
              }).then(() => {
                // on close
              });
              wx.navigateTo({
                url: '../index/index.wxml',
                success: (result) => {
  
                },
                fail: () => {},
                complete: () => {}
              });
            }
            else
            {
              Dialog.alert({
                message: '解散拼局失败',
              }).then(() => {
                // on close
              });
            }
            
          }
        })
        // on confirm
      })
      .catch(() => {
        // on cancel
      });
    this.popover.onHide();
  },
  showMoveOff:function(e){
    let _this=this;
    let participantsId=this.data.partyParticipantsId;
    let arr=[];
    if(_this.data.moveOffButtonText=="移除成员")
    {
      _this.setData(
        {
          moveOffButtonText:"取消"
        }
      ),
      participantsId.forEach(
        function (e) {
          if(e==_this.data.userId)
          {
            arr.push({
              participantsId:e,
              isPartyOwner:true
            })
          }
          else{
            arr.push({
              participantsId:e,
              isPartyOwner:false
            })
          }
        }
      )
      
    }
    else
    {
      _this.setData({
        moveOffButtonText:"移除成员"
      })
      participantsId.forEach(
        function (e) {
          if(e==_this.data.userId)
          {
            arr.push({
              participantsId:e,
              isPartyOwner:true
            })
          }
          else{
            arr.push({
              participantsId:e,
              isPartyOwner:false
            })
          }
        }
      )
      
    }
    _this.setData({
      partyMemberList:arr
    })
    console.log(_this.data.partyMemberList);
  },
  // 将参与者移除组局函数
  moveOffMember: function (e) {
    let _this=this;
    Dialog.confirm({
        message: '确定要移除该成员吗',
      })
      .then(() => {
        console.log(100000);
        console.log(e);
        let deleteIndex = e.currentTarget.dataset.index;
        console.log(deleteIndex);
        console.log(_this.data.partyMemberList[deleteIndex]);
        request({
          url:app.globalData.baseUrl+"/api/party-participants/moveoff",
          method:"POST",
          data:{
            partyId:_this.data.partyID,
            userId:123456
          },
          header:{
            'content-type': 'application/x-www-form-urlencoded'
          },
          success:function (res) {
            console.log(res);
            _this.moveOff(deleteIndex);
          },
          fail:function(res)
          {
            console.log(res)
          }
          
        })
      })
      .catch(() => {
        // on cancel
      });
  },
  moveOff: function (deleteIndex) {//直接回显的移除成员
    console.log(deleteIndex);
    let newMemberList = this.data.partyMemberList;
    newMemberList.splice(deleteIndex, 1);
    this.setData({
      partyMemberList: newMemberList
    });
  },
  getCommentBox: function (e) {
    this.setData({
      showCommentBox: true,
      commentMessage: e.detail
    })
    console.log(this.data.commentMessage);
    console.log(10);
  },
  bindTextAreaBlur: function (e) {
    this.setData({
      commentInputText: e.detail.value
    })
    console.log(this.data.commentInputText)
  },
  getPublisherMessage:function (e) {
    let data={
      publisherId:this.data.partyPublisherID
    };
    let _this=this;
    wx.request({
      url: app.globalData.baseUrl+"/api/posts/publishermsg",
      method:"POST",
      data:data,
      success:function (res) {
        console.log(res.data.data)
        _this.setData({
          partyPublisherMsg:res.data.data
        })
      }
    })
  },
  getValue:function (e) {
    var _this=this;
    this.setData({
      commentInputText:e.detail.value
    })
  }
})
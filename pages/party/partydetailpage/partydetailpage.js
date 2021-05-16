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
    userId: 0,
    partyID: 0,
    partyDetailContent: "你们好",
    partyDetailImageUrlS: [],
    partyPublisherID: 0,
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
    commentMessage: {},
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
    let _this = this;
    if (this.data.hasjoined == false) {
      if (this.data.partyMemmberCntNow < this.data.partyMemmberCnt) {

        request({
          url: app.globalData.baseUrl + "/api/party/join",
          method: "post",
          data: {
            partyId: 101,
            userId: 123456
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
            _this.setData({
              partyMemberList: _this.data.partyMemberList.concat({
                userName: "222",
                state: true
              }),
              partyMemmberCntNow: _this.data.partyMemmberCntNow + 1
            })
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
          partyId: 101,
          userId: 123456
        },
        success: function (res) {
          _this.setData({
            buttonContent: "加入组局",
            hasjoined: false
          })

          Notify({
            type: 'success',
            message: '退出拼局成功'
          });

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
    wx.request({
      url: 'http://ccreater.top:61112/api/party/partymes',
      method: 'GET',
      data: {
        partyId: 101
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
            partyCreateTime: data.gmtCreate,
            partyParticipantsId: data.participantsID,
            partyDetailImageUrlS: data.images,
            partyMemmberCntNow:data.nowPeopleCnt
          })
        }





        console.log(_this.data.partyMemberList);
      },
      fail: function (res) {
        console.log(res);
      }

    });
  },
  // 获取评论列表函数
  getPartyCommentList: function (e) {
    wx.request({
      url: 'http://xx.com/api/alumnicycle/party-comment/commentlsit',
      method: 'POST',
      data: {
        partyId: '1'
      },
      success: function (res) {
        this.data.partyCommentList = res.data;
      }
    })
  },
  onReady: function () {
    this.getPartyDetail();
    this.getPartyCommentList();
    this.popover = this.selectComponent('#popover');
  },
  // 发送评论函数
  sendComment: function (e) {
    wx.request({
      url: 'http://ccreater.top:61112/api/alumnicycle/party-comment/comment',
      method: "POST",
      data: {
        content: "",
        userId: this.data.userId,
        partyId: this.data.partyID,
        preId: this.commentPreId
      },
      success: function (res) {
        Notify({
          type: 'success',
          message: '评论成功'
        });
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
      url: '../create_party/createparty?partyDetailContent=' + this.data.partyDetailContent + '&partyMemberCnt=' + this.data.partyMemmberCnt + '&operation=修改拼局',
    });
    // 调用自定义组件 popover 中的 onHide 方法
    this.popover.onHide();
  },
  // 解散组局函数
  disbandParty: function (e) {
    Dialog.confirm({
        message: '确定要解散拼局吗',
      })
      .then(() => {
        request({
          url: app.globalData.baseUrl+'/api/party/delete',
          method: "POST",
          data: {
            partyId: 101
          },
          success: function (res) {
            console.log(res)
            Dialog.alert({
              message: '解散拼局成功',
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

    Dialog.confirm({
        message: '确定要移除该成员吗',
      })
      .then(() => {
        let deleteIndex = e.currentTarget.dataset.index;
        request({
          url:app.globalData.baseUrl+"/api/party-participants/moveoff",
          method:"POST",
          data:{
            partyId:_this.data.partyID,
            userId:_this.data.partyMemberList[deleteIndex]
          },
          success:function (res) {
            this.moveOff(deleteIndex);
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
  }
})
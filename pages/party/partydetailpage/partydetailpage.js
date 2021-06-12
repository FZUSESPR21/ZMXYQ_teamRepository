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
    //当前小程序使用者userid
    userId: 123456,
    partyID: "",
    partyTypeId: -2,
    partyDetailContent: "你们好",
    partyDetailImageUrls: ["https://www.fzu.edu.cn/attach/2021/04/29/419363.JPG","https://www.fzu.edu.cn/attach/2021/04/29/419363.JPG","https://www.fzu.edu.cn/attach/2021/04/29/419363.JPG","https://www.fzu.edu.cn/attach/2021/04/29/419363.JPG"],
    //组局创建者的ID
    partyPublisherID: 0,
    partyPublisherMsg:{},
    partyCreateTime: "",
    partyParticipantsId:[],
    partyMemberList: [
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

  /** 
  * 生命周期函数
  * 页面加载时将组局列表传过来的partyID赋值给data中的partyID
  */
  onLoad: function (options) {
    this.setData({
      partyID: options.partyID
    });
    this.getPartyDetail();//获取组局详情
    this.getPartyCommentList();//获取组局评论列表
    this.popover = this.selectComponent('#popover');
  },

  // 加入或退出组局函数
  joinParty: function (e) {
    //  
    // console.log(app.globalData.userInfo)
    let _this = this;
    if (this.data.hasjoined == false) {
      if (this.data.partyMemmberCntNow < this.data.partyMemmberCnt) {

        request({
          url: app.globalData.baseUrl +"/api/party/join",
          method: "post",
          data: {
            partyId: parseInt(_this.data.partyID),
          },
          header:{
            'content-type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            console.log(res);
            let memberList=_this.data.partyParticipantsId;
            memberList.push({
              participantsId:_this.data.userId,
              deletable:true
            }
            );
            _this.setData({
              buttonContent: "退出组局",
              hasjoined: true,
              partyMemberList:memberList
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
          partyId: parseInt(_this.data.partyID),
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
  },
  // 获取组局详情
  getPartyDetail: function (e) {
    let _this = this;
    request({
      url: app.globalData.baseUrl+'/api/party/partymes',
      method: 'GET',
      data: {
        partyId: parseInt(_this.data.partyID)
      },
      success: function (res) {
        let data = res.data.data;
        console.log('success-------res=\n',res);
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
            partyMemmberCntNow:data.nowPeopleCnt,
            partyTypeId: data.partyType
          })
        }
        let participantsId=_this.data.partyParticipantsId;
        let arr=[];
        participantsId.forEach(
          function (e) {
              arr.push({
                participantsId:e,
                deletable:true
              })
          }
        )
        _this.setData({
          partyMemberList:arr
        })
        // console.log(_this.data.partyDetailImageUrls)
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
        partyId: _this.data.partyID,
      },
      header:{
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
       console.log(res);
       _this.setData({
         partyCommentList:res.data.data
       })
       console.log(_this.data.partyCommentList)
      },
      fail:function (res) {
        console.log(res);
      }
    })
  },
  onReady: function () {
    
  },
  // 发送评论函数
  sendComment: function (e) {
    let _this=this;
    request({
      url: app.globalData.baseUrl+"/api/party-comment/comment",
     
      method: "POST",
      data: {
        information: _this.data.commentInputText.toString(),
          userId: parseInt (_this.data.userId),
          partyId:parseInt (_this.data.partyID),
          preId: -1
      },
      success: function (res) {
        console.log(res);
        Notify({
          type: 'success',
          message: '评论成功'
        });
        _this.getPartyCommentList()
      },
      fail:function (res) {
        console.log(res);
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
    let newFileList=[]
    this.data.partyDetailImageUrls.forEach(function(e)
    {
      newFileList.push({
        url:app.globalData.baseUrl+'/static/'+e,
        name: '图片2',
        isImage: true,
        deletable: true,
      })
    })
    console.log(newFileList);
    wx.navigateTo({
      url: '../create_party/createparty?partyDetailContent=' + this.data.partyDetailContent + '&partyMemberCnt=' + this.data.partyMemmberCnt + '&operation=修改拼局'+'&partyID='+this.data.partyID+'&fileList='+JSON.stringify(newFileList) + '&partyTypeId=' + this.data.partyTypeId,
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
        console.log(parseInt(_this.data.partyID));
        request({
          url: app.globalData.baseUrl+'/api/party/delete',
          method: "POST",
          data:parseInt(_this.data.partyID),
        
          success: function (res) {
            console.log(res)
            if(res.data.status=200){
              Dialog.alert({
                message: res.data.message,
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
      console.log(this.data.partyParticipantsId)
      participantsId.forEach(
        function (e) {
            arr.push({
              participantsId:e,
              deletable:false
            })
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
              participantsId:e.participantsId,
              deletable:true
            })
          }
          else{
            arr.push({
              participantsId:e.participantsId,
              deletable:false
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
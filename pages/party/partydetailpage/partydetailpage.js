// pages/party/partydetailpage/partydetailpage.js
import Notify from '../../../miniprogram_npm/@vant/weapp/notify/notify';
import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
import {
  request
} from "../../../utils/request"
const timeago = require("timeago.js");
const app = getApp();
const baseUrl = app.globalData.baseUrl
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
    partyDetailImageUrls: [],
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
    isPublisher: false,
    buttonContent: "加入拼局",
    commentMessage: {
     publisherName:"楼主"
    },
    commentInputText: "",
    moveOffButtonText:"移除成员",
    isFirstOnShow: true,
    // 存放图片的url(不是后缀)的数组，用于图片预览
    imageUrlsArr: [],
    // 组局成员信息列表，不包括局长的
    membersInfoArr: [],
    defaultIconUrl: '../../../../static/icons/add_post_active.png',
    memberIconUrl:  'https://i.loli.net/2021/06/14/KoVSPTc1eyCpBgE.jpg'
  },

  /** 
  * 生命周期函数
  * 页面加载时将组局列表传过来的partyID赋值给data中的partyID
  */
  onLoad: function (options) {
    this.setData({
      partyID: options.partyID
    });
    this.init();//获取组局详情，同时异步获取创建者信息
    this.getPartyCommentList();//获取组局评论列表
    this.popover = this.selectComponent('#popover');
  },

  onShow: function () {
    if(!this.data.isFirstOnShow) {
      this.getPartyDetail()
      this.updateMemberInfoArr()
    }
    // this.updateMemberInfoArr()
    this.setData({
      isFirstOnShow: false
    })
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
            _this.updateMemberInfoArr()
          },
          fail:function (e) {
            console.log(e);
          }
        })
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
          // _this.getPartyDetail();
          _this.updateMemberInfoArr()
        } 
      })
    }
  },
  // 获取组局详情
  getPartyDetail: function (e) {
    let _this = this;
    new Promise(function(resolve, reject) {
      request({
        url: app.globalData.baseUrl+'/api/party/partymes',
        method: 'GET',
        data: {
          partyId: parseInt(_this.data.partyID)
        },
        success: function (res) {
          let data = res.data.data;
          // console.log('success-------res=\n',res);
          // console.log(data);
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
          resolve(data.images);
        },
        fail: function (res) {
          console.log(res);
          reject()
        }
      });
    }).then((images) => {
      this.setData({
        imageUrlsArr: processSuffix(images)
      })
    })
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
        _this.setData({
          commentInputText:""
        })
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
    if(!this.data.isPublisher) {
      wx.showToast({
        title: '您没有权限',
        icon: 'error',
        duration: 1000
      })
    }
    else {
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
        url: '../edit_party/edit_party?partyDetailContent=' + this.data.partyDetailContent + '&partyMemberCnt=' + this.data.partyMemmberCnt + '&operation=修改拼局'+'&partyID='+this.data.partyID+'&fileList='+JSON.stringify(newFileList) + '&partyTypeId=' + this.data.partyTypeId + '&partyMemberCntnow=' + this.data.partyMemmberCntNow,
      });
      // 调用自定义组件 popover 中的 onHide 方法
    }
    this.popover.onHide();
  },
  // 解散组局函数
  disbandParty: function (e) {
    if(!this.data.isPublisher) {
      wx.showToast({
        title: '您没有权限',
        icon: 'error',
        duration: 1000
      })
    }
    else {
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
              if(res.data.status != 200){
                Dialog.alert({
                  message: '失败原因：\n' + res.data.message,
                }).then(() => {
                  wx.navigateBack({
                    delta: 0,
                  })
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
    }
    this.popover.onHide();
  },
  showMoveOff:function(e){
    let _this=this;
    let participantsId = this.data.partyParticipantsId;
    let {membersInfoArr} = this.data
    let arr=[];
    if(!this.data.isPublisher) {
      wx.showToast({
        title: '您没有权限',
        icon: 'error',
        duration: 1000
      })
    }
    else {
      if(_this.data.moveOffButtonText == "移除成员")
      {
        _this.setData(
          {
            moveOffButtonText:"取消"
          }
        ),
        membersInfoArr.forEach((item) => {
          if(item.isOccupied) {
            item.ifShow = true
          }
        })
        this.setData({
          membersInfoArr: membersInfoArr
        })
      }
      else
      {
        _this.setData({
          moveOffButtonText:"移除成员"
        })
        membersInfoArr.forEach((item) => {
          item.ifShow = false
        })
        this.setData({
          membersInfoArr: membersInfoArr
        })
        // participantsId.forEach(
        //   function (e) {
        //     if(e==_this.data.userId)
        //     {
        //       arr.push({
        //         participantsId:e.participantsId,
        //         deletable:true
        //       })
        //     }
        //     else{
        //       arr.push({
        //         participantsId:e.participantsId,
        //         deletable:false
        //       })
        //     }
        //   }
        // )
      }
    }
    // _this.setData({
    //   partyMemberList:arr
    // })
    // console.log(_this.data.partyMemberList);
  },
  // 将参与者移除组局函数
  moveOffMember: function (e) {
    let _this=this;
    Dialog.confirm({
        message: '确定要移除该成员吗',
      }).then(() => {
        console.log('确认完毕-------------')
        let index = e.currentTarget.dataset.index;
        let {membersInfoArr} = this.data
        let partyId = _this.data.partyID
        let userId = membersInfoArr[index].userId
        wx.request({
          url: baseUrl + "/api/party-participants/moveoff",
          method:"POST",
          data:{
            "partyId": partyId.toString(),
            "userId": userId.toString()
          },
          header:{
            'content-type': 'application/x-www-form-urlencoded'
          },
          success(res) {
            console.log('服务器返回----------', res);
            // _this.moveOff(deleteIndex);
            // _this.updateMemberInfoArr()
            _this.updateMemberInfoArrVerMoveoff()
          },
          fail(res) {
        console.log('收到响应-------------fail')
            console.log('删除失败-----',res.errMsg)
          }
        })
      })
      .catch((err) => {
        console.log('关闭-----------------' + err)
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

  getValue:function (e) {
    var _this=this;
    this.setData({
      commentInputText:e.detail.value
    })
  },
  /**
   * 预览图片
   */
  previewImage:function(e) {
    wx.previewImage({
      urls: this.data.imageUrlsArr,
      showmenu: true,
      current: e.currentTarget.dataset.url,
      success(res){

      },
      fail(err){
        Dialog.alert({
          message: '图片预览失败\n' + 'wx.previewImage调用失败'
        }).then(() => {

        })
        console.log('调用失败原因', err.errMsg)
      }
    })
  },
  /**
   * 首次进入详情页时：获取组局详情；获取创建者信息；获得当前用户id
   */
  init: function() {
    wx.showLoading({
      title: '加载中'
    })
    // wx.showToast({
    //   title: '加载中',
    //   icon: 'loading',
    //   duration: 1000
    // })
    let _this = this;
    new Promise(function(resolve, reject) {
      request({
        url: app.globalData.baseUrl+'/api/party/partymes',
        method: 'GET',
        data: {
          partyId: parseInt(_this.data.partyID)
        },
        success: function (res) {
          let data = res.data.data;
          // console.log('success-------res=\n',res);
          // console.log(data);
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
          resolve(data.images);
        },
        fail: function (res) {
          console.log(res);
          reject()
        }
      });
    }).then((images) => {// then
      console.log('publisherID--------', this.data.partyPublisherID)
      // 取到图片后缀，处理后缀存到imageUrlArr中
      this.setData({
        imageUrlsArr: processSuffix(images)
      })
      // 获取创建者的信息，包括年龄、头像url的后缀、昵称等
      let promise = this.getPublisherMessage()
      return promise
    }).then(() => {// then
      // 获取当前用户Id
      return this.getUserId()
    }).then(() => {// then
      // 判断是否已经加入过，是否为局长
      let {partyParticipantsId} = this.data
      partyParticipantsId.forEach((item) => {
        if(item == this.data.userId) {
          this.setData({
            hasjoined: true,
            buttonContent: '退出拼局'
          })
          if(item == this.data.partyPublisherID) {
            this.setData({
              isPublisher: true
            })
          }
        }
      })
      // 处理partyParticipantsId，获得membersInfoArr
      this.getMembersInfoArr()
    }).then(() => {
      wx.hideLoading({
        success: (res) => {},
      })
    })
  },
  /**
   * 获得组局创建者的信息
   */
  getPublisherMessage:function (e) {
    let data = {
      publisherId: parseInt(this.data.partyPublisherID)
    };
    let _this=this;
    let promise = new Promise(function(resolve, reject){
      wx.request({
        url: app.globalData.baseUrl+"/api/posts/publishermsg",
        method:"POST",
        data: data,
        success:function (res) {
          // console.log('请求参数data----------1', data)
          // console.log('返回的结果-------------1', res)
          // console.log('getPublisherMessage方法中的publisherMsg------1', res.data.data)
          _this.setData({
            partyPublisherMsg:res.data.data
          })
          resolve('222')
        }
      })
    })
    return promise
  },
  /**
   * 获得
   */
  getMembersInfoArr: function() {
    let {membersInfoArr} = this.data;
    let {partyParticipantsId} = this.data;
    let {partyMemmberCntNow} = this.data;
    let {partyMemmberCnt} = this.data;
    let {partyPublisherMsg} = this.data;
    let {userId} = this.data
    let {partyPublisherID} = this.data
    for (let i = 0; i < partyMemmberCnt; i++) {
      let member = {
        index: i,
        url: this.data.defaultIconUrl,
        userId: 0,
        // 用于判断是否 会 显示删除按钮，也可以用于判断是否是组员
        isOccupied: false,
        ifShow: false,
        isPublisher: false,
        myself: false,
        text: '成员'
      }
      // 判断：把组员先存进去
      if (i < partyMemmberCntNow) {
        // 判断：如果是局长的话
        if (partyParticipantsId[i] == partyPublisherID) {
          member.url = baseUrl + '/static/' + partyPublisherMsg.iconUrl;
          member.isPublisher = true;
          member.text = '局长';
          // 如果是自己
          if (partyParticipantsId[i] == userId) {
            member.myself = true;
            member.text = '自己'
          }
        }
        // 判断：是组员
        else {
          member = {
            index: i,
            url: this.data.memberIconUrl,
            userId: partyParticipantsId[i],
            // 用于判断是否 会 显示删除按钮，也可以用于判断是否是组员
            isOccupied: true,
            ifShow: false,
            isPublisher: false,
            myself: false,
            text: '成员'
          }
          // 如果是自己
          if (partyParticipantsId[i] == userId) {
            member.myself = true;
            member.text = '自己'
          }
        }
        membersInfoArr.push(member)
      }
      // 判断：空位的默认信息,直接将默认的配置push
      else {
        membersInfoArr.push(member)
      }
    }
    this.setData({
      membersInfoArr: membersInfoArr
    })
  },
    /**
   * 获取当前用户id
   */
  getUserId: function() {
    //缓存中的用户信息
    let storageUserInfo = null
    wx.getStorageSync({
      key: 'userInfo',
      success(res) {
        storageUserInfo = res
      }
    })
    // 如果缓存中用户信息为空，发送请求获取用户数据
    if(storageUserInfo == null) {
      let promise = new Promise(function(resolve, reject){
        wx.login({
          success: res => {
            wx.request({
              url: baseUrl + '/api/user/login',
              method: 'POST',
              data: res.code,
              success: res => {
                wx.setStorageSync({
                  key: "userInfo",
                  data: res.data.data
                })
                resolve(res.data.data)
              },
              fail:(err) => {
                Dialog.alert({
                  message: 'api/user/login接口调用失败' + err.errMsg
                }).then(() => {
                  // close
                })
                reject()
              }
            })
          },
          fail(err) {
            Dialog.alert({
              message: 'wx.login调用失败\n' + err.errMsg
            }).then(() => {
              // close
            })
            reject()
          }
        })
      }).then((userInfo) => {
        let userId = userInfo.id
        this.setData({
          userId: userId
        })
      })
      return promise
    }
    // 缓存中有用户信息，直接拿来用
    else {
      let userId
      wx.getStorageSync({
        key: 'userInfo',
        success(res) {
          userid = res.id
        }
      })
      this.setData({
        userId: userId
      })
    }
  },
  /**
   * 更新局内成员列表
   */
  updateMemberInfoArr: function() {
    let _this = this;
    new Promise(function(resolve, reject) {
      request({
        url: app.globalData.baseUrl+'/api/party/partymes',
        method: 'GET',
        data: {
          partyId: parseInt(_this.data.partyID)
        },
        success: function (res) {
          let data = res.data.data;
          // console.log('success-------res=\n',res);
          // console.log(data);
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
          resolve(data.images);
        },
        fail: function (res) {
          console.log(res);
          reject()
        }
      });
    }).then(() => {
      let {membersInfoArr} = this.data;
      let {partyParticipantsId} = this.data;
      let {partyMemmberCntNow} = this.data;
      let {partyMemmberCnt} = this.data;
      let {partyPublisherMsg} = this.data;
      let {userId} = this.data
      let {partyPublisherID} = this.data
      let i = 0

      // 索引不大于最大人数的部分
      // 每次要放进数组时，都判断这个位置是不是空位。是就push，否则赋值
      for (i = 0; i < partyMemmberCnt; i++) {
        let member = {
          index: i,
          url: this.data.defaultIconUrl,
          userId: 0,
          // 用于判断是否 会 显示删除按钮，也可以用于判断是否是组员
          isOccupied: false,
          ifShow: false,
          isPublisher: false,
          myself: false,
          text: '成员'
        }
        // 判断：把组员先存进去，再存空位
        if (i < partyMemmberCntNow) {
          // 判断：如果是局长的话
          if (partyParticipantsId[i] == partyPublisherID) {
            member.url = baseUrl + '/static/' + partyPublisherMsg.iconUrl;
            member.isPublisher = true;
            member.text = '局长';
            // 如果是自己
            if (partyParticipantsId[i] == userId) {
              member.myself = true;
              member.text = '自己'
            }
          }
          // 判断：是组员
          else {
            member = {
              index: i,
              url: this.data.memberIconUrl,
              userId: partyParticipantsId[i],
              // 用于判断是否 会 显示删除按钮，也可以用于判断是否是组员
              isOccupied: true,
              ifShow: false,
              isPublisher: false,
              myself: false,
              text: '成员'
            }
            // 如果是自己
            if (partyParticipantsId[i] == userId) {
              member.myself = true;
              member.text = '自己'
            }
          }
          if(membersInfoArr[i] == null) {
            membersInfoArr.push(member)
          }
          else {
            membersInfoArr[i] = member
          }
        }
        // 存空位
        else {
          if(membersInfoArr[i] == null) {
            membersInfoArr.push(member)
          }
          else {
            membersInfoArr[i] = member
          }
        }

      }
      // 索引大于最大人数的部分。两种情况，刚好/有空位
      // 如果不为空，去除后面多余的
      if(membersInfoArr[i] != null) {
        let length = membersInfoArr.length
        membersInfoArr = membersInfoArr.splice(i, length - partyMemmberCnt)
        this.setData({
          membersInfoArr: membersInfoArr
        })
      }
      else {
        this.setData({
          membersInfoArr: membersInfoArr
        })
      }
    })
    console.log('updateMemberInfo---------\n', this.data.membersInfoArr)
  },
    /**
   * 更新局内成员列表,MoveOff版本
   */
  updateMemberInfoArrVerMoveoff: function() {
    let _this = this;
    new Promise(function(resolve, reject) {
      request({
        url: app.globalData.baseUrl+'/api/party/partymes',
        method: 'GET',
        data: {
          partyId: parseInt(_this.data.partyID)
        },
        success: function (res) {
          let data = res.data.data;
          // console.log('success-------res=\n',res);
          // console.log(data);
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
          resolve(data.images);
        },
        fail: function (res) {
          console.log(res);
          reject()
        }
      });
    }).then(() => {
      let {membersInfoArr} = this.data;
      let {partyParticipantsId} = this.data;
      let {partyMemmberCntNow} = this.data;
      let {partyMemmberCnt} = this.data;
      let {partyPublisherMsg} = this.data;
      let {userId} = this.data
      let {partyPublisherID} = this.data
      let i = 0

      // 索引不大于最大人数的部分
      // 每次要放进数组时，都判断这个位置是不是空位。是就push，否则赋值
      for (i = 0; i < partyMemmberCnt; i++) {
        let member = {
          index: i,
          url: this.data.defaultIconUrl,
          userId: 0,
          // 用于判断是否 会 显示删除按钮，也可以用于判断是否是组员
          isOccupied: false,
          ifShow: false,
          isPublisher: false,
          myself: false,
          text: '成员'
        }
        // 判断：把组员先存进去，再存空位
        if (i < partyMemmberCntNow) {
          // 判断：如果是局长的话
          if (partyParticipantsId[i] == partyPublisherID) {
            member.url = baseUrl + '/static/' + partyPublisherMsg.iconUrl;
            member.isPublisher = true;
            member.text = '局长';
            // 如果是自己
            if (partyParticipantsId[i] == userId) {
              member.myself = true;
              member.text = '自己'
            }
          }
          // 判断：是组员
          else {
            member = {
              index: i,
              url: this.data.memberIconUrl,
              userId: partyParticipantsId[i],
              // 用于判断是否 会 显示删除按钮，也可以用于判断是否是组员
              isOccupied: true,
              ifShow: false,
              isPublisher: false,
              myself: false,
              text: '成员'
            }
            // 如果是自己
            if (partyParticipantsId[i] == userId) {
              member.myself = true;
              member.text = '自己'
            }
          }
          if(membersInfoArr[i] == null) {
            membersInfoArr.push(member)
          }
          else {
            membersInfoArr[i] = member
          }
        }
        // 存空位
        else {
          if(membersInfoArr[i] == null) {
            membersInfoArr.push(member)
          }
          else {
            membersInfoArr[i] = member
          }
        }

      }
      // 保持踢人按钮显示状态
      membersInfoArr.forEach((item) => {
        if(item.isOccupied) {
          item.ifShow = true
        }
      })
      // 索引大于最大人数的部分。两种情况，刚好/有空位
      // 如果不为空，去除后面多余的
      if(membersInfoArr[i] != null) {
        let length = membersInfoArr.length
        membersInfoArr = membersInfoArr.splice(i, length - partyMemmberCnt)
        this.setData({
          membersInfoArr: membersInfoArr
        })
      }
      else {
        this.setData({
          membersInfoArr: membersInfoArr
        })
      }
    })
    console.log('updateMemberInfo---------\n', this.data.membersInfoArr)
  }

})//Page END
function processSuffix(suffix) {
  let imageUrlsArr = []
  suffix.forEach((item) => {
    let url = baseUrl + '/static/' + item
    imageUrlsArr.push(url)
  })
  return imageUrlsArr
}
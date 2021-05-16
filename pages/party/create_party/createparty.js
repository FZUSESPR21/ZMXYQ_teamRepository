// pages/party/create_party/createparty.js
import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
import {request} from "../../../utils/request"
const timeago = require("timeago.js");
const app=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userId:123456,
    option1: [{
        text: '主题1',
        value: 0
      },
      {
        text: '主题2',
        value: 1
      },
      {
        text: '主题3',
        value: 2
      },
    ],
    themeArray: ["主题1", "主题2", "主题3", "主题4"],
    value1: 0,
    select: false,
    memNum:1,
    tihuoWay: '全部主题',
    fileList: [{
        url: 'http://iph.href.lu/60x60?text=default',
        name: '图片2',
        isImage: true,
        deletable: true,
      },


    ],
    base64fileList: [],
    partyDetailContent: "",
    buttonOperation:"创建组局(消耗50人品)",
    buttonOperationValue:1,
    imgUrls:[],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // console.log(options.partyDetailContent)
    this.setData({
      partyDetailContent: options.partyDetailContent,
      memberNum: options.partyMemberCnt,
      buttonOperation: options.operation
    })
    if(this.data.buttonOperation=="修改组局")
    {
      this.setData({
        buttonOperationValue:2
      })
    }
  },
  bindShowMsg: function () {
    this.setData({
      select: !this.data.select
    })
  },

  afterRead: function (event) {
    const _this = this;
    console.log(event.detail.file[0].url);
    this.setData({
      fileList: _this.data.fileList.concat(event.detail.file)
    });

  },
  deleteImage: function (e) {
    const index = e.detail.index; //获取到点击要删除的图片的下标
    const deletImageList = this.data.fileList //用一个变量将本地的图片数组保存起来
    deletImageList.splice(index, 1) //删除下标为index的元素，splice的返回值是被删除的元素
    this.setData({
      fileList: deletImageList
    })
  },
  addmemberOp(e) {
    if (this.data.memNum < 12) {
      this.setData({
        memNum: this.data.memNum + 1
      })
    }

  },
  delmemberOp(e) {
    if (this.data.memNum > 0) {
      this.setData({
        memNum: this.data.memNum - 1
      })
    }
  },
  mySelect(e) {
    var name = e.currentTarget.dataset.name
    this.setData({
      tihuoWay: name,
      select: false
    })
  },
  createParty: function (e) {
  if(this.data.partyDetailContent=="")//判断拼局内容是否为空
  {
    Dialog.alert({
      message: '活动详情不能为空',
    }).then(() => {
      // on close
    });
  }
  else if(this.data.memNum==0)//判断拼局人数是否为空
  {
    Dialog.alert({
      message: '拼局人数不能为空',
    }).then(() => {
      // on close
    });
  }
  else{
     let _this=this;
     console.log(this.data.memNum);
       const file = _this.data.fileList;
       let promiseArr=[];
       let imgServerUrls=new Array();
       console.log(_this.data.fileList);
       file.forEach(function (e) {
         var FSM = wx.getFileSystemManager();
         let imageType=getApp().getImageType(e.url);
         promiseArr.push(
           new Promise(function (resolve,reject) {
             FSM.readFile({
               filePath: e.url,
               encoding: "base64",
               success: function (data) {
                     wx.request({
                       url: 'http://ccreater.top:61112/api/posts/imgupload',
                       method: "POST",
                       data: {
                         base64Str: imageType + data.data,
                         filename: "111"
                       },
                       success: function (res) {
                         console.log(res);
                         console.log("上传图片成功");
                         if(res.data.code==200)
                         {
                         return resolve(res);
                         }
                         else{
                           return reject(res.data.message);
                         }
                       },
                       fail: function (e) {
                        console.log(e);
                    console.log("上传图片失败");
                         return reject(e)
                       },
                       complete: function (complete) {
          
                         return complete;
                       }
                     })
                 
              
               }
             });
           })
         )
      })
        Promise.all(promiseArr).then(function (values) {
          console.log(values);
          values.forEach(function (e) {
            console.log(e);
            imgServerUrls.push(e.data.data)
          })
    
          // console.log(imgServerUrls);
          _this.setData({
            imgUrls:imgServerUrls
          })
          console.log(_this.data.imgUrls)
        }).catch(
          reason=>{
            console.log(reason)
          }
        )
       
        if(_this.data.buttonOperationValue==1)
        {
          let createData={
            userId:_this.data.userId,
            description:_this.data.partyDetailContent,
            images:_this.data.imgUrls,
            peopleCnt:_this.data.memNum,
            partyTypeId:0
          }
          request({
          url: app.globalData.baseUrl+'/api/party/insert',
          method:"POST",
          data:createData,
          success(res)
          {
            console.log(res);
            Dialog.alert({
              message: '发布成功',
            }).then(() => {
              // on close
            });
          },
          fail:function(res)
          {
            console.log(res);
          }
  
        })
      }
      else{
        this.editParty();
      }
   
  }

  },
  editParty:function (e) {
    
    wx.request({
      url: 'http://ccreater.top:61112/api/party/update',
      method:"POST",
      data:{
        userId:this.data,
        description:"",
        imageUrls:[],
        peopleCnt:0,
        partyTypeID:0
      },
      success(res)
      {
        console.log(res);
        Dialog.alert({
          message: '发布成功',
        }).then(() => {
          // on close
        });
      },
      fail:function(res)
      {
        console.log(res);
      }

    })
  },
  getValue:function (e) {
    var _this=this;
    this.setData({
      partyDetailContent:e.detail.value
    })
  }
})
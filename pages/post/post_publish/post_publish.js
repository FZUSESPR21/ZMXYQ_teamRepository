Page({
  data: {
    fileList: [
      {
        url: 'https://img.yzcdn.cn/vant/leaf.jpg',
      },
      {
        url: 'https://img.yzcdn.cn/vant/tree.jpg',
      },
    ],
    multiArray: [['求助', '找人', '投稿','投票','租房','帮转','公告','闲置','兼职招聘','寻物/招领'], ['日常生活', '学业疑难', '求医问药', '找人帮忙', '攻略经验','求推荐','求点评','求租/借','求购']],
    multiArray1: [['求助', '找人', '投稿','投票','租房','帮转','公告','闲置','兼职招聘','寻物/招领','请设置主题'], ['日常生活', '学业疑难', '求医问药', '找人帮忙', '攻略经验','求推荐','求点评','求租/借','求购']],
    objectMultiArray: [
      [
        {
          id: 0,
          name: '求助'
        },
        {
          id: 1,
          name: '找人'
        },
        {
          id: 2,
          name: '投稿'
        },
        {
          id: 3,
          name: '投票'
        },
        {
          id: 4,
          name: '租房'
        },
        {
          id: 5,
          name: '帮转'
        },
        {
          id: 6,
          name: '公告'
        },
        {
          id: 7,
          name: '闲置'
        },
        {
          id: 8,
          name: '兼职招聘'
        },
        {
          id: 9,
          name: '寻物/招领'
        },
      ], [
        {
          id: 0,
          name: '日常生活'
        },
        {
          id: 1,
          name: '学业疑难'
        },
        {
          id: 2,
          name: '求医问药'
        },
        {
          id: 3,
          name: '找人帮忙'
        },
        {
          id: 4,
          name: '攻略经验'
        },
        {
          id: 5,
          name: '求推荐'
        },
        {
          id: 6,
          name: '求点评'
        },
        {
          id: 7,
          name: '求租/借'
        },
        {
          id: 8,
          name: '求购'
        }
      ]
    ],
    multiIndex: [10,null],
    postContent:"111",
    imgUrls:[]
  },
  bindPickerChange: function(e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index: e.detail.value
    })
  },
  bindMultiPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      multiIndex: e.detail.value
    })
  },
  bindMultiPickerColumnChange: function (e) {
    console.log('修改的列为', e.detail.column, '，值为', e.detail.value);
    var data = {
      multiArray: this.data.multiArray,
      multiIndex: this.data.multiIndex
    };
    data.multiIndex[e.detail.column] = e.detail.value;
    switch (e.detail.column) {
      case 0:
        switch (data.multiIndex[0]) {
          case 0:
            data.multiArray[1] = ['日常生活', '学业疑难', '求医问药', '找人帮忙', '攻略经验','求推荐','求点评','求租/借','求购'];
            break;
          case 1:
            data.multiArray[1] = [''];
            break;
          case 2:
            data.multiArray[1] = ['身边趣事', '创作分享', '情感','吐槽爆料','时事新闻'];
            break;
          case 3:
            data.multiArray[1] = [''];
            break;
          case 4:
            data.multiArray[1] = [''];
            break;
          case 5:
            data.multiArray[1] = ['活动', '问卷'];
             break;
          case 6:
            data.multiArray[1] = [''];
            break;
          case 7:
            data.multiArray[1] = [''];
            break;
          case 8:
            data.multiArray[1] = ['书籍资料', '电子数码','洗漱日化','鞋服包饰','代步工具','票卡转让','仙女集市','食品','体育器材','学习用品','电器家具','其他'];
            break;
          case 9:
            data.multiArray[1] = ['家教','被试','实习','全职','其他兼职',];
            break;       
          case 10:
            data.multiArray[1] = [''];
            break;                               
        }
        data.multiIndex[1] = 0;
        break;
    }
    console.log(data.multiIndex);
    this.setData(data);
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
  publishPost:function(e)
  {
    if(this.data.postContent=="")
    {
      Dialog.alert({
        message: '发布内容不能为空',
      }).then(() => {
        // on close
      });
    }
    else{
      let _this=this;
      const file = _this.data.fileList;
      let promiseArr=[];
      let imgServerUrls=new Array();
      console.log(_this.data.fileList);
      file.forEach(function (e) {
        var FSM = wx.getFileSystemManager();
        let imageType=getApp().getImageType(e.url);
        promiseArr.push(//顺序上传照片请求
          new Promise(function (resolve,reject) {
            FSM.readFile({
              filePath: e.url,
              encoding: "base64",
              success: function (data) {
                    wx.request({
                      url: 'http://192.168.5.219:8088/api/posts/imgupload',
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
          wx.request({//创建组局请求
            url: 'http://192.168.5.219:8088/api/posts/publish',
            method:'POST',
            data:{
              userId:123456,
              postTheme:1,
              message:_this.data.postContent,
              imgUrls:_this.data.imgUrls.join(";")
            },
            success:function(res)
            {
              console.log(_this.data.imgUrls)
              console.log(res);
            }
          })
        }).catch(
          reason=>{
            console.log(reason)
          }
        )
    }
  }
})

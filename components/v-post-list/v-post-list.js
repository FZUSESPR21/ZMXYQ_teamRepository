// pages/post_list/post_list.js
const app = getApp();
import {request} from "../../utils/request"
const timeago = require("timeago.js");
Component({

  /**
   * 页面的初始数据
   */
  data: {
    postsData: [],
    pageSize: 10,
    pageNum: 0,
    nowPage: 1,  //当前页面
    hidden: true, 
    timer: null,
    scrollTop: 0, 
    scrollHeight: 0,
    isLoading: false,  //防止触发多次加载
    fileList: [
      {
        url: 'https://img.yzcdn.cn/vant/leaf.jpg',
      },
      {
        url: 'https://img.yzcdn.cn/vant/tree.jpg',
      },
    ],
    multiArray: [['求助', '找人', '投稿','投票','租房','帮转','公告','闲置','兼职招聘','寻物/招领'], ['日常生活', '学业疑难', '求医问药', '找人帮忙', '攻略经验','求推荐','求点评','求租/借','求购']],
    multiArray1: [['求助', '找人', '投稿','投票','租房','帮转','公告','闲置','兼职招聘','寻物/招领','请选择主题'], ['日常生活', '学业疑难', '求医问药', '找人帮忙', '攻略经验','求推荐','求点评','求租/借','求购']],
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
    commentMessage:{},
    commentInputText:"",
    postId:0,
    showRewardBox:false,
    popularityNum:0,
    hasMark:false,
    hasLike:true,
    publisherMsg: {}
  },

    attached: function() {
      if(!app.globalData.userInfo)
      {
        let postsData = [];
        let that = this;
        app.userLogin().then(function (res) {
          let baseUrl = app.globalData.baseUrl;
          let jsonStr = '{"pageSize": 10,"pageNum": 1}';
          let jsonValue = JSON.parse(jsonStr);
          console.log(jsonValue);
          request({
            url:  baseUrl + '/api/posts/all',
            method:'POST',
            Headers: {
              'content-type': 'application/json'
            },
            data: jsonValue,
            success:function(res)
            {
              app.globalData.userInfo = "already";
             console.log("初始页面");
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

                  let publisherMsg = {};
                  publisherMsg.userName = midPostsData[i].publisherName;
                  publisherMsg.iconUrl = app.globalData.baseUrl + "/static/" + "4ef39be8ebb04b6ab81428405af7ce1b.jpeg";
                  midPostsData[i].publisherMsg = publisherMsg;
                  console.log(midPostsData[i].publisherMsg);
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
                console.log("that.data.postsData[0].imageUrls");
                console.log(that.data.postsData[0].imageUrls);
             }

             //设置高度
             wx.getSystemInfo({ 
             success: function (res) { 
             console.info(res.windowHeight); 
             that.setData({ 
                scrollHeight: res.windowHeight 
             }); 
             } 
             }); 

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
    },


  methods: {
    getPostsData(pageNum){
        this.setData({isLoading: true});
        if(pageNum == 1)
        {
          this.setData({postsData: []});
          this.setData({nowPage: 1});
        }
        let that = this;
        let baseUrl = app.globalData.baseUrl;
        let jsonStr = '{"pageSize": 10,"pageNum":' + pageNum + '}';
        console.log(pageNum);
        let jsonValue = JSON.parse(jsonStr);
        console.log(jsonValue);
        request({
          url:  baseUrl + '/api/posts/all',
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
                let publisherMsg = {};
                publisherMsg.userName = midPostsData[i].publisherName;
                publisherMsg.iconUrl = app.globalData.baseUrl + "/static/" + "4ef39be8ebb04b6ab81428405af7ce1b.jpeg";
                midPostsData[i].publisherMsg = publisherMsg;
                console.log(midPostsData[i].publisherMsg);
                console.log("***");
                //图片最终url
                for(let imageIndex = 0; imageIndex < midPostsData[i].imageUrls.length; imageIndex++){
                  midPostsData[i].imageUrls[imageIndex] = baseUrl + "/static/" + midPostsData[i].imageUrls[imageIndex];
                }
              }
              //追加数据
              that.setData({postsData: that.data.postsData.concat(midPostsData)});
              // console.log(that.data.postsData);
              //改变当前页数
              if(midPostsData.length != 0)
                that.setData(
                  {
                    nowPage: pageNum, 
                    isLoading: false
                  }
                );
            }
          },
          fail:function(res)
          {
            console.log(res);
          }
        });
    },

  //下拉刷新函数定义开始
  bindDownLoad: function () { 
      //已经触发过
      if(!this.data.isLoading){
        this.setData({isLoading: true});
        console.log("到底部了");
        let m = this.data.nowPage + 1;
        this.getPostsData(m);
      }   
    }, 

    // 下拉刷新函数定义结束
    scroll: function (e) { 
      clearTimeout(this.timer)
      if (e.detail.scrollTop < this.data.scrollTop) {     
        this.timer = setTimeout( () => {
          this.refresh()
        }, 350)
      }
    }, 

    refresh() { // 函数式触发开始下拉刷新。如可以绑定按钮点击事件来触发下拉刷新
      wx.startPullDownRefresh({
        success(errMsg) {
          console.log('开始下拉刷新', errMsg)
        },
        complete() {
          console.log('下拉刷新完毕')
        }
      })
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
              data.multiArray[1] = ['书籍资料', '电子数码','洗漱日化','鞋服包饰','代步工具','票卡转让','仙女集市','食品','体育器材','学习用品','电器家具','其他'];
              break;
            case 8:
              data.multiArray[1] = ['家教','被试','实习','全职','其他兼职',];
              break;       
            case 9:
              data.multiArray[1] = [''];
              break;                               
          }
          data.multiIndex[1] = 0;
          break;
      }
      console.log(data.multiIndex);
      this.setData(data);
    }
  }
})
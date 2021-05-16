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
    hasLike:true
  },



    attached: function() {
      if(!app.globalData.userInfo)
      {
        let postsData = this.data.postsData;
        let that = this;
        app.userLogin().then(function (res) {
          let baseUrl = app.globalData.baseUrl;
          let jsonStr = '{"pageSize": 50,"pageNum": 1}';
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

    detached: function() {
      // 在组件实例被从页面节点树移除时执行
    },

  methods: {
    getPostsData(pageNum){
        let postsData = [];
        let that = this;
        let baseUrl = app.globalData.baseUrl;
        let jsonStr = '{"pageSize": 50,"pageNum": 1}';
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
            }
          },
          fail:function(res)
          {
            console.log(res);
          }
        });
    }},

  // method: {
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
    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {
  
    },

    // getCommentBox:function(e)
    // {
    //   this.setData({
    //     commentMessage:e.detail
    //   })
    //   console.log(this.data.commentMessage);
    //   console.log(10);
    // },

    // bindTextAreaBlur:function(e)
    // {
    //   this.setData({
    //     commentInputText:e.detail.value
    //   })
    //   console.log(this.data.commentInputText)
    // },

    // getRewardBox:function(e)
    // {
    //   this.setData({
    //     showRewardBox:true
    //   })
    // },
    // onClose() {
    //   this.setData({ showRewardBox: false });
    // },


      /*获取页面数据*/
    // /**
  //  * 页面相关事件处理函数--监听用户下拉动作
  //  */
  // onPullDownRefresh() {
  //   // 上拉刷新
  //   if (!this.loading) {
  //     this. getInfoListData(1, true).then(() => {
  //       // 处理完成后，终止下拉刷新
  //       wx.stopPullDownRefresh()
  //     })
  //   }
  // },
  
  //   getInfoListData(pageNo, over) {
  //     this.loading = true
  
  //     return getArticles(pageNo).then(res => {
  //       const articles = res.items
  //       this.setData({
  //         page: pageNo,     //当前的页号
  //         pages: res.pages,  //总页数
  //         articles: over ? articles : this.data.articles.concat(articles)
  //       })
  //     }).catch(err => {
  //       console.log( err)
  //     }).then(() => {
  //       this.loading = false
  //     })
  // },

  /**
   * 页面上拉触底事件的处理函数
   */
  // onReachBottom: () =>{
  //   // 下拉触底，先判断是否有请求正在进行中
  //   // 以及检查当前请求页数是不是小于数据总页数，如符合条件，则发送请求
  //   if (!this.loading && this.data.page < this.data.pages) {
  //     this.getInfoListData(this.data.page + 1)
  //   }
  // },
  // getArticles(pageNum){
    
  // },
  // getInfoListData(pageNo, over) {
  //   this.loading = true;

  //   // 向后端请求指定页码的数据
  //   return this.getArticles(pageNo).then(res => {
  //     const articles = res.items
  //     this.setData({
  //       page: pageNo,     //当前的页号
  //       pages: res.pages,  //总页数
  //       articles: over ? articles : this.data.articles.concat(articles)
  //     })
  //   }).catch(err => {
  //     console.log( err)
  //   }).then(() => {
  //     this.loading = false
  //   })
  // },

  // /**
  //  * 用户点击右上角分享
  //  */
  // onShareAppMessage: function () {

  // },
  // goto_postdetail:function(param){
  //   wx.navigateTo({
  //     url: '../post_detail/post_detail',
  //     })
  // }
})
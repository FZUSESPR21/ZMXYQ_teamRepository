// pages/post_list/post_list.js
import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
Page({

  /**
   * 页面的初始数据
   */
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
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
 
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  goto_postdetail:function(param){
    wx.navigateTo({
      url: 'pages/post/post_detail/post_detail',
      })
  },
  
})
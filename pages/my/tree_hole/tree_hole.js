// pages/my/tree_hole/tree_hole.js
import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';

Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentId:0,
    treeHoleList:[]
  },

  deleteTreeHole:function(event) {
    let id = event.target.dataset.id;
    let that = this;
    // console.log(id);
    Dialog.confirm({
      message: '确定要删除树洞吗',
    })
        .then(() => {
          wx.request({
            url: 'http://localhost:8088/api/user/treehole/deleted',
            method:"POST",
            data:id,

            success:function(res)
            {
              that.getTreeHoleList();
              // console.log(res);
            }
          })
          // on confirm
        })
        .catch(() => {
          // on cancel
        });
  },

  goToHoleDetail(event){
    // console.log(event.target.dataset.id);
    let id = event.target.dataset.id;
    let content = event.target.dataset.content;
    this.setData({
      currentId:id,
    })
    wx.navigateTo({
      url: `../hole_detail/hole_detail?id=${id}&content=${content}`
    })
  },

  getTreeHoleList(){
    let that = this;
    wx.request({
      url:"http://localhost:8088/api/user/treehole/content",
      success(res){
        // console.log(res.data.data);
        if(res.data.code === 200){
          that.setData({
            treeHoleList:res.data.data
          })
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getTreeHoleList();
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
    this.getTreeHoleList();
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

  }
})
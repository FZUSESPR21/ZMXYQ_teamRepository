
Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentDate: new Date().getTime(),
    maxDate: new Date().getTime(),
    minDate: new Date(1950,1,1),
    show1: false,
    show2: false,
    picktime:"请选择",
    sex:"请选择",
    formatter(type, value) {
      if (type === 'year') {
        return `${value}年`;
      }
      if (type === 'month') {
        return `${value}月`;
      }
      if (type === 'day') {
        return `${value}日`;
      }
      return value;
    },
    actions: [
      {
        name: '男',
      },
      {
        name: '女',
      },
    ]
  },

  timeFormat(date, fmt) {
    var o = {
      "M+": date.getMonth() + 1,         //月份 
      "d+": date.getDate(),          //日
    };
    if (/(y+)/.test(fmt))
      fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
      if (new RegExp("(" + k + ")").test(fmt))
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;

  },

  showBirthday() {
    this.setData({ show1: true });
  },

  showSex() {
    this.setData({ show2: true });
  },

  onConfirm(event) {
    var timeValue = this.timeFormat(new Date(event.detail), "yyyy-MM-dd");
    this.setData({ picktime: timeValue, show1: false });
    var myEventDetail = {
      val: timeValue
    }
    this.triggerEvent('myevent', myEventDetail);
  },

  onCancel1() {
    this.setData({ show1: false });
  },

  onClose1() {
    this.setData({ show1: false });
  },

  onCancel2() {
    this.setData({ show2: false });
  },

  onClose2() {
    this.setData({ show2: false });
  },

  onSelect2(res){
    this.setData({
      sex:res.detail.name
    })
  },

  onInput(event) {
    this.setData({
      currentDate: event.detail,
    });
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

  }
})
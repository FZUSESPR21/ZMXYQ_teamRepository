import { areaList } from '../../../static/@vant/area-data/lib/index';
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
    show3: false,
    areaList,
    birthday:"",
    sex:"",
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
    ],
    region: [],
    UserInfo:[],

  },


  //获取用户资料
  getUserInfo(){
    let that = this;
    wx.request({
      url:"http://localhost:8088/api/user/data/select",
      success(res){
        // console.log(res);
        that.setData({
          UserInfo:res.data.data,
          sex:res.data.data.sex-0 === 1 ? '男':'女',
          region:[res.data.data.province,res.data.data.city],
          birthday:res.data.data.birthday.substring(0,10),
        })
        // console.log(that.data.UserInfo.username);
      }
    })
  },

  //时间转换
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

  showRegion() {
    this.setData({ show3: true });
  },

  onConfirm(event) {
    var timeValue = this.timeFormat(new Date(event.detail), "yyyy-MM-dd");
    this.setData({ birthday: timeValue, show1: false });
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

  onCancel3() {
    this.setData({ show3: false });
  },

  onClose3() {
    this.setData({ show3: false });
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

  confirmRegion(event){
    let midRegion = [];
    midRegion[0] = event.detail.values[0].name;
    midRegion[1] = event.detail.values[1].name;
    this.setData({
      region: midRegion,
      show3: false
    });
  },

  /*
  保存修改
  */
 handleSave(){
   console.log("保存");
 },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      // this.setData({
      //   sex: "男",
      //   birthday: "1999-09-09",
      //   region: ["福建省", "福州市"]
      // });
    this.getUserInfo();
  }
})
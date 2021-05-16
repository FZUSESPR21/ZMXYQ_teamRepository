import { areaList } from '../../../static/@vant/area-data/lib/index';
import {request} from "../../../utils/request"
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentDate: new Date().getTime(),
    maxDate: new Date().getTime(),
    minDate: new Date(1950,1,1).getTime(),
    currentArea:350100,
    originCode:1,
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
    let baseUrl = app.globalData.baseUrl;
    request({
      url: baseUrl + '/api/user/data/select',
      method:'GET',
      success(res){
        let bDay = res.data.data.user.birthday;
        // console.log(bDay.substring(0,10));
        that.setData({
          UserInfo:res.data.data.user,
          sex:res.data.data.user.sex-0 === 2 ? '女':'男',
          region:[res.data.data.user.province,res.data.data.user.city],
          birthday:bDay.substring(0,10),
          originCode:res.data.data.user.originCode-0,
        })
        // console.log(that.data.UserInfo.username);
      }
    })
  },

  //修改个人信息
  updateUserInfo(){
    let that = this;
    let baseUrl = app.globalData.baseUrl;
    // let id = this.data.currentId - 0
    // console.log(that.data.birthday.substring(8,10))
    request({
      url: baseUrl + '/api/user/data/update',
      method: 'POST',
      data: {
        sex:that.data.sex === "女" ? "2" : "1",
        // birthday:new Date(that.data.birthday.substring(0,4)-1,that.data.birthday.substring(5,7)-1,that.data.birthday.substring(8,10)-1),
        birthday:that.data.birthday,
        province:that.data.region[0],
        city:that.data.region[1],
        originCode:that.data.currentArea,
      },
      success(res){
        console.log(res);
        wx.navigateBack({
          delta:1
        })
      }
    })
  },

  //时间转换
  timeFormat(date, fmt) {
    let o = {
      "M+": date.getMonth() + 1,         //月份 
      "d+": date.getDate(),          //日
    };
    if (/(y+)/.test(fmt))
      fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let k in o)
      if (new RegExp("(" + k + ")").test(fmt))
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;

  },


  showBirthday() {
    this.setData(
        { show1: true });
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
    this.setData({
      show2: false });
  },

  onCancel3() {
    this.setData({
      show3: false });
  },

  onClose3() {
    this.setData({
      show3: false });
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
    // console.log(event);
    let midRegion = [];
    let areaCode = event.detail.values[1].code;
    midRegion[0] = event.detail.values[0].name;
    midRegion[1] = event.detail.values[1].name;
    this.setData({
      region: midRegion,
      show3: false,
      currentArea:areaCode,
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getUserInfo();
  },

  onReady: function () {
    let that = this;

    this.setData({
      currentDate: new Date(that.data.birthday.substring(0,10)).getTime(),
      currentArea: that.data.originCode,
    })
    // console.log(that.data.region)
  },

  onShow: function () {
    this.getUserInfo();
  },
})
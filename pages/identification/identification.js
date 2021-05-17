// pages/identification/identification.js
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
    //身份选择
    array_iden: ['学生','老师'],
    array_iden1: ['学生','老师','身份选择'],
    objectArray: [
      {
        id: 0,
        name: '学生'
      },
      {
        id: 1,
        name: '老师'
      }
    ],
    index1: 2,
    //是否毕业
    array_grad: ['是', '否'],
    array_grad1: ['是', '否','是否毕业'],
    objectArray: [
      {
        id: 0,
        name: '是'
      },
      {
        id: 1,
        name: '否'
      }
    ],
    index2: 2,
    //入学年份
    array_year: ['2020', '2019', '2018', '2017', '2016', '2015', '2014', '2013', '2012', '2011', '2010', '2009', '2008'
    , '2007', '2006', '2005', '2004', '2003', '2002', '2001', '2000'],
    array_year1: ['2020', '2019', '2018', '2017', '2016', '2015', '2014', '2013', '2012', '2011', '2010', '2009', '2008'
    , '2007', '2006', '2005', '2004', '2003', '2002', '2001', '2000', '入学/职年份'],
    objectArray: [
      {
        id: 0,
        name: '2020'
      },
      {
        id: 1,
        name: '2019'
      },
      {
        id: 2,
        name: '2018'
      },
      {
        id: 3,
        name: '2017'
      },
      {
        id: 4,
        name: '2016'
      },
      {
        id: 5,
        name: '2015'
      },
      {
        id: 6,
        name: '2014'
      },
      {
        id: 7,
        name: '2013'
      },
      {
        id: 8,
        name: '2012'
      },
      {
        id: 9,
        name: '2011'
      },
      {
        id: 10,
        name: '2010'
      },
      {
        id: 11,
        name: '2009'
      },
      {
        id: 12,
        name: '2008'
      },
      {
        id: 13,
        name: '2007'
      },
      {
        id: 14,
        name: '2006'
      },
      {
        id: 15,
        name: '2005'
      },
      {
        id: 16,
        name: '2004'
      },
      {
        id: 17,
        name: '2003'
      },
      {
        id: 18,
        name: '2002'
      },
      {
        id: 19,
        name: '2001'
      },
      {
        id: 20,
        name: '2000'
      },
    ],
    index3: 21,
    //认证学校
    multiArray: [['北京市', '福建省', '浙江省'], ['北京大学', '清华大学']],
    multiArray1: [['北京市', '福建省', '浙江省','请选择学校'],['北京大学', '清华大学']],
    objectMultiArray: [
      [
        {
          id: 0,
          name: '北京市'
        },
        {
          id: 1,
          name: '福建省'
        },
        {
          id: 2,
          name: '浙江省'
        },
      ], [
        {
          id: 0,
          name: '北京大学'
        },
        {
          id: 1,
          name: '清华大学'
        },
      ]
    ],
    multiIndex: [3, null],
  },
  bindPickerChange1: function(e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index1: e.detail.value
    })
  },
  bindPickerChange2: function(e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index2: e.detail.value
    })
  },
  bindPickerChange3: function(e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index3: e.detail.value
    })
  },
  bindMultiPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      multiIndex: e.detail.value
    })
  },
  bindDateChange: function(e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      date: e.detail.value
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
            data.multiArray[1] = ['北京大学', '清华大学'];
            break;
          case 1:
            data.multiArray[1] = ['厦门大学','福州大学'];
            break;
          case 2:
            data.multiArray[1] = ['浙江大学', '杭州电子科技大学'];
            break;                     
        }
        data.multiIndex[1] = 0;
        break;
    }
    console.log(data.multiIndex);
    this.setData(data);
  },
  afterRead(event) {
    const { file } = event.detail;
    // 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
    wx.uploadFile({
      url: 'https://example.weixin.qq.com/upload', // 仅为示例，非真实的接口地址
      filePath: file.url,
      name: 'file',
      formData: { user: 'test' },
      success(res) {
        // 上传完成需要更新 fileList
        const { fileList = [] } = this.data;
        fileList.push({ ...file, url: res.data });
        this.setData({ fileList });
      },
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
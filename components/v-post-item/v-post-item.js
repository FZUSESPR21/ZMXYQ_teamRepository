// components/v-post-item/v-post-component.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    eyeOnNum: Number,
    gmtCreate: String,
    imageUrls: String,
    isEyeOn: Number,
    isLike: Number,
    likeNum: Number,
    message: String,
    postId: Number,
    postType: String,
    publisherId: Number,
    pubulisherName: String,
    rewardNum: Number
  },

  /**
   * 组件的初始数据
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
    multiIndex: [10,null],
    commentMessage:{},
    commentInputText:"",
    postId:0,
    showRewardBox:false,
    popularityNum:0,
    hasMark:false,
    hasLike:true
  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})

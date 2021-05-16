// components/v-post-item/v-post-component.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    eyeOnNum: Number,
    gmtCreate: String,
    imageUrls: { type:Array, value:[] },
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
    isReward: 0
  },

  /**
   * 组件的方法列表
   */
  methods: {
  }
})

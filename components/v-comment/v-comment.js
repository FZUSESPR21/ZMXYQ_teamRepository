// pages/components/comment.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    commentList:{
      type:Array,
      value:[]
    },
  },

  /**
   * 组件的初始数据
   */
  data: {
  parentComments:[
    "1","2","3","4"
  ],
  childComments:[
    "1","2","3"
  ],
    hasMoreComments:true,
    isMoreCommentsShowed:false,
    moreButtonContent:"查看更多回复",
    iconSrc:"../../static/icons/down arrow.png",
    showInput:true,
  },

  /**
   * 组件的方法列表
   */
  methods: {
  showMoreContents:function(e)
  {
   this.setData({
     isMoreCommentsShowed:!this.data.isMoreCommentsShowed
   });
   if(this.data.isMoreCommentsShowed==true)
   {
     this.setData({
       moreButtonContent:"收起更多回复",
       iconSrc:"../../static/icons/up.png"
     })
   }
   else
   {
     this.setData({
       moreButtonContent:"查看更多回复",
       iconSrc:"../../static/icons/down arrow.png"
     })
   }
  },
  showCommentBox:function(e)
  {
    let item = {publisherName:'测试',preID:8}
    this.triggerEvent('showCommentBox',item);
  }
  }
})

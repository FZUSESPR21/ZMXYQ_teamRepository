// pages/components/comment.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    commentListData:{
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
    isMoreCommentsShowed:[],
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
    console.log(e.currentTarget.dataset.index);
    if(this.data.isMoreCommentsShowed==false)
    {
      for (let index = 0; index < this.data.commentListData.length; index++) {
        this.data.isMoreCommentsShowed[index]={
          state:false,
          moreButtonContent:"收起更多回复",
          iconSrc:"../../static/icons/up.png"
        };
      }
      this.setData({
        isMoreCommentsShowed:this.data.isMoreCommentsShowed
      })
    }
    console.log(this.data.isMoreCommentsShowed)
    this.data.isMoreCommentsShowed[e.currentTarget.dataset.index].state=!this.data.isMoreCommentsShowed[e.currentTarget.dataset.index].state;
   this.setData({
     isMoreCommentsShowed:this.data.isMoreCommentsShowed
   });
   if(this.data.isMoreCommentsShowed[e.currentTarget.dataset.index].state==true)
   {
    this.data.isMoreCommentsShowed[e.currentTarget.dataset.index].moreButtonContent="收起更多回复";
    this.data.isMoreCommentsShowed[e.currentTarget.dataset.index].iconSrc="../../static/icons/up.png"
     this.setData({
      isMoreCommentsShowed:this.data.isMoreCommentsShowed
     })
   }
   else
   {
    this.data.isMoreCommentsShowed[e.currentTarget.dataset.index].moreButtonContent="查看更多回复";
    this.data.isMoreCommentsShowed[e.currentTarget.dataset.index].iconSrc="../../static/icons/down arrow.png"
     this.setData({
      isMoreCommentsShowed:this.data.isMoreCommentsShowed
     })
   }
  },
  /**
   * 触发自定义事件'showCommentBox'
   */
  showCommentBox:function(e)
  {
    console.log('当前点击的评论-----\n', e)
    let {preid} = e.currentTarget.dataset;
    let {username} = e.currentTarget.dataset
    let item = {
      publisherName: username,
      preId: preid
    }
    this.triggerEvent('showCommentBox',item);
  },
  },
})

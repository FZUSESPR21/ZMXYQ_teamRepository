<template>
  <div class="school-card" clearfix>
            <!--卡片视图        -->
        <el-card class="box-card">
          <el-table
            :data="myData"
            border
            style="width: 100%"
            :default-sort = "{prop: 'time', order: 'descending'}"
          >
            <el-table-column
              fixed
              prop="userID"
              label="用户ID"
              width="100">
            </el-table-column>
            <el-table-column
              prop="userName"
              label="用户名"
              width="150">
            </el-table-column>
            <el-table-column
              prop="gmtCreate"
              label="申请时间"
              sortable
              width="160">
            </el-table-column>
            <el-table-column
              label="校园卡图片"
              width="500">
              <template slot-scope="scope">
                <el-image
                  :src="scope.row.imageUrls"
                  fit="contain"
                  lazy
                >
                <div slot="error" class="image-slot">
                  <span>无图片</span>
                </div>
                </el-image>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="130">
              <template slot-scope="scope">
                <el-button icon="el-icon-close"  circle style="background-color:  #ff9f80;" 
                @click="unPass(scope.row.userID, scope.$index)"></el-button>
                <el-button type="warning" icon="el-icon-check"  circle style="background-color: #80ff80;"
                @click="pass(scope.row.userID, scope.$index)"></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
        <!-- 表格定义结束 -->

        <!-- 分页定义开始 -->
        <div class="block">
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
            </el-pagination>
        </div>
        <!-- 分页定义结束 -->
        
  </div>
</template>

<script>
export default {
  name: "school-card",
  data() {
    return {
      myData:[],
      currentPage: 1,  //分页用到，当前页面
      total: 1,  //总条数
      pageSize: 6
    };
  },
  created() {
    this.getData(1);
  },
  methods: {
    //审核不通过事件响应
    unPass(id, index){
      let that = this;
      id = id.toString();
      // console.log(id);
      this.$axios.post('/authorityconfirm',
      {
        userID: id,
        pass: 1
      })
      .then((response)=>{
      // console.log(response.data);
      that.getData(that.currentPage);
      })
      .catch((response)=>{
          console.log(response);
      });
    },
    //审核通过事件响应
    pass(id, index){
      id = id.toString();
      let that = this;
      // console.log(id);
      this.$axios.post('/authorityconfirm',
      {
        userID: id,
        pass: 0
      })
      .then((response)=>{
      // console.log(response.data);
      that.getData(that.currentPage);
      })
      .catch((response)=>{
          console.log(response);
      });
    },
    getData(index){
      let that = this;
      this.$axios.post('/authority',
            {
              pageIndex: index - 1,
              order: 1
            }
            )
            .then(function (response) {
              console.log(response);
              if(response.data.code == 1){
                that.getData(index - 1);
              }
              else if(response.data.code == 0){
                that.myData = response.data.data.mes;
                that.total = response.data.data.count;
                that.currentPage = index;
                for(let i = 0; i < that.myData.length; i++){
                    that.myData[i].gmtCreate = that.formatDate(that.myData[i].gmtCreate);  //格式化日期格式
                }
              }
            })
            .catch(function (error) {
              console.log(error);
            });
    },
    //以下方法为实现分页功能
      handleCurrentChange(val) {
        this.getData(val);
        this.currentPage = val;
      }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.school-card{
  margin: 0 5%;
  max-height: 100%;
}

.box-card{
  max-height: 100%;
}

</style>

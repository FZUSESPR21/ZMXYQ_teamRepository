<template>
  <div class="post" clearfix>
            <!--卡片视图        -->
        <el-card class="box-card">
          <el-table
            :data="myData"
            border
            style="width: 100%"
            :default-sort = "{prop: 'time', order: 'descending'}"
            @row-click="getDetails"
          >
            <el-table-column
              fixed
              prop="postID"
              label="贴文ID"
              width="100">
            </el-table-column>
            <el-table-column
              prop="publisherId"
              label="发帖者ID"
              width="100">
            </el-table-column>
            <el-table-column
              prop="message"
              label="内容"
              width="250"
              style="max-width:250px"
              >
            </el-table-column>
            <el-table-column
              prop="gmtCreate"
              label="发帖时间"
              sortable
              width="160">
            </el-table-column>
            <el-table-column
              label="图片"
              width="300">
              <template slot-scope="scope">
                <el-image v-for="url in scope.row.imageUrls"  :key="url"
                  :src="url"
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
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="100"
            layout="prev, pager, next, jumper"
            :total="totalCnt">
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
      totalCnt:100  //总条数
    };
  },
  created() {
    this.getData();
  },
  methods: {
    unPass(id, index){
        this.myData.splice(index,1);
    },
    pass(id, index){
        this.myData.splice(index,1);
    },
    getData(){
        for(let i = 0; i < 10; i++)
        {
          this.myData.push(
            {           
            "postID":"12345",           
            "publisherId":"53422",    
            "publisherName":"张三",  
            "message":"阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！阿伟好帅，我好爱！",     
            "imageUrls":["https://www.fzu.edu.cn/attach/2021/04/29/419363.JPG","https://www.fzu.edu.cn/attach/2021/04/21/418037.jpg"],
            "gmtCreate":"2021/4/28 18:00:01"         
            }
          )
        }
    },
    //以下方法为实现分页功能
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
      }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.post{
  margin: 0 5%;
  max-height: 100%;
}

.box-card{
  max-height: 100%;
}

</style>
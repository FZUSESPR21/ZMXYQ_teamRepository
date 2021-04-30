<template>
  <div class="school-card" clearfix>
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
              prop="imageUrls"
              label="校园卡图片"
              width="500">
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
  </div>
</template>

<script>
export default {
  name: "school-card",
  data() {
    return {
      myData:[]
    };
  },
  created() {
    this.myData = [
          {           
          "userID":"12345",           
          "userName":"阿伟",           
          "imageUrls":"图片路径1", 
          "gmtCreate":"2021/4/28 18:00:01"         
          },  
          {           
          "userID":"123456",           
          "userName":"阿伟",           
          "imageUrls":"图片路径1", 
          "gmtCreate":"2021/4/28 18:00:01"         
          },  
          {           
          "userID":"123457",           
          "userName":"阿伟",           
          "imageUrls":"图片路径1", 
          "gmtCreate":"2021/4/28 18:00:01"         
          },  
          {           
          "userID":"123458",           
          "userName":"阿伟",           
          "imageUrls":"图片路径1", 
          "gmtCreate":"2021/4/28 18:00:01"         
          },  
          {           
          "userID":"123459",           
          "userName":"阿伟",           
          "imageUrls":"图片路径1", 
          "gmtCreate":"2021/4/28 18:00:01"         
          },  
          {           
          "userID":"123454",           
          "userName":"阿伟",           
          "imageUrls":"图片路径1", 
          "gmtCreate":"2021/4/28 18:00:01"         
          },
          {           
          "userID":"123454",           
          "userName":"阿伟",           
          "imageUrls":"图片路径1", 
          "gmtCreate":"2021/4/28 18:00:01"         
          },
          {           
          "userID":"123454",           
          "userName":"阿伟",           
          "imageUrls":"图片路径1", 
          "gmtCreate":"2021/4/28 18:00:01"         
          },
          {           
          "userID":"123454",           
          "userName":"阿伟",           
          "imageUrls":"图片路径1", 
          "gmtCreate":"2021/4/28 18:00:01"         
          }
    ]
  },
  methods: {
    unPass(id, index){
        this.myData.splice(index,1);
    },
    pass(id, index){
        this.myData.splice(index,1);
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

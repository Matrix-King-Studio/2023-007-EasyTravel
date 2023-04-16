<template>
  <div class="home">
    <Banner />
    <div class="main">
      <!-- 搜索框 -->
      <div class="search">
        <div class="mt-4 searchBar">
          <el-input
            v-model="inputText"
            placeholder="输入关键词"
            class="input-with-select"
            @input="handleSearch"
          >
            <template #append>
              <el-button :icon="Search" />
            </template>
          </el-input>
        </div>
      </div>

      <div class="carBoard">
        <img src="../assets/car.png" alt="" />
        <div class="carRoute">
          <strong>今日拼车路线</strong>
        </div>
      </div>

      <!-- 拼车展示 -->
      <div class="carList">
        <div class="carItem" v-for="item in carList" :key="item.id">
          <div class="carBar">
            <span>{{item.path}}</span>
            <!-- <span>{{ item.start }}</span>
            <div>——————</div>
            <span>{{ item.end }}</span> -->
          </div>
          <div class="carInfo">
            <p><span>出发时间：</span>{{ item.gmtModified }}</p>
            <p><span>最大乘客数：</span>{{ item.maxNum }}</p>
            <p><span>已乘人数：</span>{{ item.used }}</p>
            <p><span>价格：</span>{{ item.price }}</p>
            <p><span>司机联系方式：</span>{{ item.phone }}</p>
            <p><el-button type="success" round @click="go(item.id)">预订</el-button></p>
          </div>
        </div>
      </div>

      <FooterBar />
    </div>
  </div>
</template>

<script setup>
import Banner from "../components/Banner.vue";
import FooterBar from "../components/FooterBar.vue";
import { ElMessage } from "element-plus";
import { Search } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import { taxi, taxiCondition } from "../util/get";
const inputText = ref("");
let carList = ref([]);
const router = useRouter();
function go(id) {
  sessionStorage.getItem("userName")
    ? router.push(`carOrder/${id}`)
    : router.push("login");
}

async function handleSearch() {
  if (inputText.value === "") {
    getCars();
  } else {
    let res = await taxiCondition(inputText.value);

    if (res.reCode == "200") {
      carList.value = res.body.dataList;
      carList.value=carList.value.filter(item=>item.maxNum!==item.used)
      carList.value.forEach((item) => {
        item.start = item.path.split("—")[0];
        item.end = item.path.split("—")[1];
      });
    } else {
      ElMessage.error("未搜索到相应内容");
    }
  }
}

async function getCars() {
  let res = await taxi();
  carList.value = res.body.dataList;
  carList.value=carList.value.filter(item=>item.maxNum!==item.used)
  carList.value.forEach((item) => {
    item.start = item.path.split("—")[0];
    item.end = item.path.split("—")[1];
  });
}

onMounted(() => {
  getCars();
});
</script>
<style lang="less" scoped>
@color: #a6cdb0;
.searchBar {
  width: 60%;
  margin: 0 auto;
  margin-top: 20px;
}
.carBoard {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
.carRoute {
  background-image: linear-gradient(
    to right,
    rgba(66, 200, 181, 0) 0%,
    rgb(66, 200, 181) 90%
  );
  padding: 0px 1em;
  height: 35px;
  width: 200px;
  line-height: 35px;
  text-align: center;
  letter-spacing: 2px;
  font-size: 18px;
  color: #fff;
  box-sizing: border-box;
  word-wrap: break-word;
  text-shadow: #39c6b6 1px 1px, #39c6b6 1px -1px, #39c6b6 -1px 1px,
    #39c6b6 -1px -1px, #39c6b6 0px 1.4px, #39c6b6 0px -1.4px, #39c6b6 -1.4px 0px,
    #39c6b6 1.4px 0px, transparent 2px 2px, transparent 3px 3px,
    transparent 3px 1px, transparent 1px 3px, transparent 1px 1px,
    transparent 2px 3.4px, transparent 2px 0.6px, transparent 0.6px 2px,
    transparent 3.4px 2px;
}

.carList {
  margin: 20px auto;
  width: 60%;

  .carItem {
    border: 1px solid @color;
    border-radius: 6px;
    padding: 30px;
    background-color: #ebfff6;
    margin-top: 10px;
  }

  .carBar {
    display: flex;
    justify-content: center;
    font-weight: 800;
    font-size: 1.1rem;
    > div {
      color: @color;
      margin: 0 20px;
    }
  }
}

.carInfo {
  border: 1px solid @color;
  border-radius: 6px;
  margin: 30px;
  text-align: left;
  p {
    padding: 8px 30px;
  }
  span {
    color: @color;
  }
}

@media (max-width: 450px) {
  .carList {
    width: 90%;
    .carItem {
      padding: 10px 0;
    }
  }
}
</style>

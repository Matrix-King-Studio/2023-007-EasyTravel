<template>
  <div class="home">
    <Banner />
    <div class="main">
      <!-- 搜索框 -->
      <div class="search">
        <div class="mt-4 searchBar">
          <el-input
            v-model="inputText"
            placeholder="搜索商品"
            class="input-with-select"
            @input="handleSearch"
          >
            <template #append>
              <el-button :icon="Search" />
            </template>
          </el-input>
        </div>
      </div>

      <!-- 商品展示 -->
      <div class="goodList">
        <div class="goodItem" v-for="item in goodsList" :key="item.id">
          <img
            class="describeImg"
            :src="item.url"
          />
          <div class="goodInfo">
            <h4>{{item.goodsName}}</h4>
            <p class="description">
             {{item.introduction}}
            </p>
            <p class="price"><span>￥{{item.price}}</span></p>
            <p class="shop">{{item.shopName}}</p>
            <p>
              <el-button
                type="success"
                style="margin-top: 6px"
                @click="go(item.goodsName,item.id)"
                plain
                >购买</el-button
              >
            </p>
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
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { goods, goodsCondition } from "../util/get";
const inputText = ref("");
let goodsList = ref([]);
const router = useRouter();
function go(name,id) {
  sessionStorage.getItem("userName")
    ? router.push(`goodOrder/${name}/${id}`)
    : router.push("login");
}
//搜索
async function handleSearch() {
  if (inputText.value === "") {
    getGoods();
  } else {
    let res = await goodsCondition(inputText.value);

    if (res.reCode == "200") {
      goodsList.value = res.body.dataList;
    } else {
      ElMessage.error("未搜索到相应内容");
    }
  }
}

async function getGoods() {
  let res = await goods();
  goodsList.value = res.body.dataList;
}

onMounted(() => {
  getGoods();
});
</script>
<style lang="less" scoped>
@color: #a6cdb0;
.searchBar {
  width: 60%;
  margin: 0 auto;
  margin-top: 20px;
}

.goodList {
  width: 60%;
  margin: 0 auto;
  margin-top: 20px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  .goodItem {
    border: 1px solid #f2f2f2;
    width: 210px;
    height: 370px;
    box-sizing: border-box;
    padding: 10px;
    margin-left: 4px;
    margin-top: 20px;

    img{
      width: 180px;
    }
  }

  h4 {
    color: @color;
    margin-bottom: 4px;
  }
}

.describeImg {
  width: 198px;
  height: 198px;
}

.description {
  height: 40px;
  white-space: normal;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  font-size: 14px;
  color: #9b9b9b;
  overflow: hidden;
  line-height: 20px;
}

.price {
  color: @color;
  font-weight: 600;
  font-size: 16px;
  text-align: left;
}

.shop {
  font-size: 12px;
  color: #9b9b9b;
  text-align: left;
  padding: 4px;
}

@media (max-width: 450px) {
  
.goodList {
  width: 100%;
  .goodItem {
     width: 180px;
    height: 320px;
    img{
      width: 140px;
      height: 140px;
    }
  }

}

}
</style>

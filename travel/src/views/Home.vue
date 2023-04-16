<template>
  <div class="home">
    <Banner />
    <div class="main">
      <!-- 搜索框 -->
      <div class="search">
        <div class="mt-4 searchBar">
          <el-input
            v-model="inputText"
            placeholder="搜索景点"
            class="input-with-select"
            @input="handleSearch"
          >
            <template #append>
              <el-button :icon="Search" />
            </template>
          </el-input>
        </div>
      </div>

      <!-- 景点展示 -->
      <div class="sceneryList">
        <div class="sceneryItem" v-for="item in sceneryList" :key="item">
          <img :src="item.url" alt="" />
          <div class="sceneryItemInfo">
            <h4>{{ item.name }}</h4>
            <p class="rank">{{ item.ranking }}</p>
            <p class="introduction">
              {{ item.introduction }}
            </p>
            <p>
              <span class="special">{{ item.score }}分</span>
              {{ item.commentCnt }}条点评
            </p>
            <p class="sceneryItemInfoLast">
              <span
                >门票<span class="special">￥{{ item.price }}</span
                >起</span
              >
              <el-button
                type="success"
                style="margin-left: 10px"
                plain
                @click="sceneryOrder(item.name,item.id)"
                >预订</el-button
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
import { Search } from "@element-plus/icons-vue";
import { onMounted, reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { scenic, scenicCondition } from "../util/get";
import { ElMessage } from "element-plus";
const inputText = ref("");
const router = useRouter();
let sceneryList = ref([]);

function sceneryOrder(name,id) {
  sessionStorage.getItem('userName')?router.push(`sceneryOrder/${name}/${id}`):router.push('login')
}

async function handleSearch() {
  if (inputText.value === "") {
    Scenic();
  } else {
    let res = await scenicCondition(inputText.value);

    if (res.reCode == "200") {
      sceneryList.value = res.body.dataList;
      
    } else {
      ElMessage.error("未搜索到相应内容");
    }
  }
}

async function Scenic() {
  let res = await scenic();
  sceneryList.value = res.body.dataList;
}

onMounted(() => {
  Scenic();
});
</script>
<style lang="less" scoped>
@color: #a6cdb0;
.searchBar {
  width: 60%;
  margin: 0 auto;
  margin-top: 20px;
}

.sceneryList {
  width: 60%;
  margin: 0 auto;
  margin-top: 20px;
  display: flex;
  flex-wrap: wrap;
}

.sceneryItem {
  display: flex;
  margin: 16px;
  width: 44%;
  height: 180px;

  .introduction {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    word-break: break-all;
    -webkit-line-clamp: 2;
    overflow: hidden;
  }

  img {
    width: 160px;
    height: 180px;
  }

  .sceneryItemInfo {
    padding-left: 20px;
    color: #666;
    text-align: left;
    p {
      margin-top: 10px;
    }
    .rank {
      background-color: @color;
      color: white;
      border-radius: 6px;
      padding: 2px 6px;
    }

    .special {
      color: #f60;
    }

    .sceneryItemInfoLast {
      text-align: right;
    }
  }
}

@media (max-width: 450px) {
  .sceneryList {
    width: 100%;
  }

  .sceneryItem {
    width: 100%;
  }
}
</style>

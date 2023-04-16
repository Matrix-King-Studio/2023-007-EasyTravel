<template>
  <div class="home">
    <Banner />
    <div class="main">
      <!-- 搜索框 -->
      <div class="search">
        <div class="mt-4 searchBar">
          <el-input
            v-model="inputText"
            placeholder="搜索民宿"
            class="input-with-select"
            @input="handleSearch"
          >
            <template #append>
              <el-button :icon="Search" />
            </template>
          </el-input>
        </div>
      </div>

      <!-- 民宿展示 -->
      <div class="sceneryList">
        <div class="sceneryItem" v-for="item in houseList" :key="item">
          <div class="sceneryItemInfoLeft">
            <img :src="item.url" alt="" />
            <div class="sceneryItemInfo">
              <h4>{{ item.name }}</h4>
              <img
                class="star"
                src="http://webresource.c-ctrip.com/ResH5HotelOnline/R1/hotel_detail_icon_star4_20180824.png"
                alt=""
              />
              <img
                class="good"
                src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAFo9M/3AAAABGdBTUEAALGPC/xhBQAAAjNJREFUOBF1Uz1rVEEUPfP2bSJiOi2EEOwk+IEIKlkEXUksYm+tppFYiB8oGqxFSTRY+AOUlJpqVRS/mgg2amGw0kYJgggWuxa7b67n3JmsEcws772ZuR/n3HPvBrvWaFm7Nol4/qBphXipYfhdR4FqCOHOKyBeblg8l8zxdNNKdOq8HYQ9X4Rt2IoQr4wZujWgG/jUGNstEEb2AL0ajHvE5TeeXq/qxDhzvF+CrazQkkJSjooHXUSG7DsGLD0G8l3pABNTwNMFhLln0LJdTb64mZ9JoBgeZUTpRr3Cjv1Ap0MiJBSnDxM9IBghKrHSV3DpG2xmzB10EehgkSnooKDCao+oSY7QpaI2b3OywQrEnk0WVjFCBj03SfL4hQTBgMCqSi9tahYoBxD4WMWI6evA8jvYkwesQtijB4D6IFOxgu17EXby/OGtEy+duSyqYs2yb18dig60vXwI/PgO+/yp7yh8BVGHpqGXSRJO5TnhTDLEq+ynZJW3YLh3LXwv52RzCvLTGX/vvQhVGjw7g9cgYPcR4NZr4AabO7Ap20jNfdQVySQq/P2DumUEOHsXYeOQ4GALs7B2OzNMvopJ+nOeNbSrgxcvHrX45WN/CP+3ifduWzw1bvHkBAdUHcy1O5zUU/Z1lv36CXvR6rNJnV4VSkGqT2Kts2zxvo9IX/jqzCHpn+rJKvMP6gjGc+qM7Mybz9KLs+q6FRrZpGruMac0icMgsvGu5K+GQ2dNsoaY49/6A2+uWnf2uNHhAAAAAElFTkSuQmCC"
                alt=""
              />
              <p class="address">{{ item.address }}</p>
              <p class="tagList" >
                <el-tag v-for="jtem in item.label" :key="jtem" class="ml-2" type="success">{{ jtem }}</el-tag>
              </p>
            </div>
          </div>
          <div class="sceneryItemInfoRight">
            <div>
              <div class="sceneryItemInfoRightTop">
                <p>棒</p>
                <p>{{ item.commentCnt }}条点评</p>
              </div>
              <div class="score">{{ item.score }}</div>
            </div>
            <div class="sceneryItemInfoRightBottom">
              <p class="price">￥{{ item.price }}</p>
              <div>
                <el-button type="success" @click="houseOrder(item.name,item.id)" plain
                  >预订</el-button
                >
              </div>
            </div>
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
import { onMounted, reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { homestay, homestayCondition } from "../util/get";
const router = useRouter();
const inputText = ref("");
let houseList = ref([]);

async function handleSearch() {
  if (inputText.value === "") {
    Homestay();
  } else {
    let res = await homestayCondition(inputText.value);

    if (res.reCode == "200") {
      houseList.value = res.body.dataList;
    } else {
      ElMessage.error("未搜索到相应内容");
    }
  }
}

async function Homestay() {
  let res = await homestay();
  houseList.value = res.body.dataList;
  res.body.dataList.forEach((item) => {
    item.label = item.label.split(",");
  });

}

onMounted(() => {
  Homestay();
});

function houseOrder(name,id) {
  sessionStorage.getItem('user')
  sessionStorage.getItem('userName')?router.push(`houseOrder/${name}/${id}`):router.push('login')
}
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
  width: 100%;
  border-bottom: 2px solid #f5f7fa;
  padding: 20px 0;
  img {
    width: 160px;
  }

  .sceneryItemInfo {
    color: #666;
    text-align: left;
    border-right: 1px solid #dadfe6;
    padding: 0 20px;
    width: 370px;
    p {
      margin-top: 6px;
    }

    .star {
      width: 80px;
      margin-top: 10px;
    }

    .good {
      width: 20px;
      margin-left: 6px;
    }

    .address {
      font-size: 12px;
    }

    .tagList {
      .el-tag {
        margin-left: 6px;
        margin-top: 6px;
      }
    }

    .sceneryItemInfoLast {
      text-align: right;
    }
  }

  .sceneryItemInfoLeft {
    display: flex;
  }

  .sceneryItemInfoRight {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: flex-end;
    width: 30%;
    > div {
      display: flex;
      align-items: center;
      .score {
        background-color: @color;
        color: white;
        padding: 4px 10px;
        border-radius: 4px;
        margin-left: 8px;
      }

      .price {
        color: @color;
        font-weight: 600;
        margin-right: 10px;
        font-size: 1.5rem;
      }
    }
  }

  .sceneryItemInfoRightTop {
    p {
      text-align: right;
      &:first-child {
        color: @color;
        font-weight: 800;
      }
      &:last-child {
        font-size: 12px;
      }
    }
  }
}

@media (max-width: 450px) {
  .sceneryList {
    width: 100%;
  }

  .sceneryItem {
    width: 100%;
    img {
      height: 160px;
    }

    .star {
      height: 30px;
    }

    .good {
      height: 20px;
    }
  }

  .sceneryItem {
    flex-direction: column;
    .sceneryItemInfoRight {
      margin: 0 auto;
      width: 100%;
      margin-top: 20px;
      flex-direction: row;
      justify-content: center;
      .sceneryItemInfoRightBottom {
        margin-left: 10px;
      }
    }
  }
}
</style>

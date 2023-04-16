<template>
  <div class="banner">
    <div class="carousel">
      <div class="navBar">
        <ul>
          <li
            :class="[index == selected ? 'active' : '']"
            v-for="(item, index) in navList"
            :key="index"
            @click="go(item, index)"
          >
            <!-- <router-link :to="item.url">{{ item.title }}</router-link> -->
            <a >{{ item.title }}</a>
          </li>
        </ul>
      </div>
      <el-carousel>
        <el-carousel-item v-for="item in carouselList" :key="item">
          <div class="head">
            <div class="banner">
              <img :src="item.url" />
              <div class="banner-content">
                <p>
                  <span>——</span>
                  <span> MAKE TRAVEL MORE CONVENIENT </span>
                  <span>——</span>
                </p>
              </div>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, watchEffect } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();

let selected=ref(0)
selected.value=sessionStorage.getItem("selected") ?sessionStorage.getItem("selected"): 0//来确定当前当前路由对应的导航添加样式

let carouselList = reactive([
  {
    url: "https://1.s60i.faiusr.com/2/1078/ALYICAIQ2tXmvwUYvPifUiCADyj0Aw.jpg",
  },
  {
    url: "https://16713102.s61i.faiusr.com/4/AD0Ijov8BxAEGAAgtuDA1wUo6ZmyjwEwgA842AQ.png.webp",
  },
  {
    url: "https://16713102.s61i.faiusr.com/4/AD0Ijov8BxAEGAAg_N7A1wUoiYzRvQEwgA842AQ.png",
  },
]);
let navList = reactive([
  {
    title: "景点推荐",
    url: "/home",
    name: "Home",
  },
  {
    title: "民宿预订",
    url: "/homeStay",
    name: "HomeStay",
  },
  {
    title: "购物平台",
    url: "/shopping",
    name: "Shopping",
  },
  {
    title: "拼车平台",
    url: "/carPool",
    name: "CarPool",
  },
  {
    title: "我的订单",
    url: "/myOrder",
    name: "MyOrder",
  },
]);

function go(item, index) {
  selected.value=index
  sessionStorage.setItem("selected", index);
  router.push(item.url)
}


</script>

<style scoped lang="less">
@color: #4ab344;

/deep/ .el-carousel__container {
  height: 500px;
}

.navBar {
  position: absolute;
  top: 6%;
  right: 10%;
  z-index: 999;
  ul {
    display: flex;
    list-style: none;
    background-color: #fff;
    li {
      padding: 10px 20px;
    }
  }
}

.navBar ul a {
  color: #333;
  padding: 10px;
}

.active {
  border-bottom: 2px solid @color;
  a {
    color: @color !important;
  }
}

.banner {
  position: relative;
  height: 500px;
}

.banner-content {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  z-index: 999;
}

.banner-content p {
  text-align: center;
  color: white;
  font-size: 2.2rem;
}

.banner img {
  width: 100%;
  height: 500px;
}

@media (max-width: 450px) {
  .navBar {
    ul{
      a {
      padding: 4px;
    }
    }
  }

  .navBar ul li {
    padding: 5px;
    font-size: 12px;
  }
  /deep/ .el-carousel__container {
    height: 200px;
  }
  .banner-content p {
    font-size: 14px;
    width: 100vw;
  }
  .banner {
    height: 200px;
    img {
      height: 200px;
    }
  }
}
</style>

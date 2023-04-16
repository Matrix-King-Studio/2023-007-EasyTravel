<template>
  <div id="main">
    <div class="navBar">景点预订</div>
    <div class="houseInfo box">
      <h3>
        {{ name }}
        <span> <i class="iconfont icon-huo"></i>热度 </span>
      </h3>
      <div class="detailInfo">
        <img :src="sceneryItemInfo.sceneryItemInfoDetail.url" alt="" />
        <div>
          <p>
            <el-icon><Medal /></el-icon
            >{{ sceneryItemInfo.sceneryItemInfoDetail.ranking }}
          </p>
          <p>{{ sceneryItemInfo.sceneryItemInfoDetail.introduction }}</p>
          <p>
            <el-icon><ChatLineRound /></el-icon>评论数：{{
              sceneryItemInfo.sceneryItemInfoDetail.commentCnt
            }}
          </p>
          <p>
            <el-icon><StarFilled /></el-icon>评分：{{
              sceneryItemInfo.sceneryItemInfoDetail.score
            }}
          </p>
          <p>
            <i class="iconfont icon-shijian"></i
            ><span class="start">开园中 </span>
            {{ sceneryItemInfo.sceneryItemInfoDetail.openTime }} 开放
          </p>
        </div>
      </div>
    </div>
    <div class="chooseInfo box">
      <div>
        <h2>选择日期</h2>
        <p>
          <span class="demonstration">预订日期 &nbsp;</span>
          <el-date-picker
            v-model="sceneryOrder.time"
            type="date"
            value-format="YYYY-MM-DD"
            :disabledDate="disabledDateFn"
            placeholder="预订日期"
          />
        </p>
        <h2>选择门票个数</h2>
        <p class="houseNum">
          门票个数&nbsp;
          <el-input-number v-model="sceneryOrder.count" :min="1" :max="10" />
        </p>
        <div class="userInfo">
          <h2>需填1位游客</h2>
          <p>
            请按实际入园人数填写，姓名与证件保持一致
            <el-icon><Warning /></el-icon>
          </p>
          <p class="inputBar">
            游客姓名
            <el-input
              v-model="sceneryOrder.touristName"
              placeholder="需填1位游客姓名"
            />
          </p>
          <p class="inputBar">
            电话号码
            <el-input
              v-model="sceneryOrder.phone"
              placeholder="电话号码"
              @blur="test"
            />
          </p>
        </div>
      </div>
    </div>
    <div class="submit box">
      <div>
        <p>
          门票
          <span class="price"
            >￥{{
              sceneryItemInfo.sceneryItemInfoDetail.price * sceneryOrder.count
            }}</span
          >
        </p>
        <p class="tip">立即预订，今日无须付款</p>
      </div>
      <div>
        <el-button type="success" size="large" @click="reserve" plain
          >预订</el-button
        >
      </div>
    </div>
    <Comment
      style="margin-top: 20px"
      :Comments="Comments"
      type="scenic"
      :articleId="id"
    />
    <el-dialog v-model="dialogVisible" title="预订" width="30%">
      <span>确认预订吗？</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="sure"> 确定 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import Comment from "../components/Comment.vue";
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import { scenic, scenicDetail } from "../util/get";
import { addScenicOrder } from "../util/post";
import { checkPhone } from "../util/check";
let name = ref("");
let id = ref("");
let sceneryOrder = ref({
  time: "",
  count: 1,
  touristName: "",
  phone: "",
  scenicName: "",
});
let sceneryItemInfo = reactive({
  sceneryItemInfoDetail: {
    introduction: "",
    openTime: "",
    price: "",
    ranking: "",
    score: "",
    url: "",
    id: "",
    commentCnt: "",
  },
});
const router = useRouter();
sceneryOrder.value.scenicName = decodeURI(
  router.currentRoute.value.fullPath
).split("/")[2];

name.value = decodeURI(router.currentRoute.value.fullPath).split("/")[2];
id.value = router.currentRoute.value.params.id;

let dialogVisible = ref(false);
let Comments = reactive({
  sum: 4.7,
  subComment: [
    {
      text: "景色",
      point: 92,
    },
    {
      text: "趣味",
      point: 90,
    },
    {
      text: "性价比",
      point: 88,
    },
  ],
});

async function getScenicDetail() {
  let res = await scenicDetail(name.value);
  sceneryItemInfo.sceneryItemInfoDetail = res.body.dataInfo;
  sceneryOrder.value.scenicName = res.body.dataInfo.name;
}

function reserve() {
  dialogVisible.value = true;
}

function test() {
  if (!checkPhone(sceneryOrder.value.phone)) {
    ElMessage.error("手机号格式错误！请重新输入");
  }
}

const disabledDateFn = (time) => {
  return time.getTime() < Date.now() - 8.64e7;
};

async function sure() {
  let { time, touristName, phone } = sceneryOrder.value;
  if (time && touristName && phone) {
    if (checkPhone(sceneryOrder.value.phone)) {
      let res = await addScenicOrder(sceneryOrder.value);
      if (res.reCode == 200) {
        ElMessage({
          message: res.reMsg,
          type: "success",
        });
      } else {
        ElMessage({
          message: res.reMsg,
          type: "error",
        });
      }
    }else{
        ElMessage.error("手机号格式错误！请重新输入");
    }

    dialogVisible.value = false;
  } else {
    ElMessage({
      message: "信息未填写完整",
      type: "warning",
    });
  }

  dialogVisible.value = false;
}

onMounted(() => {
  getScenicDetail();
});
</script>

<style  lang="less" scoped>
@color: #a6cdb0;
#main {
  background-color: #f5f7fa;
}

.start {
  color: @color;
  font-weight: 800;
}

.navBar {
  background-color: @color;
  color: white;
  height: 50px;
  line-height: 50px;
  font-size: 1.4rem;
}

.box {
  width: 60%;
  margin: 0 auto;
  background-color: #ffffff;
  padding: 20px;
  border-radius: 10px;
  margin-top: 10px;
  box-sizing: border-box;
}

.el-icon {
  vertical-align: middle;
  color: @color;
  margin-right: 6px;
}

.houseInfo {
  h3 {
    margin-bottom: 10px;
    img {
      width: 70px;
      vertical-align: middle;
    }

    span {
      background: #ff7369;
      color: white;
      padding: 4px 2px;
      border-radius: 4px;
      font-size: 12px;
      margin-left: 10px;
    }
  }

  .detailInfo {
    display: flex;
    text-align: left;
    img {
      width: 200px;
      height: 200px;
    }
    > div {
      margin-left: 20px;
      p {
        margin-top: 10px;
        i {
          color: @color;
          margin-right: 10px;
        }
      }
    }
  }
}

.chooseInfo {
  .houseNum {
    margin-top: 10px;
  }

  p {
    color: #8592a6;
    margin: 10px 0;
    text-align: left;
  }

  h2 {
    text-align: left;
  }
}

.userInfo {
  text-align: left;

  .el-input {
    margin-top: 10px;
    width: 50%;
  }

  .inputBar {
    display: flex;
    flex-direction: column;
  }
}

.submit {
  display: flex;
  justify-content: space-between;
  text-align: left;

  .price {
    font-size: 24px;
    color: @color;
    font-weight: 700;
  }

  .tip {
    color: #8592a6;
    font-size: 12px;
    padding-top: 7px;
  }
}
@media (max-width: 450px) {
  .box {
    width: 90%;
    margin-top: 20px;
  }
  .detailInfo {
    flex-direction: column;
    align-items: center;
    img {
      width: 300px;
      height: 300px;
    }
  }

  /deep/.el-dialog {
    width: 80%;
  }
}
</style>

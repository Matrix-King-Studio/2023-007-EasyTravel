<template>
  <div id="main">
    <div class="navBar">民宿预订</div>
    <div class="houseInfo box">
      <h3>
        {{ name }}
        <img
          src="http://webresource.c-ctrip.com/ResH5HotelOnline/R1/hotel_detail_icon_star4_20180824.png"
          alt=""
        />
      </h3>
      <div class="detailInfo">
        <img :src="houseItemInfo.houseItemInfoDetail.url" alt="" />
        <div>
          <p class="address">
            <el-icon><Location /></el-icon
            >{{ houseItemInfo.houseItemInfoDetail.address }}
          </p>
          <p>
            {{ houseItemInfo.houseItemInfoDetail.introduction }}
          </p>
          <p>
            <el-tag
              v-for="item in houseItemInfo.houseItemInfoDetail.label"
              :key="item"
              class="ml-2"
              type="success"
              >{{ item }}</el-tag
            >
          </p>
          <p>
            <el-icon><StarFilled /></el-icon>评分：{{
              houseItemInfo.houseItemInfoDetail.score
            }}
          </p>
          <p>
            <el-icon><Avatar /></el-icon>联系人：{{
              houseItemInfo.houseItemInfoDetail.sellerName
            }}
          </p>
          <p>
            <el-icon><Cellphone /></el-icon>联系方式：{{
              houseItemInfo.houseItemInfoDetail.phone
            }}
          </p>
        </div>
      </div>
    </div>
    <div class="chooseInfo box">
      <div>
        <p>
          <span class="demonstration">入住日期 &nbsp;</span>
          <el-date-picker
            v-model="houseOrder.arrivalTime"
            :disabledDate="disabledDateFn"
            type="date"
            placeholder="入住日期"
            value-format="YYYY-MM-DD"
          />
        </p>
        <p>
          <span class="demonstration">退房日期 &nbsp;</span>
          <el-date-picker
            v-model="houseOrder.departureTime"
            :disabledDate="disabledDateFnBack"
            type="date"
            placeholder="退房日期"
            value-format="YYYY-MM-DD"
          />
        </p>
        <p class="houseNum">
          房间个数&nbsp;
          <el-input-number v-model="houseOrder.count" :min="1" :max="10" />
        </p>
        <div class="userInfo">
          <h2>住客资料</h2>
          <p>
            请按实际入住人数填写，姓名与证件保持一致
            <el-icon><Warning /></el-icon>
          </p>
          <p class="inputBar">
            住客姓名
            <el-input
              v-model="houseOrder.touristName"
              placeholder="每间房间只需填1人"
            />
          </p>
          <p class="inputBar">
            电话号码
            <el-input
              v-model="houseOrder.phone"
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
          到店付款 <span class="price">￥{{ sum }}</span>
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
      :Comments="Comments"
      type="homestay"
      :articleId="id"
      style="margin-top: 20px"
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
import { ref, reactive, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { homestay, homestayDetail } from "../util/get";
import { addHomestayOrder } from "../util/post";
import { checkPhone } from "../util/check";
const name = ref("");
let dialogVisible = ref(false);
let id = ref("");
let Comments = reactive({
  sum: 4.6,
  subComment: [
    {
      text: "位置",
      point: 96,
    },
    {
      text: "服务",
      point: 94,
    },
    {
      text: "卫生",
      point: 94,
    },
  ],
});
let houseOrder = ref({
  arrivalTime: "",
  departureTime: "",
  count: 1,
  touristName: "",
  phone: "",
  homestayName: "",
});
let houseItemInfo = reactive({
  houseItemInfoDetail: {
    introduction: "",
    address: "",
    commentCnt: "",
    price: "",
    url: "",
    score: "",
    label: "",
    sellerName: "",
    phone: "",
  },
});

const router = useRouter();
name.value = decodeURI(router.currentRoute.value.fullPath).split("/")[2];
id.value = router.currentRoute.value.params.id;

//获得民宿详情
async function getHomestayDetail() {
  let res = await homestayDetail(name.value);
  houseItemInfo.houseItemInfoDetail = res.body.dataInfo;
  houseOrder.value.homestayName = res.body.dataInfo.name;
  houseItemInfo.houseItemInfoDetail.label =
    houseItemInfo.houseItemInfoDetail.label.split(",");
}

const disabledDateFn = (time) => {
  return time.getTime() < Date.now() - 8.64e7;
};

const disabledDateFnBack = (time) => {
  return time.getTime() < new Date(houseOrder.value.arrivalTime).getTime();
};

onMounted(() => {
  getHomestayDetail();
});

//计算价格
let sum = computed(() => {
  if (houseOrder.value.arrivalTime && houseOrder.value.departureTime) {
    let dayNum = calc_day(
      new Date(houseOrder.value.departureTime).getTime(),
      new Date(houseOrder.value.arrivalTime).getTime()
    );
    return (
      dayNum * houseOrder.value.count * houseItemInfo.houseItemInfoDetail.price
    );
  } else {
    return houseOrder.value.count * houseItemInfo.houseItemInfoDetail.price;
  }
});

//将日期转换为时间戳计算入住天数
function calc_day(timestamp1, timestamp2) {
  var differ = timestamp1 - timestamp2;
  return parseInt(differ / 1000 / 60 / 60 / 24);
}

function reserve() {
  dialogVisible.value = true;
}

function test() {
  if (!checkPhone(houseOrder.value.phone)) {
    ElMessage.error("手机号格式错误！请重新输入");
  }
}

//预定
async function sure() {
  let { arrivalTime, departureTime, touristName, phone } = houseOrder.value;
  if (arrivalTime && departureTime && touristName && phone) {
    if (checkPhone(houseOrder.value.phone)) {
      let res = await addHomestayOrder(houseOrder.value);
      if (res.reCode == 200) {
        ElMessage.success(res.reMsg);
      } else {
        ElMessage.error(res.reMsg);
      }
    } else {
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
</script>

<style  lang="less" scoped>
@color: #a6cdb0;
#main {
  background-color: #f5f7fa;
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

.el-tag {
  margin: 4px;
}

.houseInfo {
  h3 {
    margin-bottom: 10px;
    img {
      width: 70px;
      vertical-align: middle;
    }
  }

  .detailInfo {
    display: flex;
    text-align: left;
    img {
      width: 260px;
      height: 260px;
    }
    > div {
      margin-left: 10px;
      p {
        margin-top: 10px;
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

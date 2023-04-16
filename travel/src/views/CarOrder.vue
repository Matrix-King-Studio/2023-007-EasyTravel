<template>
  <div id="main">
    <div class="navBar">拼车预订</div>
    <div class="houseInfo box">
      <h3>
        {{ carItemInfo.carInfoDetail.start }}到{{
          carItemInfo.carInfoDetail.end
        }}
        出发时间：{{ carItemInfo.carInfoDetail.gmtModified }}
      </h3>
      <div class="detailInfo">
        <div>
          <p>
            <i class="iconfont icon-shijian"></i
            >{{ carItemInfo.carInfoDetail.gmtModified }}
          </p>
          <p>
            <i class="iconfont icon-xiaoche"></i>
            {{ carItemInfo.carInfoDetail.start }}
            <i class="iconfont icon-doubleHorizontalArrow"></i>
            {{ carItemInfo.carInfoDetail.end }}
          </p>
          <p>
            <el-icon><Avatar /></el-icon>最大乘客数：{{
              carItemInfo.carInfoDetail.maxNum
            }}
          </p>
          <p>
            <el-icon><Avatar /></el-icon>已乘人数：{{
              carItemInfo.carInfoDetail.used
            }}
          </p>
          <p>
            <i class="iconfont icon-xiaoche"></i> 车型：{{
              carItemInfo.carInfoDetail.carType
            }}
          </p>
          <p>
            <el-icon><Cellphone /></el-icon>联系方式：{{
              carItemInfo.carInfoDetail.phone
            }}
          </p>
        </div>
      </div>
    </div>
    <div class="chooseInfo box">
      <div>
        <h2>预约座位数目</h2>
        <p class="houseNum">
          预约个数&nbsp;
          <el-input-number
            v-model="carOrder.count"
            :min="1"
            :max="
              carItemInfo.carInfoDetail.maxNum - carItemInfo.carInfoDetail.used
            "
          />
        </p>
        <div class="userInfo">
          <h2>乘客资料</h2>
          <p>
            请按实际乘坐人数填写，姓名与证件保持一致
            <el-icon><Warning /></el-icon>
          </p>
          <p class="inputBar">
            乘客姓名
            <el-input v-model="carOrder.passageName" placeholder="只需填1人" />
          </p>
          <p class="inputBar">
            联系电话
            <el-input v-model="carOrder.phone" placeholder="联系电话" @blur="test"/>
          </p>
        </div>
      </div>
    </div>
    <div class="submit box">
      <div>
        <p>
          需付款
          <span class="price"
            >￥{{ carItemInfo.carInfoDetail.price * carOrder.count }}</span
          >
        </p>
        <p class="tip">立即预订，优先选择位置</p>
      </div>
      <div>
        <el-button type="success" size="large" @click="reserve" plain
          >预订</el-button
        >
      </div>
    </div>
    <Comment
      :Comments="Comments"
      type="taxi"
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
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import { addTaxiOrder } from "../util/post";
import { taxi, taxiDetail } from "../util/get";
import { checkPhone } from "../util/check";
let id = ref("");
let carOrder = ref({
  count: 1,
  passageName: "",
  phone: "",
  id: "",
});
let carItemInfo = reactive({
  carInfoDetail: {
    introduction: "",
    openTime: "",
    price: "",
    ranking: "",
    score: "",
    url: "",
    commentCnt: "",
  },
});
let dialogVisible = ref(false);
let Comments = reactive({
  sum: 4.6,
  subComment: [
    {
      text: "态度",
      point: 96,
    },
    {
      text: "卫生",
      point: 98,
    },
    {
      text: "舒适",
      point: 90,
    },
  ],
});
const router = useRouter();
console.log(router.currentRoute.value.params.id);
id.value=router.currentRoute.value.params.id
async function getCarDetail() {
  let res = await taxiDetail(id.value);
  carItemInfo.carInfoDetail = res.body.dataInfo;
  carItemInfo.carInfoDetail.start =
    carItemInfo.carInfoDetail.path.split("—")[0];
  carItemInfo.carInfoDetail.end = carItemInfo.carInfoDetail.path.split("—")[1];
  carOrder.value.id = res.body.dataInfo.id;
}

onMounted(() => {
  getCarDetail();
});

function reserve() {
  dialogVisible.value = true;
}

function test() {
  if(!checkPhone(carOrder.value.phone)){
    ElMessage.error('手机号格式错误！请重新输入')
  }
}

async function sure() {
  let { passageName, phone } = carOrder.value;
  if (passageName && phone) {

    if (checkPhone(carOrder.value.phone)) {
       let res = await addTaxiOrder(carOrder.value);
      if (res.reCode == 200) {
        ElMessage.success(res.reMsg);
        getCarDetail()
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

i {
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
    > div {
      margin-left: 10px;
      p {
        margin-top: 10px;
      }
    }
  }
}

.chooseInfo {
  h2 {
    text-align: left;
  }
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

<template>
  <div id="main">
    <div class="navBar">地方特产</div>
    <div class="houseInfo box">
      <h3>
        {{ name }}
        <span><a href="" class="ww-online"></a></span>
      </h3>
      <div class="detailInfo">
        <img :src="goodsItemInfo.goodsInfoDetail.url" alt="" />
        <div>
          <p>{{ goodsItemInfo.goodsInfoDetail.introduction }}</p>
          <p style="font-size: 12px">
            价保服务 假一赔四 极速退款 七天内无理由退换
          </p>
          <p style="font-size: 12px">正品保障 支持退换 请放心购买</p>
          <p style="font-size: 12px">运送方式：普通配送 快递 免邮</p>
          <p>
            <el-icon><ChatLineRound /></el-icon>评论数：{{
              goodsItemInfo.goodsInfoDetail.commentCnt
            }}
          </p>
          <p>
            <i class="iconfont icon-dianpu"></i
            >{{ goodsItemInfo.goodsInfoDetail.shopName }}
          </p>
        </div>
      </div>
    </div>
    <div class="chooseInfo box">
      <div>
        <h2>选择商品个数</h2>
        <p class="houseNum">
          商品数量&nbsp;
          <el-input-number
            v-model="goodsOrder.count"
            :min="1"
            :max="10"
          />
        </p>
        <div class="userInfo">
          <h2>填写信息</h2>
          <p>
            填写信息本网站会严格保密，请您放心，联系方式和收货地址请确认无误！
            <el-icon><Warning /></el-icon>
          </p>
          <p class="inputBar">
            收货人
            <el-input v-model="goodsOrder.consignee" placeholder="收货人" />
          </p>
          <p class="inputBar">
            联系方式
            <el-input
              v-model="goodsOrder.phone"
              placeholder="联系方式"
              @blur="test"
            />
          </p>
          <p class="inputBar">
            收货地址
            <el-input
              v-model="goodsOrder.shippingAddress"
              :rows="2"
              type="textarea"
              placeholder="收货地址"
              resize="none"
              style="margin-top: 10px"
            />
          </p>
        </div>
        <img
          src="https://img.alicdn.com/tfs/TB14dANRXXXXXbdXXXXXXXXXXXX-1190-100.jpg"
          alt=""
          class="tmall-ensure"
        />
      </div>
    </div>
    <div class="submit box">
      <div>
        <p>
          价格
          <span class="price"
            >￥{{
              goodsItemInfo.goodsInfoDetail.price * goodsOrder.count
            }}</span
          >
        </p>
        <p class="tip">立即购买，3日内发货</p>
      </div>
      <div>
        <el-button type="success" size="large" @click="reserve" plain
          >购买</el-button
        >
      </div>
    </div>
    <Comment
      style="margin-top: 20px"
      :Comments="Comments"
      type="goods"
      :articleId="id"
    />
    <el-dialog v-model="dialogVisible" title="购买" width="30%">
      <span>确认购买吗？</span>
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
import { goods, goodsDetail } from "../util/get";
import { addGoodsOrder } from "../util/post";
import { useRouter } from "vue-router";
import { checkPhone } from "../util/check";
const name = ref("");
let id = ref("");
let dialogVisible = ref(false);
const router = useRouter();
let Comments = reactive({
  sum: 4.8,
  subComment: [
    {
      text: "商品描述",
      point: 92,
    },
    {
      text: "卖家服务",
      point: 98,
    },
    {
      text: "物流服务",
      point: 98,
    },
  ],
});
let goodsOrder = ref({
  count: 1,
  consignee: "",
  phone: "",
  shippingAddress: "",
  goodsName: "",
});
let goodsItemInfo = reactive({
  goodsInfoDetail: {
    introduction: "",
    shopName: "",
    commentCnt: "",
    price: "",
    url: "",
  },
});

name.value = decodeURI(router.currentRoute.value.fullPath).split("/")[2];
id.value = router.currentRoute.value.params.id;
onMounted(() => {
  getGoodsDetail();
});

//获得商品详情
async function getGoodsDetail() {
  let res = await goodsDetail(name.value);
  goodsItemInfo.goodsInfoDetail = res.body.dataInfo;
  goodsOrder.value.goodsName = res.body.dataInfo.goodsName;
}

//预定
async function sure() {
  let { consignee, phone, shippingAddress } = goodsOrder.value;
  if (consignee && phone && shippingAddress) {
    if (checkPhone(goodsOrder.value.phone)) {
      let res = await addGoodsOrder(goodsOrder.value);
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

function reserve() {
  dialogVisible.value = true;
}

function test() {
  if (!checkPhone(goodsOrder.value.phone)) {
    ElMessage.error("手机号格式错误！请重新输入");
  }
}

</script>

<style  lang="less" scoped>
@color: #a6cdb0;
#main {
  background-color: #f5f7fa;
}
.ww-online {
  background-image: url(//img.alicdn.com/tps/i1/T15AD7FFFaXXbJnvQ_-130-60.gif);
  width: 20px;
  height: 20px;
  background-position: -80px 0;
  display: inline-block;
  vertical-align: text-bottom;
}

.tmall-ensure {
  width: 100%;
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

    span {
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

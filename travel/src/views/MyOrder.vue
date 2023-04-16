<template>
  <div class="home">
    <Banner />
    <div class="main">
      <!-- 订单展示 -->
      <div class="changeBar">
        <el-tabs v-model="activeName" class="demo-tabs" type="border-card">
          <el-tab-pane label="景点" name="first">
            <div class="container" v-if="sceneryList != ''">
              <table>
                <colgroup>
                  <col />
                  <col />
                  <col />
                  <col />
                  <col />
                </colgroup>
                <thead>
                  <tr>
                    <th>
                      <div>
                        <span>景点信息 </span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>单价</span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>数量</span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>总金额</span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>操作 </span>
                      </div>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    class="info-msg"
                    v-for="item in sceneryList"
                    :key="item.id"
                  >
                    <td>
                      <div
                        class="infoDetail"
                        @click="go(item.id, item.name, 'scenic')"
                      >
                        <img :src="item.url" alt="" />
                        <div style="margin-left: 10px">
                          <p>{{ item.name }}</p>
                          <p style="font-size: 12px; color: #909399">
                            {{ item.detail }}
                          </p>
                        </div>
                      </div>
                    </td>

                    <td>
                      <div>
                        <span>￥{{ item.unitPrice }}</span>
                      </div>
                    </td>

                    <td>
                      <div>
                        <span>{{ item.count }}</span>
                      </div>
                    </td>

                    <td>
                      <div>
                        <span>￥{{ item.price }}</span>
                      </div>
                    </td>

                    <td>
                      <div class="operate">
                        <span
                          ><el-button
                            type="warning"
                            @click="change1(item.detail, item.id)"
                            >更改</el-button
                          ></span
                        >
                        <span
                          ><el-button
                            type="danger"
                            @click="unsubscribe(item.id)"
                            >退订</el-button
                          ></span
                        >
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div v-else class="noOrder">暂无订单</div>
          </el-tab-pane>
          <el-tab-pane label="民宿" name="second">
            <div class="container" v-if="houseList != ''">
              <table>
                <colgroup>
                  <col />
                  <col />
                  <col />
                  <col />
                  <col />
                </colgroup>
                <thead>
                  <tr>
                    <th>
                      <div>
                        <span>民宿信息 </span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>单价</span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>数量</span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>总金额</span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>操作</span>
                      </div>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="item in houseList" :key="item.id">
                    <td class="info-msg">
                      <div
                        class="infoDetail"
                        @click="go(item.id, item.name, 'homestay')"
                      >
                        <img :src="item.url" alt="" />
                        <div style="margin-left: 10px">
                          <p>{{ item.name }}</p>
                          <p
                            style="
                              margin-top: 10px;
                              color: #9e9e9e;
                              font-size: 12px;
                            "
                          >
                            {{ item.detail }}
                          </p>
                        </div>
                      </div>
                    </td>

                    <td>
                      <div>
                        <span>￥{{ item.unitPrice }}</span>
                      </div>
                    </td>

                    <td>
                      <div>
                        <span>{{ item.count }}</span>
                      </div>
                    </td>

                    <td>
                      <div>
                        <span>￥{{ item.price }}</span>
                      </div>
                    </td>

                    <td>
                      <div class="operate">
                        <span
                          ><el-button
                            type="warning"
                            @click="change2(item.detail, item.id)"
                            >更改</el-button
                          ></span
                        >
                        <span
                          ><el-button
                            type="danger"
                            @click="unsubscribe(item.id)"
                            >退订</el-button
                          ></span
                        >
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div v-else class="noOrder">暂无订单</div>
          </el-tab-pane>
          <el-tab-pane label="购物" name="third">
            <div class="container" v-if="goodsList != ''">
              <table>
                <colgroup>
                  <col />
                  <col />
                  <col />
                  <col />
                  <col />
                </colgroup>
                <thead>
                  <tr>
                    <th>
                      <div>
                        <span>购物信息 </span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>单价</span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>数量</span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>总金额</span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>操作</span>
                      </div>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="item in goodsList" :key="item.id">
                    <td class="info-msg">
                      <div
                        class="infoDetail"
                        @click="go(item.id, item.name, 'goods')"
                      >
                        <img :src="item.url" alt="" />
                        <div style="margin-left: 10px">
                          <p>{{ item.name }}</p>
                          <p
                            style="
                              margin-top: 10px;
                              color: #9e9e9e;
                              font-size: 12px;
                            "
                          >
                            {{ item.detail }}
                          </p>
                        </div>
                      </div>
                    </td>

                    <td>
                      <div>
                        <span>￥{{ item.unitPrice }}</span>
                      </div>
                    </td>

                    <td>
                      <div>
                        <span>{{ item.count }}</span>
                      </div>
                    </td>

                    <td>
                      <div>
                        <span>￥{{ item.price }}</span>
                      </div>
                    </td>

                    <td>
                      <div class="operate">
                        <span
                          ><el-button
                            type="warning"
                            @click="change3(item.detail, item.id)"
                            >更改</el-button
                          ></span
                        >
                        <span
                          ><el-button
                            type="danger"
                            @click="unsubscribe(item.id)"
                            >退订</el-button
                          ></span
                        >
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div v-else class="noOrder">暂无订单</div>
          </el-tab-pane>
          <el-tab-pane label="拼车" name="fourth">
            <div class="container" v-if="carList != ''">
              <table>
                <colgroup>
                  <col />
                  <col />
                  <col />
                  <col />
                  <col />
                </colgroup>
                <thead>
                  <tr>
                    <th>
                      <div>
                        <span>拼车信息 </span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>单价</span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>数量</span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>总金额</span>
                      </div>
                    </th>
                    <th>
                      <div>
                        <span>操作</span>
                      </div>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="item in carList" :key="item.id">
                    <td class="info-msg">
                      <div
                        class="infoDetail"
                        @click="go(item.carId, item.name, 'taxi')"
                      >
                        <div style="margin-left: 10px">
                          <p>{{ item.path }}</p>
                          <p
                            style="
                              margin-top: 10px;
                              color: #9e9e9e;
                              font-size: 12px;
                            "
                          >
                            出发时间：{{ item.gmtModified }}
                          </p>
                          <p
                            style="
                              margin-top: 10px;
                              color: #9e9e9e;
                              font-size: 12px;
                            "
                          >
                            {{ item.detail }}
                          </p>
                        </div>
                      </div>
                    </td>

                    <td>
                      <div>
                        <span>￥{{ item.unitPrice }}</span>
                      </div>
                    </td>

                    <td>
                      <div>
                        <span>{{ item.count }}</span>
                      </div>
                    </td>

                    <td>
                      <div>
                        <span>￥{{ item.price }}</span>
                      </div>
                    </td>

                    <td>
                      <div class="operate">
                        <span
                          ><el-button
                            type="warning"
                            @click="change4(item.detail, item.id)"
                            >更改</el-button
                          ></span
                        >
                        <span
                          ><el-button
                            type="danger"
                            @click="unsubscribe(item.id)"
                            >退订</el-button
                          ></span
                        >
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div v-else class="noOrder">暂无订单</div></el-tab-pane
          >
        </el-tabs>
      </div>

      <!-- 更改景点 -->
      <el-dialog v-model="dialogVisible1" title="修改景点订单" width="30%">
        <el-form
          label-width="100px"
          :model="sceneryOrder"
          style="max-width: 460px"
        >
          <el-form-item label="预定日期">
            <el-date-picker
              v-model="sceneryOrder.time"
              :disabledDate="disabledDateFn"
              type="date"
              format="YYYY/MM/DD"
              value-format="YYYY-MM-DD"
              placeholder="预定日期"
            />
          </el-form-item>
          <el-form-item label="游客姓名">
            <el-input v-model="sceneryOrder.touristName" />
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input v-model="sceneryOrder.phone" @blur="test('scenic')" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible1 = false">取消</el-button>
            <el-button type="primary" @click="sureScenery"> 确定 </el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 更改民宿 -->
      <el-dialog v-model="dialogVisible2" title="修改民宿订单" width="30%">
        <el-form
          label-width="100px"
          :model="houseOrder"
          style="max-width: 460px"
        >
          <el-form-item label="住客姓名">
            <el-input v-model="houseOrder.touristName" />
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input v-model="houseOrder.phone" @blur="test('homestay')" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible2 = false">取消</el-button>
            <el-button type="primary" @click="sureHouse"> 确定 </el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 更改物品 -->
      <el-dialog v-model="dialogVisible3" title="修改购物订单" width="30%">
        <el-form
          label-width="100px"
          :model="goodsOrder"
          style="max-width: 460px"
        >
          <el-form-item label="收货人">
            <el-input v-model="goodsOrder.consignee" />
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input v-model="goodsOrder.phone" @blur="test('goods')" />
          </el-form-item>
          <el-form-item label="收货地址">
            <el-input
              v-model="goodsOrder.shippingAddress"
              :rows="2"
              type="textarea"
              resize="none"
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible3 = false">取消</el-button>
            <el-button type="primary" @click="sureGoods"> 确定 </el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 更改拼车 -->
      <el-dialog v-model="dialogVisible4" title="修改拼车订单" width="30%">
        <el-form
          label-width="100px"
          :model="taxiOrder"
          style="max-width: 460px"
        >
          <el-form-item label="姓名">
            <el-input v-model="taxiOrder.passageName" />
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input v-model="taxiOrder.phone" @blur="test('taxi')" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible4 = false">取消</el-button>
            <el-button type="primary" @click="sureTaxi"> 确定 </el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 退订 -->
      <el-dialog v-model="dialogVisiblenot" title="退订" width="30%">
        <span>确认退订吗？</span>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisiblenot = false">取消</el-button>
            <el-button type="primary" @click="sureReturn"> 确定 </el-button>
          </span>
        </template>
      </el-dialog>

      <FooterBar />
    </div>
  </div>
</template>

<script setup>
import Banner from "../components/Banner.vue";
import FooterBar from "../components/FooterBar.vue";
import { ElMessageBox, ElMessage } from "element-plus";
import { ref, reactive, onMounted } from "vue";
import { useRouter } from "vue-router";
import { orderList } from "../util/post";
import { checkPhone } from "../util/check";
import {
  scenicChange,
  homestayChange,
  goodsChange,
  taxiChange,
} from "../util/put";
import { deleteOrder } from "../util/delete";
const router = useRouter();
const activeName = ref("first");
let dialogVisible1 = ref(false);
let dialogVisiblenot = ref(false);
let dialogVisible2 = ref(false);
let dialogVisible3 = ref(false);
let dialogVisible4 = ref(false);
let sceneryList = ref([]);
let houseList = ref([]);
let goodsList = ref([]);
let carList = ref([]);
let orderId = ref("");
let type = ref("");

let sceneryOrder = ref({
  time: "",
  touristName: "",
  phone: "",
  indentId: 0,
});
let houseOrder = ref({
  touristName: "",
  phone: "",
  indentId: 0,
});
let goodsOrder = ref({
  consignee: "",
  shippingAddress: "",
  phone: "",
  indentId: 0,
});
let taxiOrder = ref({
  passageName: "",
  phone: "",
  indentId: 0,
});

async function getOrderList() {
  let {
    body: { dataList },
  } = await orderList();
  sceneryList.value = dataList[0];
  houseList.value = dataList[1];
  goodsList.value = dataList[2];
  carList.value = dataList[3];
}

const disabledDateFn = (time) => {
  return time.getTime() < Date.now() - 8.64e7;
};

function test(type) {
  switch (type) {
    case "scenic":
      if (!checkPhone(sceneryOrder.value.phone)) {
        ElMessage.error("手机号格式错误！请重新输入");
      }
      break;
    case "homestay":
      if (!checkPhone(houseOrder.value.phone)) {
        ElMessage.error("手机号格式错误！请重新输入");
      }
      break;
    case "goods":
      if (!checkPhone(goodsOrder.value.phone)) {
        ElMessage.error("手机号格式错误！请重新输入");
      }
      break;
    case "taxi":
      if (!checkPhone(taxiOrder.value.phone)) {
        ElMessage.error("手机号格式错误！请重新输入");
      }
      break;
  }
}

onMounted(() => {
  getOrderList();
});

const formLabelAlign = reactive({
  name: "",
  region: "",
  type: "",
});

//跳转
function go(id, name, type) {
  switch (type) {
    case "scenic":
      router.push(`sceneryOrder/${name}/${id}`);
      break;
    case "homestay":
      router.push(`houseOrder/${name}/${id}`);
      break;
    case "goods":
      router.push(`goodOrder/${name}/${id}`);
      break;
    case "taxi":
      router.push(`carOrder/${id}`);
      break;
  }
}

//获取景点订单详情
function change1(detail, id) {
  sceneryOrder.value.indentId = id;
  detail.split(";").forEach((item) => {
    switch (item.split(":")[0]) {
      case "游客姓名":
        sceneryOrder.value.touristName = item.split(":")[1];
        break;
      case "门票日期":
        sceneryOrder.value.time = item.split(":")[1];
        break;
      case "游客电话":
        sceneryOrder.value.phone = item.split(":")[1];
        break;
    }
  });
  dialogVisible1.value = true;
}

//修改景点订单
async function sureScenery() {
  if (checkPhone(sceneryOrder.value.phone)) {
    let res = await scenicChange(sceneryOrder.value);
    if (res.reCode === "200") {
      ElMessage.success(res.reMsg);
    }
    getOrderList();
  }else{
     ElMessage.error("手机号格式错误！请重新输入");
  }

  dialogVisible1.value = false;
}

//获取民宿订单详情
function change2(detail, id) {
  houseOrder.value.indentId = id;
  detail.split(";").forEach((item) => {
    switch (item.split(":")[0]) {
      case "住客姓名":
        houseOrder.value.touristName = item.split(":")[1];
        break;
      case "电话号码":
        houseOrder.value.phone = item.split(":")[1];
        break;
    }
  });
  dialogVisible2.value = true;
}

//修改景点订单
async function sureHouse() {
 if(checkPhone(houseOrder.value.phone)){
   let res = await homestayChange(houseOrder.value);
  if (res.reCode === "200") {
    ElMessage.success(res.reMsg);
  }
  getOrderList();
 }else{
  ElMessage.error("手机号格式错误！请重新输入");
 }
  dialogVisible2.value = false;
}

//获取商品订单详情
function change3(detail, id) {
  goodsOrder.value.indentId = id;
  detail.split(";").forEach((item) => {
    switch (item.split(":")[0]) {
      case "收货人":
        goodsOrder.value.consignee = item.split(":")[1];
        break;
      case "联系方式":
        goodsOrder.value.phone = item.split(":")[1];
        break;
      case "收货地址":
        goodsOrder.value.shippingAddress = item.split(":")[1];
        break;
    }
  });
  dialogVisible3.value = true;
}

//修改商品订单
async function sureGoods() {
  if(checkPhone(goodsOrder.value.phone)){
    let res = await goodsChange(goodsOrder.value);
  if (res.reCode === "200") {
    ElMessage.success(res.reMsg);
  }
  getOrderList();
  }else{
     ElMessage.error("手机号格式错误！请重新输入");
  }
  dialogVisible3.value = false;
}

//获取拼车订单详情
function change4(detail, id) {
  taxiOrder.value.indentId = id;
  detail.split(";").forEach((item) => {
    switch (item.split(":")[0]) {
      case "乘客姓名":
        taxiOrder.value.passageName = item.split(":")[1];
        break;
      case "联系电话":
        taxiOrder.value.phone = item.split(":")[1];
        break;
    }
  });
  dialogVisible4.value = true;
}

//修改拼车订单
async function sureTaxi() {
  if(checkPhone(taxiOrder.value.phone)){
let res = await taxiChange(taxiOrder.value);
  if (res.reCode === "200") {
    ElMessage.success(res.reMsg);
  }
  getOrderList();
  }else{
     ElMessage.error("手机号格式错误！请重新输入");
  }
  
  dialogVisible4.value = false;
}
//打开退订的弹窗
function unsubscribe(id) {
  orderId.value = id;
  dialogVisiblenot.value = true;
}

//退订
async function sureReturn() {
  let res = await deleteOrder(orderId.value);
  if (res.reCode === "200") {
    ElMessage.success(res.reMsg);
  }
  getOrderList();
  dialogVisiblenot.value = false;
}
</script>
<style lang="less" scoped>
@color: #a6cdb0;

.orderBar {
  display: flex;
  justify-content: space-between;
}

.changeBar {
  width: 60%;
  margin: 10px auto;
}

.orderList {
  margin-top: 20px;

  .orderItem {
    display: flex;
    margin-top: 10px;
    background-color: #ebfff6;
    border: 1px solid @color;
    border-radius: 6px;
    padding: 20px;
    img {
      width: 100px;
      height: 100px;
    }
  }
}

colgroup {
  col {
    &:first-child {
      width: 310px;
    }

    &:nth-child(2) {
      width: 110px;
    }

    &:nth-child(3) {
      width: 60px;
    }

    &:nth-child(4) {
      width: 130px;
    }

    &:last-child {
      width: 110px;
    }
  }
}

.noOrder {
  border: 1px solid #2fdab8;
  margin: 20px 0;
  background-color: rgba(47, 218, 184, 0.1);
  padding: 10px 5px;
}

.container {
  margin: 20px 0px;
  table {
    margin: 0 auto;
    border-collapse: collapse;
    border: 1px solid #2fdab8;
    thead {
      background-color: rgba(47, 218, 184, 0.1);
      th {
        padding: 10px 5px;
        div {
          text-align: center;
        }
        input {
          transform: translateY(3px);
        }
      }
    }
  }

  tbody {
    tr {
      td {
        & > div {
          box-sizing: border-box;
          padding: 10px;
        }
      }
    }
  }

  .info-msg {
    .infoDetail {
      display: flex;
      align-items: center;
      cursor: pointer;
    }
    img {
      height: 100px;
      width: 100px;
    }
  }
}

.operate {
  display: flex;
  span {
    margin: 0 2px;
  }
}

@media (max-width: 450px) {
  .changeBar {
    width: 100%;
  }

  .operate {
    flex-direction: column;
    span {
      margin: 2px 0;
    }
  }

  .infoDetail {
    flex-direction: column !important;
    > div {
      font-size: 12px;
      margin-top: 10px;
    }
  }

  #dialogVisible1 {
    width: 80%;
  }

  /deep/.el-dialog {
    width: 80%;
  }
}
</style>

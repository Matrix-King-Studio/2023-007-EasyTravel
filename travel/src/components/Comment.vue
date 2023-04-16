<template>
  <div class="wrapper">
    <div>
      <h2>网友评价</h2>
      <div v-if="show">
        <div class="sumComment">
          <div class="sumScore">{{ commentSum.sum }}</div>
          <ul>
            <li class="score" v-for="item in commentSum.subComment" :key="item">
              <el-progress
                type="dashboard"
                :percentage="item.point"
                width="60"
                color="#a6cdb0"
              >
                <template #default="{ percentage }">
                  <span class="percentage-value">{{ percentage / 20 }}</span>
                </template>
              </el-progress>
              <span>{{ item.text }}</span>
            </li>
          </ul>
        </div>
        <div class="commentList">
          <ul>
            <li v-for="item in commentLists" :key="item.id">
              <p class="user">
                <img
                  src="http://img.alicdn.com/tps/TB1l6dkOXXXXXXEXVXXXXXXXXXX-210-210.png_70x70.jpg"
                  alt=""
                />
                <span class="userName">{{ item.userName }}</span>
                <span class="tag">消费后评价</span>
              </p>
              <p class="type">{{ item.gmtModified }}发表</p>
              <div>{{ item.content }}</div>
              <div class="love">
                <span
                  ><i
                    :class="[
                      'iconfont',
                      item.loveStatus
                        ? 'icon-aixin_shixin'
                        : 'icon-a-dianzanaixin',
                    ]"
                    @click="giveLove(item)"
                    style="font-size: 14px"
                  ></i>
                  &nbsp;{{ item.loveCnt }}</span
                ><el-icon
                  style="margin-left: 10px"
                  v-if="item.userId == userId"
                  @click="deleteComments(item.id)"
                  ><Delete
                /></el-icon>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <div v-else class="noComment">暂无评论</div>
      <div class="sendComment">
        <textarea
          name=""
          id=""
          cols="30"
          rows="10"
          v-model="content"
          placeholder="请输入评论"
        ></textarea>
        <div>
          <el-button type="success" round plain @click="send"
            >发布评论</el-button
          >
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { defineProps, reactive, ref, toRaw } from "vue";
import { comment } from "../util/get";
import { deleteComment } from "../util/delete";
import { commentLove, commentSend } from "../util/post";
import { ElMessage } from "element-plus";
let commentLists = ref([]);
let content = ref("");
let show = ref(false);
let userId = ref(0);

userId.value = sessionStorage.getItem("userId");

const props = defineProps({
  Comments: Object,
  articleId: String,
  type: String,
});

let commentSum = reactive({});

commentSum = toRaw(props).Comments;
async function getComment() {
  let res = await comment(toRaw(props).type, toRaw(props).articleId);
  commentLists.value = res.body.dataList.reverse();
  if (res.body.dataList != "") {
    show.value = true;
  }
}

//删除评论
async function deleteComments(id) {
  let res = await deleteComment({
    type: toRaw(props).type,
    articleId: toRaw(props).articleId,
    commentId: id,
  });
  if (res.reCode == 200) {
    ElMessage({
      message: "删除评论成功",
      type: "success",
    });
  }
  getComment();
}

getComment();

//点赞
async function giveLove(item) {
  let res;
  if (!item.loveStatus) {
     res = await commentLove({
      id: item.id,
      userId: item.userId,
      status: 0,
    });
  } else {
     res = await commentLove({
      id: item.id,
      userId: item.userId,
      status: 1,
    });
  }
  if (res.reCode == 200) {
      ElMessage({
        message: res.reMsg,
        type: "success",
      });
    }
  getComment();
}

//发布评论
async function send() {
  let res = await commentSend({
    type: toRaw(props).type,
    articleId: toRaw(props).articleId,
    content: content.value,
  });
  if (res.reCode == 200) {
    ElMessage({
      message: res.reMsg,
      type: "success",
    });
    content.value = "";
  }
  getComment();
}
</script>
<style lang="less" scoped>
@color: #a6cdb0;
.wrapper {
  width: 60%;
  margin: 0 auto;
}

.icon-aixin_shixin {
  color: red;
}
.love {
  margin-top: 10px;
  margin-right: 20px;
  text-align: right;
  span {
    vertical-align: top;
    i {
      font-size: 16px;
    }
  }
}

.noComment {
  background: #ebebeb;
  padding: 10px 0;
  border-radius: 10px;
  border: 2px solid #c3c2c2;
  margin-top: 10px;
}
.sumComment {
  background: #ebebeb;
  display: flex;
  align-items: center;
  padding: 10px 0;
  justify-content: center;
  border-radius: 10px;
  margin-top: 10px;
  .sumScore {
    color: @color;
    font-weight: 800;
    font-size: 2rem;
    border-right: 2px solid #c3c2c2;
    padding: 0 20px;
  }

  ul {
    display: flex;
    li {
      display: flex;
      flex-direction: column;
      padding: 0 20px;

      span {
        margin-top: 4px;
      }
    }
  }
}

.commentList {
  text-align: left;
  margin-top: 10px;

  li {
    border-bottom: 4px solid #f4f4f4;
    padding: 16px;
    background: #fff;
  }
  .userName {
    font-weight: 600;
    margin-left: 10px;
  }
  .tag {
    color: @color;
    background-color: #cbfac9;
    padding: 2px 6px;
    margin-left: 10px;
    border-radius: 4px;
    font-size: 12px;
  }
  .user {
    display: flex;
    align-items: center;
    img {
      width: 40px;
      border-radius: 50%;
    }
  }

  .type {
    color: #b2b1b1;
    margin: 6px 0;
  }
}

.sendComment {
  margin-top: 20px;
  width: 100%;
  textarea {
    width: 100%;
    border: 2px solid #d7d6d6;
    resize: none;
    border-radius: 6px;
    padding: 10px;
    outline: none;
    box-sizing: border-box;
  }
  > div {
    text-align: right;
    margin-top: 10px;
  }
}

/deep/.percentage-value {
  font-size: 16px;
}

@media (max-width: 450px) {
  .wrapper {
    width: 90%;
  }

  .sumComment {
    .sumScore {
      padding: 0 10px;
    }
    li {
      padding: 0 10px !important;
    }
  }

  .sendComment {
    width: 94%;
  }
}
</style>
<template>
  <div id="main">
    <div class="login-register">
      <div class="contain">
        <!-- 主体盒子 -->
        <div class="big-box" :class="{ active: isLogin }">
          <!-- 登录 -->
          <div class="big-contain" v-if="isLogin">
            <div class="btitle">账户登录</div>

            <div class="bform">
              <input placeholder="用户名" v-model="form.username" />
              <input
                type="password"
                placeholder="密码"
                v-model="form.password"
              />
            </div>

            <!-- 按钮 -->
            <div class="btn">
              <button class="bbutton" @click="Login">登录</button>
            </div>
          </div>

          <!-- 注册 -->
          <div class="big-contain" v-else>
            <div class="btitle">创建账户</div>
            <div class="bform">
              <input type="text" placeholder="用户名" v-model="form.username" />
              <input
                type="password"
                placeholder="密码"
                v-model="form.password"
              />
              <input
                type="password"
                placeholder="再次输入密码"
                v-model="passwords"
                @blur="checkpassword"
              />
            </div>
            <button class="bbutton" @click="Register">注册</button>
          </div>
        </div>

        <!-- 侧边小盒子 -->
        <div class="small-box" :class="{ active: isLogin }">
          <!-- 注册 -->
          <div class="small-contain" v-if="isLogin">
            <div class="stitle">你好，朋友!</div>
            <p class="scontent">开始注册</p>
            <button class="sbutton" @click="changeType">注册</button>
          </div>
          <!-- 登录 -->
          <div class="small-contain" v-else>
            <div class="stitle">欢迎回来!</div>
            <p class="scontent">请登录你的账户</p>
            <button class="sbutton" @click="changeType">登录</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import { register,login } from "../util/post";
import { useRouter } from 'vue-router';
export default {
  name: "Login",
  setup() {
    let isLogin = ref(true);
    let form = reactive({
      username: "",
      password: "",
    });
    let passwordError = ref(false);
    let existed = ref(true);
    let passwords = ref("");
    let router=useRouter();
    // 切换类型
    function changeType() {
      this.isLogin = !this.isLogin;
      this.form.username = "";
      this.form.password = "";
    }
    //检查密码
    function checkpassword() {
      if (this.form.password != this.passwords) {
        ElMessage.error("两次输入密码不一样");
      }
    }

//注册
  async  function Register() {
      if (
        this.form.username !== "" &&
        this.form.password !== "" &&
        this.passwords !== ""
      ) {
        let res =await register(this.form);
        if (res.reCode =='200' ) {
           ElMessage.success(res.reMsg);
          this.isLogin = true; //跳转到登录
        } else {
          ElMessage.error(res.reMsg);
        }
      } else {
         ElMessage.error("填写信息不全");
      }
    }

     //登录
   async function Login() {
       if (this.form.username !== "" && this.form.password !== "") {
        let res = await login(this.form);
        if (res.reCode == "200") {
          ElMessage.success(res.reMsg);
           window.sessionStorage.setItem('userId',res.body.userId)
          sessionStorage.setItem('userName',this.form.username)
            window.history.state.back?router.back():router.push('home')
        } else {
           ElMessage.error(res.reMsg);
        }
      } else {
        ElMessage.error("填写不能为空");
      }
    }

    return {
      isLogin,
      form,
      passwordError,
      existed,
      passwords,
      changeType,
      checkpassword,
      router,
      Register,
      Login
    };
  },
};
</script>

<style  lang="less" scoped>
@color: #a6cdb0;
#main {
  font-size: 16px;
  width: 100%;
  height: 100%;
}

.input {
  width: 300px;
  margin-right: 20px;
}

.login-register {
  background: url("../assets/login.webp") no-repeat;
  height: 750px;
  box-sizing: border-box;
}

.contain {
  width: 50%;
  height: 60%;
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  border-radius: 20px;
  box-shadow: 0 0 3px #f0f0f0, 0 0 6px #f0f0f0;
}

/* 主体盒子*/
.big-box {
  width: 70%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 30%;
  transform: translateX(0%);
  transition: all 1s;

  /* 登录 */
  .big-contain {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .btitle {
      font-size: 2em;
      font-weight: bold;
      color: @color;
    }

    .bform {
      width: 100%;
      height: 40%;
      padding: 2em 0;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: center;
      .errTips {
        display: block;
        width: 50%;
        text-align: left;
        color: red;
        font-size: 0.7em;
        margin-left: 1em;
      }

      input {
        width: 50%;
        height: 30px;
        border: none;
        outline: none;
        border-radius: 10px;
        padding-left: 2em;
        background-color: #f0f0f0;
      }
    }
  }
}

/* 按钮 */
.btn {
  display: flex;
  width: 220px;
  align-items: center;
  justify-content: space-between;
}

.bbutton {
  width: 100px;
  height: 40px;
  border-radius: 24px;
  border: none;
  outline: none;
  background-color: @color;
  color: #fff;
  cursor: pointer;
}

// 侧边小盒子
.small-box {
  width: 30%;
  height: 100%;
  background: @color;
  position: absolute;
  top: 0;
  left: 0;
  transform: translateX(0%);
  transition: all 1s;
  border-top-left-radius: inherit;
  border-bottom-left-radius: inherit;

  .small-contain {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .stitle {
      font-size: 1.5em;
      font-weight: bold;
      color: #fff;
    }

    .scontent {
      font-size: 0.8em;
      color: #fff;
      text-align: center;
      padding: 2em 4em;
      line-height: 1.7em;
    }

    .sbutton {
      width: 60%;
      height: 40px;
      border-radius: 24px;
      border: 1px solid #fff;
      outline: none;
      background-color: transparent;
      color: #fff;
      font-size: 0.9em;
      cursor: pointer;
    }
  }
}

.big-box.active {
  left: 0;
  transition: all 0.5s;
}

.small-box.active {
  left: 100%;
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
  border-top-right-radius: inherit;
  border-bottom-right-radius: inherit;
  transform: translateX(-100%);
  transition: all 1s;
}

@media (max-width: 450px) {
  .contain {
    width: 90%;
    height: 50%;
  }
  .btn {
    width: 120px;
  }
  .login-register {
    background-position: center center;
  }

  .big-box .big-contain .btitle {
    font-size: 1.4em;
  }

  .small-box .small-contain .stitle {
    font-size: 1.2em;
  }
}
</style>

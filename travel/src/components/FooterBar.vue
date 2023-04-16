<template>
  <div class="wrapper">
          <div class="contact">
        <div class="contactMain">
           <div class="about">
               <h4>关于我们</h4>
               <p>便民游网站为您提供景点购票、预订民宿、购物平台和拼车服务，使您的旅游更加方便快捷舒心。让您觉得满意是我们最大的追求</p>
           </div>
           <div class="message">
               <h4>发表您的留言：</h4>
               <div>
                   <span>姓名：</span>
                   <input type="text">
                   <span class="required">&nbsp;*</span>
               </div>
               <div>
                   <span>邮箱：</span>
                   <input type="text">
               </div>
               <div>
                   <span>电话：</span>
                   <input type="text">
               </div>
               <div>
                   <span>内容：</span>
                   <textarea type="text" placeholder="留言需审核后才会显示" rows="3"></textarea>
               </div>
               <div>
                   <button>提交留言</button>
               </div>
           </div>
        </div>
    </div>

    <div class="footer">
        <div class="footerMain container">
            <div class="footerList">
                <h4>景点推荐</h4>
                <span>天山神秘大峡谷</span>
                <span>库车王府</span>
            </div>

            <div class="footerList">
                <h4>民宿预订</h4>
                <span>阿克苏百间里民宿 </span>
                <span>阿克苏海富祥宾馆</span>
            </div>

            <div class="footerList">
                <h4>地方特产</h4>
                <span>手剥纸皮核桃</span>
                <span>特级锁鲜枣</span>
            </div>

        </div>
    </div>
  </div>
</template>
<script setup>

</script>
<style lang="less" scoped>
@color:#a6cdb0;
.contact {
    margin-top: 30px;
    background-color: @color;
    color: #fff;
    padding: 40px 0;
}

.contactMain{
 display: flex;
    justify-content: space-between;
    align-items: center;
    color: #fff;
    width: 60%;
    margin: 0 auto;
}

.about{
    width: 50%;
}
.contactMain h4{
    font-size: 20px;
    font-weight: 200;
}

.about p{
   font-size: 14px;
    line-height: 2rem;
    margin-top: 20px;
    text-align: left;
    text-indent: 2em;
}

.contactMain input{
    padding: 5px;
     outline: none;
}

.contactMain textarea{
    resize: none;
    padding: 5px;
    width: 260px;
    outline: none;
}

.message div{
    margin-top: 20px;
}

.required{
    color: red;
}

button{
    border: none;
    border-radius: 5px;
    text-align: center;
    padding: 10px 30px;
    background: #de9932;
    color: #fff;
}

.footer {
    background-color: #2b2b2b;
    color: #fff;
}

.footerMain {
    display: flex;
    justify-content: space-around;
    padding: 30px;
}

.footerList span {
    display: block;
    color: #b2b2b2;
    font-size: 14px;
    margin-top: 10px;
}

@media (max-width:450px) {
    .contactMain{
 flex-direction: column;
    width: 100%;

}
.about{
    width: 80%;
}

.message{
    margin-top: 20px;
}

.footerMain{
    font-size: 12px;
}
}
</style>
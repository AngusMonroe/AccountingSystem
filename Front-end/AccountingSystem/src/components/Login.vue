<template>
  <div class="box">
    <div class="box-item">
      <div
        id="login-page"
        class="row"
      >
        <div class="col s12 z-depth-4 card-panel">
          <form class="login-form">
            <div class="row">
              <div class="input-field col s12 center">
                <img
                  src="images/login-logo.png"
                  alt=""
                  class="circle responsive-img valign profile-image-login"
                >
                <p class="center login-form-text">Accounting System Login</p>
              </div>
            </div>
            <div class="row margin">
              <div class="input-field col s12">
                <i class="mdi-social-person-outline prefix"></i>
                <input
                  id="username"
                  type="text" 
                  v-model="userName"
                >
                <label
                  for="username"
                  class="center-align"
                >Username</label>
              </div>
            </div>
            <div class="row margin">
              <div class="input-field col s12">
                <i class="mdi-action-lock-outline prefix"></i>
                <input
                  id="password"
                  type="password" 
                  v-model="password"
                >
                <label for="password">Password</label>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12 m12 l12  login-text">
                <input
                  type="checkbox"
                  id="remember-me"
                />
                <label for="remember-me">Remember me</label>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <a
                  href="javascript:void(0);"
                  class="btn waves-effect waves-light col s12" 
                  @click="login"
                >Login</a>
              </div>
            </div>
           

          </form>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
    data:function(){
        return {
            userName:"",
            password:""
        }
    },
    methods:{
        login: function(){
            var payload = {
                username:this.userName,
                pwd:this.password
            };
            this.$post("login",payload,(data)=>{
                switch(data.kind){
                  case "Administrator":
                    data.kind = "admin";
                    break;
                  case "Seller":
                    data.kind = "seller";
                    break;
                  case "Buyer":
                    data.kind = "buyer";
                    break;
                  case "Accountant":
                    data.kind = "accountant";
                    break;
                }
                sessionStorage.setItem('user',data.username);
                sessionStorage.setItem('kind',data.kind);
                this.$router.push({
                    path: "/main/dashboard"
                });
            },(data)=>{
                this.$toast("登录失败，原因："+data.msg);
            });
        }
    }
};
</script>
<style scoped>

.box {
  display: table;
  margin: auto;
}
.box-item {
  display: table-cell;
  vertical-align: middle;
}
</style>

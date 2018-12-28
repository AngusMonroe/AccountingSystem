<template>
  <div>
    <!--breadcrumbs start-->
    <div
      id="breadcrumbs-wrapper"
      class=" grey lighten-3"
    >
      <div class="container">
        <div class="row">
          <div class="col s12 m12 l12">
            <h5 class="breadcrumbs-title">User Manage</h5>
            <ol class="breadcrumb">
              <li>
                <router-link to="/">User Management</router-link>
              </li>
              <li class="active">Add User</li>

            </ol>
          </div>
        </div>
      </div>
    </div>
    <!--breadcrumbs end-->
    <div class="container">
      <div class="section">
        <div class="row">
          <div class="input-field col s12 m6">
            <input              
              id="username"
              type="text"
              class="validate"
              v-model="username"
            >
            <label for="username">User Name</label>
          </div>

        </div>
        
        <div class="row">
          <div class="input-field col s12 m6">
            <input
              id="password"
              type="password"
              class="validate"
              v-model="password"
            >
            <label for="password">Password</label>

          </div>
        </div>
        <div class="row">
          <div class="input-field col s12 m6">
            <select v-model="role" class="browser-default">
              <option
                value=""
                disabled
                selected 
                
              >Choose user role</option>
              <option value="Seller">Seller</option>
              <option value="Buyer">Buyer</option>
              <option value="Accountant">Accountant</option>
              <option value="Administrator">Admin</option>
            </select>
            
          </div>
        </div>
        <br>
        <div class="row">
            <button class="btn" @click="createUser">Add User</button>
        </div>
      </div>
    </div>

  </div>
</template>
<script>
export default {
    data:function(){
        return {
            username:"",
            password:"",
            role:""
        }
    },
    methods:{
        createUser:function(){
            if(this.username==""||this.password==""||this.role=="")return;
            var payload={
                name:this.username,
                password:this.password,
                kind:this.role
            };
            this.$post("admin_addUser",payload,data=>{
                this.$toast("Add User Success");
            })
            this.username = "";
            this.password = "";
            this.role = "";
        }
    },

};
</script>
<style>
</style>

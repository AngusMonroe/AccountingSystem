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
              <li class="active">Manage Current User</li>
            </ol>
          </div>
        </div>
      </div>
    </div>
    <!--breadcrumbs end-->
    <!--start container-->
    <div class="container">
      <div class="section">

        <!--Hoverable Table-->
        <div class="divider"></div>
        <div
          id="hoverable-table"
          v-if="userList"
        >
          <h4 class="header">User List</h4>
          <div class="row" v-if="userList">
            <div class="col s12 m12 l12">
              <table class="hoverable">
                <thead>
                  <tr>
                    <th data-field="id">ID</th>
                    <th data-field="name">User Name</th>
                    <th data-field="role">Role</th>                    
                    <th>Modification</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="user in userList">
                    <td>{{user.id}}</td>
                    <td>{{user.name}}</td>
                    <td>{{user.kind}}</td>                    
                    <td><button
                        class="btn"
                        @click="deleteUser(user.id)"
                      >Delete</button></td>
                  </tr>
                </tbody>
              </table>

            </div>
          </div>
        </div>
        <!-- End Table -->

        <br><br><br><br><br><br><br><br><br><br><br><br><br>
      </div>
    </div>
    <!--end container-->
  </div>
</template>
<script>
export default {
    data: function(){
        return {
            userList:null
        }
    },
    created: function(){
        this.getList();
    },
    methods:{
        getList:function(){
            this.$post('admin_getUserList',null,data=>{
                this.userList = data.userList;
            });

        },
        deleteUser: function(id){
            var payload = {
                id:id
            };
            this.$post('admin_removeUser',payload,data=>{
                this.$toast("Success");
            });
            this.getList();
        }
    }
};
</script>
<style>
</style>

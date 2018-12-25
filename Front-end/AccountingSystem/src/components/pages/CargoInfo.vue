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
              <h5 class="breadcrumbs-title" >Cargo Information</h5>
              <ol class="breadcrumb">
                <li><router-link to="/">Cargo Management</router-link></li>
                <li class="active">Cargo Information</li>
              </ol>
            </div>
          </div>
        </div>
      </div>
      <!--breadcrumbs end-->

      <!--start container-->
      <div class="container">
        <div class="section">

          <!-- Inline Form -->
          <div class="row">
            <div class="col s12 m12 l12">
              <div class="card-panel">
                <div class="row">
                <div class="col s12">
                  <h4 class="header2">Search</h4>
                  <div class="row">
                    <div class="input-field col m4 s12">
                      <i class="mdi-action-shopping-basket prefix"></i>
                      <input id="icon_prefix" type="text" class="validate" v-model="query">
                      <label for="icon_prefix">Cargo Name</label>
                    </div>                    
                    <div class="input-field col m4 s8">
                      <div class="input-field col s12">
                        <button @click="search" class="btn cyan waves-effect waves-light" ><i class="mdi-action-search"></i> Search</button>
                      </div>
                    </div>
                  </div>
                </div>
                </div>
              </div>
            </div>
          </div>
          <!-- End Inline Form -->

          <!--Hoverable Table-->
            <div class="divider"></div>
            <div id="hoverable-table" v-if="cargoList">
              <h4 class="header">Cargo  Table</h4>
              <div class="row">
                <div class="col s12 m4 l3">
                  <p> Item Information</p>
                </div>
                <div class="col s12 m8 l9">
                  <table class="hoverable">
                    <thead>
                      <tr>
                        <th data-field="id">ID</th>
                        <th data-field="name">Item Name</th>    
                        <th data-field="ammount">Amount</th>     
                        <th data-field="price">Price</th>               
                      </tr>
                    </thead>
                    <tbody>                      
                      <tr v-for="item in cargoList">                        
                        <td>{{item.id}}</td>
                        <td>{{item.name}}</td>
                        <td>{{item.amount}}</td>
                        <td>{{item.price}}</td>                        
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
    
    

    <!-- //////////////////////////////////////////////////////////////////////////// -->
</div>
</template>
<script>
export default {
    data: function(){
      return {
        cargoList:null,
        query:""
      }
    },
    created:function(){
      
    },
    methods:{
      search: function(){
        if(this.query=="")return;
        var payload = {
          query:this.query
        }
        this.$post("item_query",payload,(data)=>{
          this.cargoList = data.result;
        })
      }
    }
}
</script>

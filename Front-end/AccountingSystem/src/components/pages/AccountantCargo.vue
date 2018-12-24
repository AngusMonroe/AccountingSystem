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
            <h5 class="breadcrumbs-title">Cargo Information</h5>
            <ol class="breadcrumb">
              <li>
                <router-link to="/">Accountant</router-link>
              </li>
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

        <!--card stats start-->
        <div
          id="card-stats"
          class="seaction"
        >
          <h4 class="header">Cargo Stats</h4>
          
          <div class="row">
            <div class="col s12 m6 l3">
              <div class="card">
                <div class="card-content  green white-text">
                  <p class="card-stats-title"><i class="mdi-action-trending-up"></i> Total Cargo Amount</p>
                  <h4 class="card-stats-number">{{totalCargoAmount}}</h4>  
                </div>
                <div class="card-action  green darken-2">
                  <div id="clients-bar"></div>
                </div>
              </div>
            </div>
            <div class="col s12 m6 l3">
              <div class="card">
                <div class="card-content purple white-text">
                  <p class="card-stats-title"><i class="mdi-editor-attach-money"></i>Total Value</p>
                  <h4 class="card-stats-number">${{totalValue}}</h4>
                  </p>
                </div>
                <div class="card-action purple darken-2">
                  <div id="sales-compositebar"></div>
                </div>
              </div>
            </div>
            <div class="col s12 m6 l3">
              <div class="card">
                <div class="card-content blue-grey white-text">
                  <p class="card-stats-title"><i class="mdi-action-trending-up"></i> Cargo Categories</p>
                  <h4 class="card-stats-number">{{cargoCategories}}</h4>
                  </p>
                </div>
                <div class="card-action blue-grey darken-2">
                  <div id="profit-tristate"></div>
                </div>
              </div>
            </div>
            <div class="col s12 m6 l3">
              <div class="card">
                <div class="card-content deep-purple white-text">
                  <p class="card-stats-title"><i class="mdi-editor-attach-money"></i>Average Price</p>
                  <h4 class="card-stats-number">${{averagePrice}}</h4>
                  </p>
                </div>
                <div class="card-action  deep-purple darken-2">
                  <div id="invoice-line"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--card stats end-->

        <!--Hoverable Table-->
        <div class="divider"></div>
        <div
          id="hoverable-table"
          v-if="cargoList"
        >
          <h4 class="header">Cargo Table</h4>
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
                    <td @click="detail(item.id)">{{item.id}}</td>
                    <td @click="detail(item.id)">{{item.name}}</td>
                    <td @click="detail(item.id)">{{item.amount}}</td>
                    <td @click="detail(item.id)">{{item.price}}</td>
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
    <br><br><br><br><br><br><br><br><br><br><br><br><br>

    <!-- //////////////////////////////////////////////////////////////////////////// -->
  </div>
</template>
<script>
export default {
  data: function() {
    return {
      cargoList: null,
      query: "",
      totalCargoAmount:0,
      totalValue:0,
      cargoCategories:0,
      averagePrice:0
    };
  },
  created: function() {
    this.getList();
  },

  methods: {
    getList: function() {
      this.$post("item_getList", null, data => {
        this.cargoList = data.items;
        this.analyse();
      });
    },
    analyse: function(){
      
      for(var i = 0;i<this.cargoList.length;i++){
        var item = this.cargoList[i];
        this.totalCargoAmount += item.amount;
        this.cargoCategories++;
        this.averagePrice += item.price;
        this.totalValue += item.price*item.amount;        
      }
      this.averagePrice/= this.cargoCategories;
    },
    detail: function(id) {
      this.$router.push({
        path:"/main/accountant/cargoDetail",
        query:{
          id:id
        }
      })
    }
  }
};
</script>

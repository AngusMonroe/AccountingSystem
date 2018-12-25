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
            <h5 class="breadcrumbs-title">Shipment manage</h5>
            <ol class="breadcrumb">
              <li>
                <router-link to="/">Cargo Management</router-link>
              </li>
              <li class="active">Shipment manage</li>
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
          v-if="cargoList"
        >
          <h4 class="header">Cargo Table</h4>
          <div class="row">
            <div class="col s12 m12 l12">
              <table class="hoverable">
                <thead>
                  <tr>
                    <th data-field="id">ID</th>
                    <th data-field="name">Item Name</th>
                    <th data-field="ammount">Amount</th>
                    <th data-field="price">Price</th>
                    <th>Modification</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="item in cargoList">
                    <td>{{item.id}}</td>
                    <td>{{item.name}}</td>
                    <td>{{item.amount}}</td>
                    <td>{{item.price}}</td>
                    <td><button
                        class="btn"
                        @click="showSell(item.id)"
                      >Sell</button></td>
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
    <!-- Modal Structure -->
    <div
      id="sellModal"
      class="modal"
    >
      <div class="modal-content">
        <h4>Sell Cargo</h4>
        <p>Select amount</p>
        <p class="range-field">
          <input
            type="range"
            id="amount"
            v-model="amount"
            min="1"
            max="100"
          />
        </p>
        <p>{{amount}}</p>
      </div>
      <div class="modal-footer">
        <a
          href="javascript:void(0)"
          class="modal-close waves-effect waves-green btn-flat"
          @click="sell(amount)"
        >Enter</a>
      </div>
    </div>

  </div>
</template>
<script>
export default {
  
  data: function() {
    return {
      cargoList: null,
      sellId:0,
      amount: 0,
      cargoName: "",
      cargoPrice: 0
    };
  },
  created: function() {
    this.getList();
  },
  mounted: function() {
    setTimeout(() => {
      var M = require("materialize-css");
      var elem = document.getElementById("sellModal")
      M.Modal.init(elem);      
    }, 100);
  },
  methods: {
    getList: function() {
      this.$post("item_getList", null, data => {
        this.cargoList = data.items;
      });
    },
    showSell: function(id) {
      //var M = require("materialize-css");
      var M = require("materialize-css");
      var instance = M.Modal.getInstance(document.getElementById("sellModal"));
      this.sellId = id;
      instance.open();
    },
    sell: function(amount) {
      var payload = {
        id: this.sellId,
        amount: parseInt(amount)
      };
      this.$post("item_sell", payload, data => {
        this.$toast("销售成功");
        this.getList();
      });
    }
  }
};
</script>

<style>
</style>

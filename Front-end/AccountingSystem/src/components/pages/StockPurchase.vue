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
            <h5 class="breadcrumbs-title">Cargo Management</h5>
            <ol class="breadcrumb">
              <li>
                <router-link to="/">Cargo Management</router-link>
              </li>
              <li class="active">Stock Purchase</li>
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
                        @click="showPurchase(item.id)"
                      >Purchase</button></td>
                  </tr>
                </tbody>
              </table>
              <br>
              <div class="divider"></div>
              <br>
              <button
                class="btn"
                @click="showAddCargo"
              ><i class="mdi-av-queue prefix"></i>Add new cargo</button>

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
      id="purchaseModal"
      class="modal"
    >
      <div class="modal-content">
        <h4>Purchase Cargo</h4>
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
          @click="purchase(amount)"
        >Enter</a>
      </div>
    </div>

    <!-- Modal Structure -->
    <div
      id="addModal"
      class="modal"
    >
      <div class="modal-content">
        <h4>Add new cargo</h4>
        <div class="input-field col m6 s12">
          <i class="mdi-av-my-library-books prefix"></i>
          <input
            id="icon_prefix_name"
            v-model="cargoName"
            type="text"
            class="validate"
          >
          <label for="icon_prefix_name">Cargo Name</label>
        </div>
        <div class="input-field col m6 s12">
          <i class="mdi-image-style prefix"></i>
          <input
            id="icon_prefix_price"
            v-model="cargoPrice"
            type="number"
            class="validate"
          >
          <label for="icon_prefix_price">Price</label>
        </div>
      </div>
      <div class="modal-footer">
        <a
          href="javascript:void(0)"
          class="modal-close waves-effect waves-green btn-flat"
          @click="addCargo()"
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
      purchaseId:0,
      amount:0,
      cargoName:'',
      cargoPrice:0
    };
  },
  created: function() {
    this.getList();
  },
  mounted: function() {
    setTimeout(() => {
      var M = require("imports-loader?$=jquery!materialize-css");
      var elem = document.getElementById("purchaseModal");
      M.Modal.init(elem);
      elem = document.getElementById("addModal");
      M.Modal.init(elem);
      
    }, 400);
  },
  methods: {
    getList: function() {
      this.$post("item_getList", null, data => {
        this.cargoList = data.items;
      });
    },
    showPurchase: function(id) {
      //var M = require("materialize-css");
      var M = require("materialize-css");
      var instance = M.Modal.getInstance(
        document.getElementById("purchaseModal")
      );
      this.purchaseId = id;
      instance.open();
    },
    purchase: function(amount) {
      var payload = {
        id: this.purchaseId,
        amount: parseInt(amount)
      };
      this.$post("item_buy", payload, data => {
        this.$toast("购买成功");
        this.getList();
      });
    },
    showAddCargo: function() {
      var M = require("materialize-css");
      
      var instance = M.Modal.getInstance(document.getElementById("addModal"));
      instance.open();
    },
    addCargo: function() {
      if(this.cargoName==""||this.cargoPrice==0){
        this.$toast("Please Enter valid data");
        return;
      }
      var payload = {
        name: this.cargoName,
        price: parseFloat(this.cargoPrice)
      };
      this.$post("item_add", payload, data => {
        this.$toast("Add Cargo Success");
        this.getList();
      });
    }
  }
};
</script>

<style>
</style>

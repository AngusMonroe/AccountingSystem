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
            <h5 class="breadcrumbs-title">Cargo Detail</h5>
            <ol class="breadcrumb">
              <li><router-link to="/">Accountant</router-link>
              </li>
              <li><router-link to="/main/accountant/cargoInfo">Cargo Information</router-link>
              </li>
              
              <li class="active">Cargo Detail</li>
            </ol>
          </div>
        </div>
      </div>
    </div>
    <!--breadcrumbs end-->
    <div class="container">
      <div id="invoice" v-if="item">
        <div class="invoice-lable">
          <div class="row">
            <div class="col s12 m3 l3 cyan">
              <h4 class="white-text invoice-text">{{item.name}}</h4>
            </div>
            <div class="col s12 m9 l9 invoice-brief cyan white-text">
              <div class="row">
                <div class="col s12 m3 l3">
                  <p class="strong">Price</p>
                  <h4 class="header">$ {{item.price}}</h4>
                </div>
                <div class="col s12 m3 l3">
                  <p class="strong">Grand Total</p>
                  <h4 class="header">$ {{item.sum}}</h4>
                </div>
                
              </div>
            </div>
          </div>
        </div>
        <div class="invoice-table">
          <div class="row">
            <div class="col s12 m12 l12">
              <table class="striped">
                <thead>
                  <tr>
                    <th data-field="no">No</th>
                    <th data-field="item">Type</th>
                    <th data-field="uprice">Amount</th>
                    <th data-field="price">Date</th>
                    <th data-field="price">Operator</th>
                    <th data-field="price">Sub Total</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="transaction in item.transaction">
                    <td>{{transaction.id}}</td>
                    <td>{{transaction.kind}}</td>
                    <td>{{transaction.amount}}</td>
                    <td>{{transaction.time}}</td>
                    <td>{{transaction.operator.name}}</td>
                    <td>$ {{transaction.totalPrice}}</td>
                  </tr>
                  
                  
                  <tr>
                    <td
                      colspan="4"
                      class="white"
                    ></td>
                    <td class="cyan white-text">Grand Total</td>
                    <td class="cyan strong white-text">$ {{item.sum}}</td>
                  </tr>
                </tbody>
              </table>
            </div>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
    data: function(){
        return {
            item: null
        }
    },
    created: function(){
        var payload={
            id: this.$route.query.id
        };
        this.$post('item_getInfo',payload,(data)=>{
            this.item = data.item
        })
    }
};
</script>
<style>
</style>

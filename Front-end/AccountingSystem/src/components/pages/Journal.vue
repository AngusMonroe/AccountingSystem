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

        <div class="divider"></div>

        <!--chart dashboard start-->
        <div id="chart-dashboard">
          <div class="row">
            <div class="col s12 m12 l12">
              <div class="card">
                <div class="card-move-up waves-effect waves-block waves-light">
                  <div class="move-up cyan darken-1">
                    <div>
                      <span class="chart-title white-text">Revenue</span>
                      <div class="chart-revenue cyan darken-2 white-text">
                        <p class="chart-revenue-total">$4,500.85</p>
                        <p class="chart-revenue-per"><i class="mdi-navigation-arrow-drop-up"></i> 21.80 %</p>
                      </div>
                      <div class="switch chart-revenue-switch right">
                        <label class="cyan-text text-lighten-5">
                          Month
                          <input type="checkbox">
                          <span class="lever"></span> Year
                        </label>
                      </div>
                    </div>
                    <div class="trending-line-chart-wrapper">
                      <canvas
                        id="trending-line-chart"
                        height="70"
                      ></canvas>
                    </div>
                  </div>
                </div>
                <div class="card-content">
                  <a class="btn-floating btn-move-up waves-effect waves-light darken-2 right"><i class="mdi-content-add activator"></i></a>
                  <div class="col s12 m3 l3">
                    <div id="doughnut-chart-wrapper">
                      <canvas
                        id="doughnut-chart"
                        height="200"
                      ></canvas>
                      <div class="doughnut-chart-status">4500
                        <p class="ultra-small center-align">Sold</p>
                      </div>
                    </div>
                  </div>
                  <div class="col s12 m2 l2">
                    <ul class="doughnut-chart-legend">
                      <li class="mobile ultra-small"><span class="legend-color"></span>Mobile</li>
                      <li class="kitchen ultra-small"><span class="legend-color"></span> Kitchen</li>
                      <li class="home ultra-small"><span class="legend-color"></span> Home</li>
                    </ul>
                  </div>
                  <div class="col s12 m5 l6">
                    <div class="trending-bar-chart-wrapper">
                      <canvas
                        id="trending-bar-chart"
                        height="90"
                      ></canvas>
                    </div>
                  </div>
                </div>

                <div class="card-reveal">
                  <span class="card-title grey-text text-darken-4">Revenue by Month <i class="mdi-navigation-close right"></i></span>
                  <table class="responsive-table">
                    <thead>
                      <tr>
                        <th data-field="id">ID</th>
                        <th data-field="month">Month</th>
                        <th data-field="item-sold">Item Sold</th>
                        <th data-field="item-price">Item Price</th>
                        <th data-field="total-profit">Total Profit</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>1</td>
                        <td>January</td>
                        <td>122</td>
                        <td>100</td>
                        <td>$122,00.00</td>
                      </tr>
                      <tr>
                        <td>2</td>
                        <td>February</td>
                        <td>122</td>
                        <td>100</td>
                        <td>$122,00.00</td>
                      </tr>
                      <tr>
                        <td>3</td>
                        <td>March</td>
                        <td>122</td>
                        <td>100</td>
                        <td>$122,00.00</td>
                      </tr>
                      <tr>
                        <td>4</td>
                        <td>April</td>
                        <td>122</td>
                        <td>100</td>
                        <td>$122,00.00</td>
                      </tr>
                      <tr>
                        <td>5</td>
                        <td>May</td>
                        <td>122</td>
                        <td>100</td>
                        <td>$122,00.00</td>
                      </tr>
                      <tr>
                        <td>6</td>
                        <td>June</td>
                        <td>122</td>
                        <td>100</td>
                        <td>$122,00.00</td>
                      </tr>
                      <tr>
                        <td>7</td>
                        <td>July</td>
                        <td>122</td>
                        <td>100</td>
                        <td>$122,00.00</td>
                      </tr>
                      <tr>
                        <td>8</td>
                        <td>August</td>
                        <td>122</td>
                        <td>100</td>
                        <td>$122,00.00</td>
                      </tr>
                      <tr>
                        <td>9</td>
                        <td>Septmber</td>
                        <td>122</td>
                        <td>100</td>
                        <td>$122,00.00</td>
                      </tr>
                      <tr>
                        <td>10</td>
                        <td>Octomber</td>
                        <td>122</td>
                        <td>100</td>
                        <td>$122,00.00</td>
                      </tr>
                      <tr>
                        <td>11</td>
                        <td>November</td>
                        <td>122</td>
                        <td>100</td>
                        <td>$122,00.00</td>
                      </tr>
                      <tr>
                        <td>12</td>
                        <td>December</td>
                        <td>122</td>
                        <td>100</td>
                        <td>$122,00.00</td>
                      </tr>
                    </tbody>
                  </table>
                </div>

              </div>
            </div>

          </div>
        </div>
        <!--chart dashboard end-->

        <br><br><br><br><br><br><br><br><br><br><br><br><br>
      </div>
    </div>
    <!--end container-->
    <br><br><br><br><br><br><br><br><br><br><br><br><br>

    <!-- //////////////////////////////////////////////////////////////////////////// -->
  </div>
</template>
<script>
import Chart from "chart.js";
export default {
  data: function() {
    return {
      cargoList: null,
      query: "",
      totalCargoAmount: 0,
      totalValue: 0,
      cargoCategories: 0,
      averagePrice: 0,
      chartData:{
        labels: ["JAN", "FEB", "MAR", "APR", "MAY", "JUNE", "JULY"],
        datasets: [
          {
            label: "Profit",
            pointBorderColor:"#00bcd4",
            borderColor:"#ffffff",
            backgroundColor:"rgba(128, 222, 234, 0.6)",
            data: [1, 5, 2, 4, 8, 5, 8]
          }
        ]
      }
    };
  },
  created: function() {
    this.getList();
    var now = new Date();
    var last = new Date();
    last.setMonth(last.getMonth()-1);
    this.getProfit(last,now);
  },
  mounted: function() {
    setTimeout(() => {     
      var trendingLineChart = document
        .getElementById("trending-line-chart")
        .getContext("2d");
      window.trandingLineChart = new Chart(trendingLineChart, {
        type: "line",
        data: this.chartData,
        options: {
          
        }
      });
    }, 200);
  },
  methods: {
    getList: function() {
      this.$post("item_getList", null, data => {
        this.cargoList = data.items;
        this.analyse();
      });
    },
    getProfit: function(start,end){        
        var payload = {
            end:end.format("yyyy-MM-dd"),
            start:start.format("yyyy-MM-dd")
        };
        this.$post("getSummary",payload,data=>{
            
        })
    },
    analyse: function() {
      for (var i = 0; i < this.cargoList.length; i++) {
        var item = this.cargoList[i];
        this.totalCargoAmount += item.amount;
        this.cargoCategories++;
        this.averagePrice += item.price;
        this.totalValue += item.price * item.amount;
      }
      this.averagePrice /= this.cargoCategories;
    },
    detail: function(id) {
      this.$router.push({
        path: "/main/accountant/cargoDetail",
        query: {
          id: id
        }
      });
    }
  }
};
</script>
<style>
</style>

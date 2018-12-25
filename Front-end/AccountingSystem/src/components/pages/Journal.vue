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
                        <p class="chart-revenue-total">${{totalProfit}}</p>

                      </div>
                      <div class="switch chart-revenue-switch right">
                        <label class="cyan-text text-lighten-5">
                          Month
                          <input
                            type="checkbox"
                            v-model="filterButton"
                            @click="changeFilter"
                          >
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

                </div>

                <div class="card-reveal">
                  <span class="card-title grey-text text-darken-4">Revenue<i class="mdi-navigation-close right"></i></span>
                  <table class="responsive-table">
                    <thead>
                      <tr>                        
                        <th data-field="date">Date</th>
                        <th data-field="sum">Sum</th>                        
                      </tr>
                    </thead>
                    <tbody v-if="detailList">
                      <tr v-for="detail in detailList" >
                        <td>{{detail.date}}</td>
                        <td>{{detail.sum}}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>

              </div>
            </div>

          </div>
        </div>
        <!--chart dashboard end-->

      </div>
    </div>
    <!--end container-->

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
      totalProfit: 0,
      filterButton: false,
      trandingLineChart: null,
      detailList:null,
      chartData: {
        labels: ["JAN", "FEB", "MAR", "APR", "MAY", "JUNE", "JULY"],
        datasets: [
          {
            label: "Profit",
            pointBorderColor: "#00bcd4",
            borderColor: "#ffffff",
            backgroundColor: "rgba(128, 222, 234, 0.6)",
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
    last.setMonth(last.getMonth() - 1);
    this.getProfit(last, now);
  },
  mounted: function() {},
  methods: {
    changeFilter: function() {
      setTimeout(() => {
        var now = new Date();
        var last = new Date();
        if (this.filterButton) {
          last.setFullYear(last.getFullYear() - 1);
        } else {
          last.setMonth(last.getMonth() - 1);
        }
        this.getProfit(last, now);
      }, 20);
    },
    getList: function() {
      this.$post("item_getList", null, data => {
        this.cargoList = data.items;
        this.analyse();
      });
    },
    getProfit: function(start, end) {
      var payload = {};

      this.$post("getSummary", payload, data => {
        this.chartData.labels = [];
        this.chartData.datasets[0].data = [];
        this.totalProfit = 0;
        var dataList = data.detailList;
        this.detailList = data.detailList;
        for (var i in dataList) {
          var data = dataList[i];
          var fmt = "yyyy-MM-dd";
          var str = data.date;
          //fmt为日期格式,str为日期字符串
          var reg = /([yMd]+)/g; //日期格式中的字符
          var key = {};
          var tmpkeys = fmt.match(reg);
          for (var i = 0; i < tmpkeys.length; i++) {
            key[tmpkeys[i].substr(0, 1)] = i + 1;
          }
          var r = str.match(fmt.replace(reg, "(\\d+)"));
          var date = new Date(r[key["y"]], r[key["M"]] - 1, r[key["d"]]);
          console.log(date);
          if (date < start || date > end) {
            continue;
          }
          this.chartData.labels.push(data.date);
          this.chartData.datasets[0].data.push(data.sum);
          this.totalProfit += data.sum;
          if (this.trandingLineChart) {
            this.trandingLineChart.data = this.chartData;
            this.trandingLineChart.update();
          } else {
            setTimeout(() => {
              var trendingLineChart = document
                .getElementById("trending-line-chart")
                .getContext("2d");
              this.trandingLineChart = new Chart(trendingLineChart, {
                type: "line",
                data: this.chartData,
                options: {}
              });
            }, 100);
          }
        }
      });
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

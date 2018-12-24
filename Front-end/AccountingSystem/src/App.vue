<template>
  <div id="app">
    <!-- Start Page Loading -->
    <div id="loader-wrapper">
      <div id="loader"></div>
      <div class="loader-section section-left"></div>
      <div class="loader-section section-right"></div>
    </div>
    <!-- End Page Loading -->

    <router-view></router-view>
    <!-- //////////////////////////////////////////////////////////////////////////// -->

  </div>
</template>
<script>
import $ from "jquery";
export default {
  mounted: function() {
    M = require("imports-loader?$=jquery!materialize-css");
    M.AutoInit();
    setTimeout(() => {
        $('body').addClass('loaded');  
    },0);
    
  }
};
</script>


<style lang="scss">

</style>

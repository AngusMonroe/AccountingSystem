<template>
  <div>

    <!-- START MAIN -->
    <div id="main">
      <!-- START WRAPPER -->
      <div class="wrapper">

        <!-- START LEFT SIDEBAR NAV-->
        <aside id="left-sidebar-nav">
          <ul
            id="slide-out"
            class="side-nav fixed leftside-navigation"
          >
            <li class="user-details cyan darken-2">
              <div class="row">
                <div class="col col s4 m4 l4">
                  <img
                    src="images/avatar.jpg"
                    alt=""
                    class="circle responsive-img valign profile-image"
                  >
                </div>
                <div class="col col s8 m8 l8">
                  <ul
                    id="profile-dropdown"
                    class="dropdown-content"
                  >

                    <li><a href="#"><i class="mdi-communication-live-help"></i> Help</a>
                    </li>
                    <li class="divider"></li>

                    <li><a
                        href="#"
                        @click="logout"
                      ><i class="mdi-hardware-keyboard-tab"></i> logout</a>
                    </li>
                  </ul>
                  <a
                    class="btn-flat dropdown-button waves-effect waves-light white-text profile-btn"
                    href="#"
                    data-target="profile-dropdown"
                  >{{username}}<i class="mdi-navigation-arrow-drop-down right"></i></a>
                  <p class="user-roal">{{kind}}</p>
                </div>
              </div>
            </li>

            <li class="li-hover">
              <div class="divider"></div>
            </li>
            <li class="li-hover">
              <p class="ultra-small margin more-text">General Information</p>
            </li>
            <li class="bold"><a
                href="index.html"
                class="waves-effect waves-cyan"
              ><i class="mdi-action-dashboard"></i> Dashboard</a>
            </li>
            <li class="li-hover">
              <div class="divider"></div>
            </li>
            <li class="li-hover">
              <p class="ultra-small margin more-text">Cargo Management</p>
            </li>
            <li class="bold">
              <router-link
                to="/main/cargoInfo"
                class="waves-effect waves-cyan"
              ><i class="mdi-editor-insert-comment"></i> Cargo Information</router-link>
            </li>

            <li class="no-padding">
              <ul class="collapsible collapsible-accordion">
                <li class="bold"><a class="collapsible-header waves-effect waves-cyan"><i class="mdi-action-invert-colors"></i> Cargo Management</a>
                  <div class="collapsible-body">
                    <ul>
                      <li><router-link to="/main/stockPurchase">Stock purchase</router-link >
                      </li>
                      <li><router-link to="/main/shipment">Shipment manage</router-link>
                      </li>

                    </ul>
                  </div>
                </li>

              </ul>
            </li>

            <li class="li-hover">
              <div class="divider"></div>
            </li>
            <li class="li-hover">
              <p class="ultra-small margin more-text">Accountant</p>
            </li>
            <li class="no-padding">
              <ul class="collapsible collapsible-accordion">
                <li class="bold"><a class="collapsible-header waves-effect waves-cyan"><i class="mdi-editor-insert-chart"></i> Accountant Properties</a>
                  <div class="collapsible-body">
                    <ul>
                      <li><router-link to="/main/accountant/cargoInfo">Cargo Information</router-link>
                      </li>
                      <li><router-link to="/main/accountant/generalJournal">General journal</router-link>
                      </li>

                    </ul>
                  </div>
                </li>

              </ul>
            </li>

            <li class="li-hover">
              <div class="divider"></div>
            </li>
            <li class="li-hover">
              <p class="ultra-small margin more-text">User Management</p>
            </li>
            <li class="no-padding">
              <ul class="collapsible collapsible-accordion">
                <li class="bold"><a class="collapsible-header waves-effect waves-cyan"><i class="mdi-action-swap-vert-circle"></i> User Management</a>
                  <div class="collapsible-body">
                    <ul>
                      <li><a href="css-typography.html">Manage Current User</a>
                      </li>
                      <li><a href="css-icons.html">Add new user</a>
                      </li>

                    </ul>
                  </div>
                </li>

              </ul>
            </li>

          </ul>
          <a
            href="#"
            data-activates="slide-out"
            class="sidebar-collapse btn-floating btn-medium waves-effect waves-light hide-on-large-only darken-2"
          ><i class="mdi-navigation-menu"></i></a>
        </aside>
        <!-- END LEFT SIDEBAR NAV-->

        <!-- //////////////////////////////////////////////////////////////////////////// -->
        <!-- START CONTENT -->
        <section id="content">
              <router-view></router-view>
              <!--work collections end-->
              
        </section>
        <!-- END CONTENT -->

      </div>
      <!-- END WRAPPER -->

    </div>

    <br><br><br><br><br><br><br><br><br><br><br><br><br>
    <!-- END MAIN -->
    <!-- //////////////////////////////////////////////////////////////////////////// -->
    <!-- START FOOTER -->
    <footer class="page-footer">

      <div class="footer-copyright">
        <div class="container">
          Copyright © 2018 All rights reserved.
          <span class="right"> Design and Developed by AccountSystemTeam </span>
        </div>
      </div>
    </footer>
    <!-- END FOOTER -->

    

  </div>
</template>

<script>
export default {
  data: function() {
    return {
      username: "",
      kind: "",
      showRouter:false
    };
  },
  beforeCreate: function() {
    if (sessionStorage.getItem("user") == null) {
      this.$toast("请先登录");
      this.$router.push({
        name: "login"
      });
    }
  },
  mounted: function() {
    M = require("imports-loader?$=jquery!materialize-css");
    var elems = document.querySelectorAll(".collapsible");
    M.Collapsible.init(elems);

    elems = document.querySelectorAll(".dropdown-button");
    M.Dropdown.init(elems);

    setTimeout(() => {
      elems = document.getElementById("slide-out")
      console.log(elems);
      M.Sidenav.init(elems);
    }, 200);
    
    M.AutoInit();
    

    this.username = sessionStorage.getItem("user");
    this.kind = sessionStorage.getItem("kind");
  },
  methods: {
    logout: function() {
      this.$post("logout", null, data => {
        this.$toast("登出成功");
        sessionStorage.removeItem("user");
        sessionStorage.removeItem("kind");
        this.$router.push({
          name: "login"
        });
      });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style >
</style>

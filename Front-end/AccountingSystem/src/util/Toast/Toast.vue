<template>  
      <!-- 全局提示框 -->
        <div v-show="visible">
            <div class="alert alert-info alert-dismissible fade show" role="alert">
                <div v-html="message" ></div>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>    
</template>
<script>
export default {
  data() {
    return {
      visible: false,
      message: ""
    };
  },
  methods:{
      close: function(){
          this.visible = false;
      }
  }

};
</script>
<style lang="css">


</style>
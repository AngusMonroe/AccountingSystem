<template>
<div>

</div>    
</template>
<script>
export default {
    beforeCreate: function(){
        var role = sessionStorage.getItem('kind');
        switch(role){
            case "admin":
                this.$router.push({
                    path:"/main/admin/userList"
                });
                break;
            case "accountant":
                this.$router.push({
                    path:"/main/accountant/generalJournal"
                });
                break;
            case "seller":
                this.$router.push({
                    path:"/main/cargoInfo"
                });
                break;
            case "buyer":
                this.$router.push({
                    path:"/main/cargoInfo"
                });
                break;            
        }
    }
}
</script>
<style>

</style>

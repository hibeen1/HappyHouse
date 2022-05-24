<template>
  <b-container class="bv-example-row mt-3 text-center">
    <h3 class="underline-hotpink">
      <img src="@/assets/location.png" width="40" /> 관심매물
    </h3>
    <router-view></router-view>
  </b-container>
</template>
<script>
export default {
  name: "FavoriteView",
};
</script>
<style scoped>
.underline-hotpink {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(6, 255, 180, 0.3) 30%
  );
}
</style>

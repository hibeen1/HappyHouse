<template>
  <b-container class="bv-example-row mt-3 text-center">
    <router-view></router-view>
  </b-container>
</template>

<script>
export default {
  name: "MemberView",
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(119, 0, 255, 0.3) 30%
  );
}
</style>

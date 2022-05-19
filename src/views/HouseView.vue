<template>
  <div class="bv-example-row mt-3 text-center abs">
    <h3 class="underline-orange">
      <b-icon icon="house-fill"></b-icon> House Service
    </h3>
    <table
      style="margin-bottom: 50px; table-layout: fixed"
      class="table-bordered"
    >
      <tr height="200">
        <td style="width: 20%">
          <house-search-bar />
        </td>
        <td rowspan="2" style="width: 80%">
          <house-detail />
        </td>
      </tr>
      <tr>
        <td class="align-top"><house-list /></td>
      </tr>
    </table>
  </div>
</template>
<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseList from "@/components/house/HouseList.vue";
import HouseDetail from "@/components/house/HouseDetail.vue";

export default {
  name: "HouseView",
  components: {
    HouseSearchBar,
    HouseList,
    HouseDetail,
  },
};
</script>
<style scoped>
.underline-orange {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 149, 27, 0.3) 30%
  );
  text-align: center;
}

table {
  width: 100%;
  height: 100%;
}
</style>

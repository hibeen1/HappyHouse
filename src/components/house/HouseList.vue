<template>
  <table style="table-layout: fixed">
    <tr v-if="houses && houses.length != 0">
      <td>
        <div style="overflow-y: scroll; height: 100%">
          <house-list-item
            v-for="house in houses"
            :key="house.aptCode"
            :house="house"
          />
        </div>
      </td>
    </tr>
    <tr v-else>
      <b-col><b-alert show>주택 목록이 없습니다.</b-alert></b-col>
    </tr>
  </table>
  <!-- <b-container v-if="houses && houses.length != 0" class="bv-example-row mt-3">
    <house-list-item
      v-for="(house, index) in houses"
      :key="index"
      :house="house"
    />
  </b-container>
  <b-container v-else class="bv-example-row mt-3">
    <b-row>
      <b-col><b-alert show>주택 목록이 없습니다.</b-alert></b-col>
    </b-row>
  </b-container> -->
</template>

<script>
import HouseListItem from "@/components/house/HouseListItem.vue";
import { mapMutations, mapState } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseList",
  components: {
    HouseListItem,
  },
  data() {
    return {};
  },
  computed: {
    ...mapState(houseStore, ["houses"]),
    // houses() {
    //   return this.$store.state.houses;
    // },
  },
  created() {
    this.CLEAR_HOUSE_LIST();
  },
  methods: {
    ...mapMutations(houseStore, ["CLEAR_HOUSE_LIST"]),
  },
};
</script>

<style></style>

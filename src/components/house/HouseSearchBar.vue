<template>
  <div class="form-group form-inline justify-content-center">
    <label class="mr-2" for="sido">시도 : </label>
    <b-form-select v-model="sidoCode" :options="sidos" @change="gugunList">
    </b-form-select>
    <label class="mr-2 ml-3" for="gugun">구군 : </label>
    <b-form-select v-model="gugunCode" :options="guguns" @change="dongList">
    </b-form-select>
    <label class="mr-2 ml-3" for="dong">읍면동 : </label>
    <b-form-select v-model="dongCode" :options="dongs" @change="searchApt2">
    </b-form-select>
    <label class="mr-2 ml-3" for="dong">아파트 이름 : </label>
    <input type="text" id="aptName" style="margin-right: 10pt" />
    <button type="button" id="aptSearchBtn" class="btn btn-primary">
      검색
    </button>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseSearchBar",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
    };
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "dongs", "houses"]),
    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    this.CLEAR_SIDO_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseList2",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
    ]),
    // sidoList() {
    //   this.getSido();
    // },
    gugunList() {
      console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      console.log(this.gugunCode);
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    // searchApt() {
    //   if (this.gugunCode) this.getHouseList(this.gugunCode);
    // },
    searchApt2() {
      // 동 이름으로 검색
      if (this.dongCode) this.getHouseList2(this.dongCode);
    },
  },
};
</script>

<style></style>

<template>
  <table class="table table-borderless">
    <tr>
      <td>
        <label for="sido">동으로 검색</label><br />
        <label for="sido">시도 : </label>
        <b-form-select v-model="sidoCode" :options="sidos" @change="gugunList">
        </b-form-select>
      </td>
    </tr>
    <tr>
      <td>
        <label for="gugun">구군 : </label>
        <b-form-select v-model="gugunCode" :options="guguns" @change="dongList">
        </b-form-select>
      </td>
    </tr>
    <tr>
      <td>
        <label for="dong">읍면동 : </label>
        <b-form-select v-model="dongCode" :options="dongs" @change="listByDong">
        </b-form-select>
      </td>
    </tr>
    <tr>
      <hr />
      <label class="mr-2 ml-3" for="dong">아파트 이름으로 검색</label>
      <br />
      <div class="input-group mb-3 px-3">
        <input
          type="text"
          class="form-control"
          placeholder="이름 입력"
          aria-label="이름 입력"
          aria-describedby="button-addon2"
          v-model="aptName"
        />
        <div class="input-group-append">
          <button
            @click="listByName"
            class="btn btn-primary"
            type="button"
            id="button-addon2"
          >
            검색
          </button>
        </div>
      </div>
    </tr>
  </table>
  <!-- <div class="form-group form-inline justify-content-center">
    <label class="mr-2" for="sido">시도 : </label>
    <b-form-select v-model="sidoCode" :options="sidos" @change="gugunList">
    </b-form-select>
    <label class="mr-2 ml-3" for="gugun">구군 : </label>
    <b-form-select v-model="gugunCode" :options="guguns" @change="dongList">
    </b-form-select>
    <label class="mr-2 ml-3" for="dong">읍면동 : </label>
    <b-form-select v-model="dongCode" :options="dongs" @change="searchApt2">
    </b-form-select>
  </div> -->
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
      aptName: "",
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
      "searchByDong",
      "searchByName",
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
    listByDong() {
      // 동 이름으로 검색
      if (this.dongCode) this.searchByDong(this.dongCode);
    },
    listByName() {
      console.log(this.aptName);
      if (this.aptName) {
        this.searchByName(this.aptName);
        this.aptName = "";
      }
    },
  },
};
</script>

<style></style>

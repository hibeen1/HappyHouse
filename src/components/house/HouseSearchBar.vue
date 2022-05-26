<template>
  <table class="table table-borderless">
    <tr>
      <td style="border-bottom: 2px solid grey">
        <label for="year">년도 : &nbsp;</label>
        <b-form-select style="width: 130px" v-model="year" :options="yearList">
        </b-form-select>
        <label for="month">&nbsp; 월 : &nbsp;</label>
        <b-form-select
          v-if="year != 2022"
          v-model="month"
          style="width: 100px"
          :options="monthList"
        >
        </b-form-select>
        <b-form-select
          v-else
          v-model="month"
          style="width: 100px"
          :options="monthListLastYear"
        >
        </b-form-select>
      </td>
    </tr>
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
</template>

<script>
import { mapState, mapActions, mapMutations, mapGetters } from "vuex";

const houseStore = "houseStore";
const memberStore = "memberStore";

export default {
  name: "HouseSearchBar",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      aptName: "",
      yearList: [
        "2015",
        "2016",
        "2017",
        "2018",
        "2019",
        "2020",
        "2021",
        "2022",
      ],
      monthList: [
        "01",
        "02",
        "03",
        "04",
        "05",
        "06",
        "07",
        "08",
        "09",
        "10",
        "11",
        "12",
      ],
      monthListLastYear: ["01", "02", "03", "04"],
      year: "2022",
      month: "04",
    };
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_DONG_LIST();
    this.sidoList();
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "dongs", "houses"]),
    ...mapState(memberStore, ["userInfo"]),
    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },
  methods: {
    ...mapGetters(memberStore, ["checkUserInfo"]),
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
    sidoList() {
      this.CLEAR_SIDO_LIST();
      this.getSido();
    },
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
      if (this.dongCode) {
        this.searchByDong({
          dong: this.dongCode,
          dealYear: this.year,
          dealMonth: this.month,
        });
      }
    },
    listByName() {
      if (this.aptName) {
        if (!this.year || !this.month) {
          alert("💡 년도 또는 월이 선택되지 않았습니다!");
        } else {
          this.searchByName({
            aptName: this.aptName,
            dealYear: this.year,
            dealMonth: this.month,
          });
          // this.aptName = "";
        }
      } else {
        alert("💡 한 글자 이상 입력 필수!!");
      }
    },
  },
};
</script>

<style></style>

<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>상세보기</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="listFavorite"
          >목록</b-button
        >
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-table-simple>
        <b-thead head-variant="light">
          <b-tr>
            <b-th>거래일</b-th>
            <b-th>거래가격</b-th>
            <b-th>크기</b-th>
            <b-th>층수</b-th>
          </b-tr>
        </b-thead>
        <b-tbody>
          <b-tr v-for="apt in dealList" :key="apt.aptCode">
            <b-td>{{
              apt.dealYear + "." + apt.dealMonth + "." + apt.dealDay
            }}</b-td>
            <b-td>{{ apt.recentPrice }} 만원</b-td>
            <b-td>{{ apt.area }}㎡</b-td>
            <b-td>{{ apt.floor }}층</b-td>
          </b-tr>
        </b-tbody>
      </b-table-simple>
    </b-row>
  </b-container>
</template>

<script>
// import moment from "moment";
import store from "@/store/index";
import { getDealList } from "@/api/favorite.js";

export default {
  name: "FavoriteDetail",
  data() {
    return {
      dealList: [],
      userInfo: store.getters["memberStore/checkUserInfo"],
    };
  },
  created() {
    this.getDeal();
  },
  methods: {
    listFavorite() {
      this.$router.push({ name: "favorite" });
    },
    async getDeal() {
      console.log("메서드 호출");
      await getDealList(
        this.$route.params.aptCode,
        (response) => {
          console.log("관심 목록 불러오기 성공!");
          this.dealList = response.data;
        },
        (error) => {
          console.log("관심 목록 에러");
        },
      );
    },
  },
  // filters: {
  //   dateFormat(regtime) {
  //     return moment(new Date(regtime)).format("YY.MM.DD hh:mm:ss");
  //   },
  // },
};
</script>

<style></style>

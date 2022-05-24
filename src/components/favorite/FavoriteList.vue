<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col v-if="userFavorite.length">
        <b-table-simple hover responsive>
          <b-thead head-variant="light">
            <b-tr>
              <b-th>순번</b-th>
              <b-th>아파트 이름</b-th>
              <b-th>도로명</b-th>
              <b-th>건축년도</b-th>
              <b-th></b-th>
            </b-tr>
          </b-thead>
          <tbody>
            <!-- 하위 component인 ListRow에 데이터 전달(props) -->
            <favorite-list-item
              v-for="(fav, index) in userFavorite"
              :key="fav.aptCode"
              :fav="fav"
              :userInfo="userInfo"
              :index="index + 1"
            />
          </tbody>
        </b-table-simple>
      </b-col>
      <!-- <b-col v-else class="text-center">도서 목록이 없습니다.</b-col> -->
    </b-row>
  </b-container>
</template>

<script>
import FavoriteListItem from "@/components/favorite/FavoriteListItem";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "FavoriteList",
  components: {
    FavoriteListItem,
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "userFavorite"]),
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
</style>

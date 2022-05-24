<template>
  <b-tr>
    <b-td>{{ index }}</b-td>
    <b-th class="text-left">
      <router-link
        :to="{ name: 'favoriteDetail', params: { aptCode: fav.aptCode } }"
        >{{ fav.apartmentName }}</router-link
      >
    </b-th>
    <b-td>{{ fav.roadName }}</b-td>
    <b-td>{{ fav.buildYear }}</b-td>
    <b-td>
      <input id="heart" type="checkbox" checked @change="changeCheck" />
      <label for="heart"> </label>
    </b-td>
  </b-tr>
</template>

<script>
import { deleteFavorite } from "@/api/favorite";
import { mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "FavoriteListItem",
  props: {
    userInfo: Object,
    fav: Object,
    index: Number,
  },
  methods: {
    ...mapActions(memberStore, ["getUserFavList"]),
    async changeCheck() {
      console.log("관심 목록 제거");
      if (this.userInfo.userid) {
        const params = {
          userid: this.userInfo.userid,
          aptCode: this.fav.aptCode,
        };
        console.log(params);
        await deleteFavorite(
          params,
          (response) => {
            console.log("서버 갔다옴");
            this.getUserFavList(this.userInfo.userid);
          },
          (error) => {
            console.log("서버 에러 발생");
            console.log(error);
          },
        );
      }
    },
  },
};
</script>

<style></style>

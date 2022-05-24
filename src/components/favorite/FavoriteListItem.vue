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
      <input id="heart" type="checkbox" checked />
      <label for="heart"> </label>
    </b-td>
  </b-tr>
</template>

<script>
// import moment from "moment";

export default {
  name: "FavoriteListItem",
  props: {
    fav: Object,
    index: Number,
  },
  // filters: {
  //   dateFormat(regtime) {
  //     return moment(new Date(regtime)).format("YY.MM.DD");
  //   },
  // },
};
</script>

<style></style>

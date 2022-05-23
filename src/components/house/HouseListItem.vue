<template>
  <b-row
    class="m-2"
    @click="selectHouse"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
    style="border: 2px solid mediumslateblue; border-radius: 8px"
  >
    <b-col class="text-left">
      [{{ house.sidoName }}] {{ house.apartmentName }}
      <div v-if="userInfo != null" style="text-align: right">
        <input
          id="heart"
          type="checkbox"
          @change="changeCheck"
          v-model="isFavorite"
        />
        <label for="heart"></label>
      </div>
    </b-col>
  </b-row>
</template>

<script>
import { mapActions, mapState, mapGetters } from "vuex";
import { addFavorite, deleteFavorite } from "@/api/favorite";

const houseStore = "houseStore";
const memberStore = "memberStore";

export default {
  name: "HouseListItem",
  data() {
    return {
      isColor: false,
      isFavorite: false,
      userid: "",
    };
  },
  props: {
    house: Object,
  },
  computed: {
    ...mapState(memberStore, ["userFavorite", "userInfo"]),
  },
  created() {
    if (this.checkUserInfo()) {
      this.userid = this.checkUserInfo().userid;
      this.isFavorite = false;

      for (let apt of this.userFavorite) {
        if (apt.aptCode == this.house.aptCode) {
          this.isFavorite = true;
          break;
        }
      }
    }
  },
  methods: {
    ...mapActions(houseStore, ["detailHouse"]),
    ...mapGetters(memberStore, ["checkUserInfo"]),
    ...mapActions(memberStore, ["getUserFavList"]),
    selectHouse() {
      // console.log("listRow : ", this.house);
      // this.$store.dispatch("getHouse", this.house);
      this.detailHouse(this.house);
    },
    colorChange(flag) {
      this.isColor = flag;
    },
    async changeCheck() {
      if (this.isFavorite) {
        console.log("관심 목록 추가");
        if (this.userid) {
          const params = {
            userid: this.userid,
            aptCode: this.house.aptCode,
          };
          await addFavorite(
            params,
            (response) => {
              console.log("서버 갔다옴");
              this.getUserFavList(this.userid);
            },
            (error) => {
              console.log("서버 에러 발생");
              console.log(error);
            },
          );
        }
      } else {
        console.log("관심 목록 제거");
        if (this.userid) {
          const params = {
            userid: this.userid,
            aptCode: this.house.aptCode,
          };
          console.log(params);
          await deleteFavorite(
            params,
            (response) => {
              console.log("서버 갔다옴");
              this.getUserFavList(this.userid);
            },
            (error) => {
              console.log("서버 에러 발생");
              console.log(error);
            },
          );
        }
      }
    },
  },
};
</script>

<style>
.apt {
  width: 50px;
}
.mouse-over-bgcolor {
  background-color: lightblue;
}
</style>

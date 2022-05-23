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
import { addFavorite, listFavorite } from "@/api/favorite";

const houseStore = "houseStore";
const memberStore = "memberStore";

export default {
  name: "HouseListItem",
  data() {
    return {
      isColor: false,
      isFavorite: false,
    };
  },
  props: {
    house: Object,
  },
  computed: {
    ...mapState(memberStore, ["userFavorite", "userInfo"]),
  },
  created() {
    this.isFavorite = false;

    for (let apt of this.userFavorite) {
      if (apt.aptCode == this.house.aptCode) {
        this.isFavorite = true;
        break;
      }
    }
  },
  methods: {
    ...mapActions(houseStore, ["detailHouse"]),
    ...mapGetters(memberStore, ["checkUserInfo"]),
    selectHouse() {
      // console.log("listRow : ", this.house);
      // this.$store.dispatch("getHouse", this.house);
      this.detailHouse(this.house);
    },
    colorChange(flag) {
      this.isColor = flag;
    },
    changeCheck() {
      if (this.isFavorite) {
        console.log("관심 목록 추가");
        if (this.checkUserInfo().userid) {
          const params = {
            userid: this.checkUserInfo().userid,
            aptCode: this.house.aptCode,
          };
          addFavorite(
            params,
            (response) => {
              console.log("서버 갔다옴");
            },
            (error) => {
              console.log("서버 에러 발생");
            },
          );
        }
      } else {
        console.log("관심 목록 제거");
      }
    },
    listFavorite() {
      const params = { userid: this.checkUserInfo().userid };
      listFavorite(
        params,
        (response) => {
          console.log("서버 갔다옴");
          console.log(response);
        },
        (error) => {
          console.log("서버 에러 발생");
        },
      );
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

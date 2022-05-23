<template>
  <b-row
    class="m-2"
    @click="selectHouse"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
    style="border: 2px solid mediumslateblue; border-radius: 8px"
  >
    <!-- <b-col cols="1">
      <b-img
        thumbnail
        :src="require(`@/assets/ssafy_logo.png`)"
        alt="Image 1"
      ></b-img>
    </b-col> -->
    <b-col class="text-left">
      [{{ house.sidoName }}] {{ house.apartmentName }}
      <div style="text-align: right">
        <input
          id="heart"
          type="checkbox"
          v-model="isFavorite"
          @change="changeHeart"
        />
        <label for="heart"></label>
      </div>
    </b-col>
  </b-row>
</template>

<script>
import { mapActions, mapState, mapGetters } from "vuex";
import { addFavorite } from "@/api/favorite";

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
    ...mapState(memberStore, ["userInfo"]),
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
    changeHeart() {
      if (this.isFavorite) {
        console.log("관심 목록 추가");
        if (this.checkUserInfo().userid) {
          const params = {
            userid: this.checkUserInfo().userid,
            aptCode: this.house.aptCode,
          };
          addFavorite(
            this.checkUserInfo().userid,
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
  },
};
</script>

<style scoped>
input[type="checkbox"] {
  display: none;
}

input[type="checkbox"] + label {
  position: relative;
  padding-left: 35px;
  display: inline-block;
  font-size: 16px;
}

input[type="checkbox"] + label:before {
  content: "\1F5A4";
  top: -11px;
  left: -8px;
  border: 1px solid transparent;
  padding: 10px;
  border-radius: 3px;
  display: block;
  position: absolute;
  transition: 0.5s ease;
}

input[type="checkbox"]:checked + label:before {
  border: 1px solid transparent;
  background-color: transparent;
}

input[type="checkbox"]:checked + label:after {
  content: "\1F49B";
  font-size: 18px;
  position: absolute;
  top: -1px;
  left: 1px;
  color: palevioletred;
  transition: 0.5s ease;
}

.apt {
  width: 50px;
}
.mouse-over-bgcolor {
  background-color: lightblue;
}
</style>

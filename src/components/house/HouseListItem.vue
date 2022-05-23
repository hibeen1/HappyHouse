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
        <input id="heart" type="checkbox" v-model="isFavorite" />
        <label for="heart"></label>
      </div>
    </b-col>
  </b-row>
</template>

<script>
import { mapActions } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseListItem",
  data() {
    return {
      isColor: false,
      isFavorite: true,
    };
  },
  props: {
    house: Object,
  },
  methods: {
    ...mapActions(houseStore, ["detailHouse"]),
    selectHouse() {
      // console.log("listRow : ", this.house);
      // this.$store.dispatch("getHouse", this.house);
      this.detailHouse(this.house);
    },
    colorChange(flag) {
      this.isColor = flag;
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

<template>
  <div>
    <b-navbar
      toggleable="lg"
      type="dark"
      style="
        background-color: white;
        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
      "
    >
      <b-navbar-brand href="#">
        <router-link to="/">
          <img
            src="@/assets/home.png"
            class="d-inline-block align-middle"
            width="60px"
            alt="home"
          />
        </router-link>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav id="navBar">
          <b-nav-item href="#"
            ><router-link :to="{ name: 'home' }" class="link"
              ><img src="@/assets/compass.png" width="40" id="homeImg" />
              HOME</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'announcement' }" class="link"
              ><img src="@/assets/note.png" width="40" id="noticeImg" />
              공지사항</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'board' }" class="link"
              ><img src="@/assets/bubble-chat.png" width="40" id="boardImg" />
              게시판</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'favorite' }" class="link"
              ><img src="@/assets/location.png" width="40" id="locationImg" />
              관심매물</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'instagram' }" class="link"
              ><img src="@/assets/two.png" width="40" id="twoImg" /> ABOUT
            </router-link></b-nav-item
          >
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto" v-if="userInfo">
          <b-nav-item class="align-self-center"
            ><b-avatar
              variant="danger"
              v-text="userInfo ? userInfo.userid.charAt(0).toUpperCase() : ''"
            ></b-avatar
            ><label for="" style="color: black"
              >&nbsp; {{ userInfo.username }}({{ userInfo.userid }})님
              환영합니다.</label
            ></b-nav-item
          >
          <b-nav-item class="align-self-center"
            ><router-link
              :to="{ name: 'mypage' }"
              class="link align-self-center"
              ><img src="@/assets/user.png" width="40" /></router-link
          ></b-nav-item>
          <b-nav-item
            class="link align-self-center"
            @click.prevent="onClickLogout"
            ><a href="#" style="color: blue">로그아웃</a></b-nav-item
          >
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto" v-else>
          <b-nav-item-dropdown right>
            <template #button-content>
              <img src="@/assets/menu.png" width="30" />
            </template>
            <b-dropdown-item href="#"
              ><router-link :to="{ name: 'signUp' }" class="link"
                ><b-icon icon="person-circle"></b-icon> 회원가입</router-link
              ></b-dropdown-item
            >
            <b-dropdown-item href="#"
              ><router-link :to="{ name: 'signIn' }" class="link"
                ><b-icon icon="key"></b-icon> 로그인</router-link
              ></b-dropdown-item
            >
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
// import ms from "@/store/modules/memberStore";

const memberStore = "memberStore";

export default {
  name: "HeaderNaviBar",
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  mounted() {
    this.$nextTick(function () {
      const homeImg = document.querySelector("#homeImg");
      homeImg.addEventListener("mouseenter", () => {
        homeImg.src = require("@/assets/compass.gif");
      });
      homeImg.addEventListener("mouseleave", () => {
        homeImg.src = require("@/assets/compass.png");
      });

      const noticeImg = document.querySelector("#noticeImg");
      noticeImg.addEventListener("mouseenter", () => {
        noticeImg.src = require("@/assets/note.gif");
      });
      noticeImg.addEventListener("mouseleave", () => {
        noticeImg.src = require("@/assets/note.png");
      });

      const boardImg = document.querySelector("#boardImg");
      boardImg.addEventListener("mouseenter", () => {
        boardImg.src = require("@/assets/bubble-chat.gif");
      });
      boardImg.addEventListener("mouseleave", () => {
        boardImg.src = require("@/assets/bubble-chat.png");
      });

      const locationImg = document.querySelector("#locationImg");
      locationImg.addEventListener("mouseenter", () => {
        locationImg.src = require("@/assets/location.gif");
      });
      locationImg.addEventListener("mouseleave", () => {
        locationImg.src = require("@/assets/location.png");
      });

      const twoImg = document.querySelector("#twoImg");
      twoImg.addEventListener("mouseenter", () => {
        twoImg.src = require("@/assets/two.gif");
      });
      twoImg.addEventListener("mouseleave", () => {
        twoImg.src = require("@/assets/two.png");
      });
    });
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      // console.log("memberStore : ", ms);
      console.log("여기!!");
      console.log(this);
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      console.log(this.$route.path);

      // 새로고침
      this.$router.go();

      if (this.$route.path != "/") this.$router.push({ name: "home" });
    },
  },
};
</script>

<style>
.link {
  color: black;
}

#navBar .nav-link {
  width: 150px;
}

img {
  margin: 0;
}

.link:hover {
  color: tomato;
}
</style>

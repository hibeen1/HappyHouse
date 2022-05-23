import jwt_decode from "jwt-decode";
import { login } from "@/api/member.js";
import { findById } from "../../api/member";
import { listFavorite } from "@/api/favorite";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    userFavorite: [],
  },
  getters: {
    checkUserInfo: function (state) {
      console.log(state);
      return state.userInfo;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
      console.log(userInfo);
    },
    SET_USER_FAVORITE: (state, userFavorite) => {
      console.log("관심 매물 설정");
      state.userFavorite = userFavorite;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          if (response.data.message === "success") {
            let token = response.data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", token);
            // this.getUserFavList(response.data);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        () => {},
      );
    },
    getUserInfo({ commit, dispatch }, token) {
      let decode_token = jwt_decode(token);
      findById(
        decode_token.userid,
        (response) => {
          if (response.data.message === "success") {
            commit("SET_USER_INFO", response.data.userInfo);
            dispatch("getUserFavList", response.data.userInfo.userid);
          } else {
            console.log("유저 정보 없음!!");
          }
        },
        (error) => {
          console.log(error);
        },
      );
    },
    getUserFavList: ({ commit }, userid) => {
      const params = { userid };
      listFavorite(
        params,
        (response) => {
          commit("SET_USER_FAVORITE", response.data);
        },
        (error) => {
          console.log("서버 에러 발생");
        },
      );
    },
  },
};

export default memberStore;

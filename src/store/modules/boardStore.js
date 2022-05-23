import { getArticle } from "@/api/board";

const boardStore = {
  namespaced: true,
  state: {
    // article: getArticle(),
  },
  getters: {
    checkArticle: function (state) {
      return state.article;
    },
  },
  mutations: {},
  actions: {},
};

export default boardStore;

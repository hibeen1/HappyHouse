import Vue from "vue";
import VueRouter from "vue-router";
// import HomeView from "../views/HomeView.vue";
import HouseView from "@/views/HouseView.vue";

import store from "@/store/index.js";

Vue.use(VueRouter);

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const getUserInfo = store._actions["memberStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  if (checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    next({ name: "signIn" });
    // router.push({ name: "signIn" });
  } else {
    // console.log("로그인 했다.");
    next();
  }
};

const onlyAdmin = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const getUserInfo = store._actions["memberStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  if (checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    next({ name: "signIn" });
    // router.push({ name: "signIn" });
  } else if (checkUserInfo.userid === "admin") {
    // console.log("로그인 했다.");
    console.log("관리자입니당!!");
    next();
  } else {
    alert("관리자만이 접근할 수 있습니다.");
    next({ name: "home" });
  }
};

const articleAuth = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const getUserInfo = store._actions["memberStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  if (checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    next({ name: "signIn" });
    // router.push({ name: "signIn" });
  } else {
    console.log("여기ㅏ!!!!!!!!!!!!!!!!!!!");
    console.log(from);
    // console.log("로그인 했다.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    component: HouseView,
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/MemberView.vue"),
    children: [
      {
        path: "singin",
        name: "signIn",
        component: () => import("@/components/user/MemberLogin.vue"),
      },
      {
        path: "singup",
        name: "signUp",
        component: () => import("@/components/user/MemberRegister.vue"),
      },
      {
        path: "mypage",
        name: "mypage",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/user/MemberMyPage.vue"),
      },
      {
        path: "mypage/modify",
        name: "memberModify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/user/MemberModify.vue"),
      },
    ],
  },
  {
    path: "/announcement",
    name: "announcement",
    component: () => import("@/views/AnnouncementView.vue"),
    redirect: "/announcement/list",
    children: [
      {
        path: "list",
        name: "announcementList",
        component: () =>
          import("@/components/announcement/AnnouncementList.vue"),
      },
      {
        path: "write",
        name: "announcementRegister",
        beforeEnter: onlyAdmin,
        component: () =>
          import("@/components/announcement/AnnouncementRegister.vue"),
      },
      {
        path: "detail/:articleno",
        name: "announcementDetail",
        beforeEnter: onlyAuthUser,
        component: () =>
          import("@/components/announcement/AnnouncementDetail.vue"),
      },
      {
        path: "modify/:articleno",
        name: "announcementModify",
        beforeEnter: onlyAdmin,
        component: () =>
          import("@/components/announcement/AnnouncementModify.vue"),
      },
    ],
  },
  {
    path: "/board",
    name: "board",
    component: () => import("@/views/BoardView.vue"),
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardList",
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "write",
        name: "boardRegister",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardRegister.vue"),
      },
      {
        path: "detail/:articleno",
        name: "boardDetail",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardDetail.vue"),
      },
      {
        path: "modify/:articleno",
        name: "boardModify",
        beforeEnter: articleAuth,
        meta: { authRequired: true },
        component: () => import("@/components/board/BoardModify.vue"),
      },
    ],
  },
  {
    path: "/instagram",
    name: "instagram",
    component: () => import("@/views/InstagramView.vue"),
  },
  {
    path: "/house",
    name: "house",
    // beforeEnter: onlyAuthUser,
    component: HouseView,
  },
  {
    path: "/todo",
    name: "todo",
    beforeEnter: onlyAuthUser,
    component: () => import("@/views/TodoView.vue"),
  },
  {
    path: "/favorite",
    name: "favorite",
    beforeEnter: onlyAuthUser,
    component: () => import("@/views/FavoriteView.vue"),
    redirect: "/favorite/list",
    children: [
      {
        path: "list",
        name: "favotieList",
        component: () => import("@/components/favorite/FavoriteList.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;

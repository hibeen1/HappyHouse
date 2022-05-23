<template>
  <b-container class="mt-4" v-if="userInfo">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>내정보</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-jumbotron>
          <template #header>My Page</template>

          <template #lead> 내 정보 수정페이지입니다. </template>

          <hr class="my-4" />

          <b-container class="mt-4">
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">아이디</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.userid }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이름</b-col
              ><b-col cols="4" align-self="start">{{
                userInfo.username
              }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이메일</b-col
              ><b-col cols="4" align-self="start">
                <b-input
                  type="email"
                  v-model="userInfo.email"
                  placeholder="이메일을 입력하세요"
                  required
                />
              </b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">비밀번호</b-col
              ><b-col cols="4" align-self="start">
                <b-input
                  type="password"
                  v-model="userInfo.userpwd"
                  placeholder="비밀번호를 입력하세요"
                  required
                />
              </b-col>
              <b-col cols="2"></b-col>
            </b-row>
          </b-container>
          <hr class="my-4" />

          <b-button variant="primary" class="mr-1" @click="modifyMyInfo"
            >수정</b-button
          >
          <b-button variant="primary" class="mr-1" @click="goBack"
            >취소</b-button
          >
        </b-jumbotron>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { modifyMember } from "@/api/member";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "MemberModify",
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    this.userInfo = memberStore.userInfo;
  },
  methods: {
    modifyMyInfo() {
      modifyMember(
        {
          userid: this.userInfo.userid,
          username: this.userInfo.username,
          userpwd: this.userInfo.userpwd,
          email: this.userInfo.email,
        },
        ({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          this.$router.push({ name: "mypage" });
        },
        (error) => {
          console.log(error);
        },
      );
    },
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style></style>

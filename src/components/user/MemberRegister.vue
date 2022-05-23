<template>
  <v-container>
    <b-alert variant="secondary" show><h3>로그인</h3></b-alert>
    <v-row>
      <v-col :cols="3"></v-col>
      <v-col :cols="6">
        <v-form ref="form" v-model="valid">
          <v-text-field
            label="아이디"
            v-model="userInfo.userid"
            :rules="[
              rule.required,
              rule.minLength,
              rule.maxLength,
              rule.idCheck,
            ]"
          >
          </v-text-field>
          <v-text-field
            label="이름"
            v-model="userInfo.username"
            :rules="[rule.required, rule.maxLength]"
          >
          </v-text-field>
          <v-text-field
            label="비밀번호"
            type="password"
            v-model="userInfo.userpwd"
            :rules="[rule.required, rule.minLength, rule.maxLength]"
          >
          </v-text-field>
          <v-text-field
            label="이메일"
            type="email"
            v-model="userInfo.email"
            :rules="[rule.required, rule.emailRule]"
          >
          </v-text-field>
          <v-btn :disabled="!valid" @click="onSubmit">회원가입</v-btn>
          <v-btn @click="clear">초기화</v-btn>
        </v-form>
      </v-col>
      <v-col :cols="3"></v-col>
    </v-row>
  </v-container>
</template>

<script>
import { registerMemeber, idCheck } from "@/api/member";

export default {
  data() {
    return {
      userInfo: {
        username: "",
        userid: "",
        userpwd: "",
        email: "",
      },
      rule: {
        required: (v) => !!v || "필수 항목입니다",
        minLength: (v) => v.length >= 6 || "6자 이상 입력해주세요",
        maxLength: (v) => v.length <= 16 || "16자 이하로 입력해주세요",
        emailRule: (v) => /.+@+/.test(v) || "이메일 형식에 맞지 않습니다",
        // idCheck: (v) => this.idCheck(v),
      },
      valid: true,
    };
  },
  methods: {
    // idCheck() {
    //   idCheck(
    //     this.userInfo.userid,
    //     ({ data }) => {
    //       if (data === "success") {
    //         alert(data);
    //       } else {
    //         alert("힝!");
    //       }
    //     },
    //     (error) => {
    //       console.log(error);
    //       console.log("힝 속았지?");
    //       console.log(error.response.data);
    //     },
    //   );
    // },
    clear() {
      this.userInfo.username = "";
      this.userInfo.userid = "";
      this.userInfo.userpwd = "";
      this.userInfo.email = "";
    },
    onSubmit(event) {
      event.preventDefault();
      registerMemeber(
        {
          userid: this.userInfo.userid,
          username: this.userInfo.username,
          userpwd: this.userInfo.userpwd,
          email: this.userInfo.email,
        },
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
            alert(msg);
            this.$router.push({ name: "signIn" });
          } else {
            alert(msg);
            this.onReset();
          }
        },
        (error) => {
          console.log(error);
          console.log("힝 속았지?");
          console.log(error.response.data);
        },
      );
    },
  },
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(72, 190, 233, 0.3) 30%
  );
}
</style>

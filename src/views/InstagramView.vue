<template>
  <b-container class="bv-example-row mt-3 text-center">
    <h3 class="underline-green">
      <b-icon icon="instagram"></b-icon> 인별그램 Service
    </h3>
    <b-row rows="10">
      <br />
      <br />
      <br />
      <br />
      <br />
    </b-row>
    <b-row>
      <b-col id="ingbeen">
        <div class="frame1">
          <div class="door">
            <div class="handle"></div>
          </div>
          <!-- </div> -->
          <!-- <meta http-equiv="refresh" content="2;url=http://www.google.com/" /> -->
        </div>
      </b-col>
      <b-col>
        <div class="frame2" id="jihong">
          <div class="door">
            <div class="handle"></div>
          </div>
          <!-- </div> -->
          <!-- <meta http-equiv="refresh" content="2;url=http://www.google.com/" /> -->
        </div>
      </b-col>
    </b-row>
    <br />
    <br />
    <br />
    <br />
    <b-row>
      <b-col>
        <h2>개발자 황인빈</h2>
      </b-col>
      <b-col>
        <h2>개발자 최지홍</h2>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "InstagramView",
};
</script>

<style scoped>
.underline-green {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(56, 233, 40, 0.3) 30%
  );
}
.frame1 {
  position: relative;
  background-color: yellow;
  top: 20px;
  width: 150px;
  height: 150px;
  margin: 0 auto;
  background: url("../assets/IMG_1165.jpeg") no-repeat;
  background-position: center;
  /* -webkit-background-size: auto 100%; */
  background-size: auto 100%;
  perspective: 480px;
}
.frame2 {
  position: relative;
  background-color: yellow;
  top: 20px;
  width: 150px;
  height: 150px;
  margin: 0 auto;
  background: url("../assets/jihong.jpeg") no-repeat;
  background-position: center;
  /* -webkit-background-size: auto 100%; */
  background-size: auto 100%;
  perspective: 480px;
}
.door {
  position: relative;
  width: 150px;
  height: 150px;
  background-color: rgb(225, 135, 0);
  transform: rotateY(-100deg);
  transform-origin: left;
  animation: opendoor 2s ease-in-out 1 alternate;
}
.handle {
  position: absolute;
  top: 60px;
  right: 10px;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  background-color: rgb(252, 194, 0);
}
@keyframes opendoor {
  from {
    transform: rotateY(-1deg);
  }
  to {
    transform: rotateY(-100deg);
  }
}
</style>

<template>
  <b-container>
    <b-row>
      <b-col>
        <div id="map" style="width: 100%; height: 500px"></div>
      </b-col>
    </b-row>
    <b-container v-if="house" class="bv-example-row">
      <b-row>
        <b-col
          ><h3>{{ house.aptName }}</h3></b-col
        >
      </b-row>
      <b-row class="mb-2 mt-1">
        <b-col
          ><b-img :src="require('@/assets/apt.png')" fluid-grow></b-img
        ></b-col>
      </b-row>
    </b-container>
  </b-container>
</template>

<script>
import { mapState } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseDetail",
  data() {
    return {
      is_show: false,
      map: null,
      markers: [],
      overlays: [],
      ps: null,
      infowindow: null,
      customOverlay: null,
      geocoder: null,
      markerPositions1: [
        [33.452278, 126.567803],
        [33.452671, 126.574792],
        [33.451744, 126.572441],
      ],
    };
  },
  computed: {
    ...mapState(houseStore, ["house"]),
  },
  watch: {
    house: {
      handler: function (val) {
        if (val) {
          this.displayMarker(val);
        }
      },
      deep: true,
    },
  },
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=2a0f6891f55df01ae4f0b78742c5e7a5&libraries=services";
      /* global kakao */
      script.addEventListener("load", () => {
        kakao.maps.load(this.initMap);

        kakao.maps.load(() => {
          // 장소 검색 객체를 생성합니다
          this.ps = new kakao.maps.services.Places();

          // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
          this.infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

          //주소-좌표 변환 객체를 생성합니다
          this.geocoder = new kakao.maps.services.Geocoder();
        });
      });
      document.head.appendChild(script);
    }
  },
  methods: {
    closeOverlay() {
      console.log("테스트");
    },
    toggle() {
      this.is_show = !this.is_show;
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };
      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);
      // this.displayMarker(this.house);
    },
    displayMarker(place) {
      var bounds = new kakao.maps.LatLngBounds();

      var placePosition = new kakao.maps.LatLng(place.lat, place.lng);

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
      // LatLngBounds 객체에 좌표를 추가합니다
      bounds.extend(placePosition);

      // 지도에 마커를 표시합니다
      let marker = new kakao.maps.Marker({
        map: this.map,
        position: placePosition,
      });

      // 커스텀 오버레이에 표시할 컨텐츠 입니다
      // 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
      // 별도의 이벤트 메소드를 제공하지 않습니다
      var content =
        `<div class="wrap">
            <div class="info">
                <div class="title">` +
        this.house.aptName +
        `</div>
                <div class="body">
                    <div class="img">
                        <img :src="require('@/assets/apt.png')" width="73" height="70">
                    </div>
                    <div class="desc">
                        <div class="ellipsis">` +
        this.house.sidoName +
        " " +
        this.house.gugunName +
        `</div>
                        <div class="jibun ellipsis">` +
        this.house.dongName +
        " " +
        this.house.jibun +
        `</div>
                        <div class="ellipsis">최근 시세: ` +
        this.house.recentPrice +
        `만원</div>
                    </div>
                </div>
            </div>
        </div>`;

      // 마커 위에 커스텀오버레이를 표시합니다
      // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
      let overlay = new kakao.maps.CustomOverlay({
        content: content,
        map: this.map,
        position: marker.getPosition(),
      });

      this.removeMarker();
      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

      overlay.setMap(this.map);
      this.overlays.push(overlay);

      let temp = this.map;
      let status = true;

      // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
      kakao.maps.event.addListener(marker, "click", function () {
        console.log("마커 클릭");
        if (status) overlay.setMap(null);
        else overlay.setMap(temp);
        status = !status;
      });

      this.map.setBounds(bounds);
    },
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
      for (var j = 0; j < this.overlays.length; j++) {
        this.overlays[j].setMap(null);
      }
      this.overlays = [];
    },
  },
};
</script>

<style>
.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
}
.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info .link {
  color: #5085bb;
}
</style>

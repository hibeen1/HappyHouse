<template>
  <div class="outer view_map" id="container">
    <div
      id="mapWrapper"
      style="width: 100%; height: 1000px; position: relative"
    >
      <div id="map" style="width: 100%; height: 100%"></div>
      <!-- 지도를 표시할 div 입니다 -->
      <input
        type="button"
        id="btnRoadview"
        @click="toggleMap(false)"
        title="로드뷰 보기"
        value="로드뷰"
      />
    </div>
    <div
      id="rvWrapper"
      style="width: 100%; height: 1000px; position: absolute; top: 0; left: 0"
    >
      <div id="roadview" style="height: 100%"></div>
      <!-- 로드뷰를 표시할 div 입니다 -->
      <input
        type="button"
        id="btnMap"
        @click="toggleMap(true)"
        title="지도 보기"
        value="지도"
      />
    </div>
  </div>
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
      roadMarkers: [],
      ps: null,
      infowindow: null,
      customOverlay: null,
      geocoder: null,
      container: null,
      mapWrapper: null,
      btnRoadview: null,
      btnMap: null,
      rvContainer: null,
      mapContainer: null,
      roadview: null,
      roadviewClient: null,
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

        this.container = document.getElementById("container"); // 지도와 로드뷰를 감싸고 있는 div 입니다
        this.mapWrapper = document.getElementById("mapWrapper"); // 지도를 감싸고 있는 div 입니다
        this.btnRoadview = document.getElementById("btnRoadview"); // 지도 위의 로드뷰 버튼, 클릭하면 지도는 감춰지고 로드뷰가 보입니다
        this.btnMap = document.getElementById("btnMap"); // 로드뷰 위의 지도 버튼, 클릭하면 로드뷰는 감춰지고 지도가 보입니다
        this.rvContainer = document.getElementById("roadview"); // 로드뷰를 표시할 div 입니다
        this.mapContainer = document.getElementById("map"); // 지도를 표시할 div 입니다

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
    toggleMap(active) {
      if (active) {
        // 지도가 보이도록 지도와 로드뷰를 감싸고 있는 div의 class를 변경합니다
        this.container.className = "view_map";
      } else {
        // 지도가 숨겨지도록 지도와 로드뷰를 감싸고 있는 div의 class를 변경합니다
        this.container.className = "view_roadview";
      }
    },
    closeOverlay() {
      console.log("테스트");
    },
    toggle() {
      this.is_show = !this.is_show;
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 3,
      };
      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);

      // 로드뷰 객체를 생성합니다
      this.roadview = new kakao.maps.Roadview(this.rvContainer);

      this.roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체
      this.displayMarker();
    },
    displayMarker(place = { lat: 37.566826, lng: 126.9786567 }) {
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

      this.removeMarker();

      if (this.house) {
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
      }

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

      let currView = this.roadview;

      // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
      this.roadviewClient.getNearestPanoId(placePosition, 50, (panoId) => {
        currView.setPanoId(panoId, placePosition); //panoId와 중심좌표를 통해 로드뷰 실행
      });

      // 특정 장소가 잘보이도록 로드뷰의 적절한 시점(ViewPoint)을 설정합니다
      // Wizard를 사용하면 적절한 로드뷰 시점(ViewPoint)값을 쉽게 확인할 수 있습니다
      currView.setViewpoint({
        pan: 321,
        tilt: 0,
        zoom: 0,
      });

      // 로드뷰 초기화가 완료되면
      kakao.maps.event.addListener(currView, "init", function () {
        // 로드뷰에 특정 장소를 표시할 마커를 생성하고 로드뷰 위에 표시합니다
        let roadMarker = new kakao.maps.Marker({
          position: placePosition,
          map: currView,
        });

        roadMarker.setMap(currView);
        this.roadMarkers.push(roadMarker);
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
      for (var k = 0; k < this.roadMarkers.length; k++) {
        this.roadMarkers[k].setMap(null);
      }
      this.roadMarkers = [];
    },
  },
};
</script>

<style>
#container {
  overflow: hidden;
  height: 1000px;
  position: relative;
}
#btnRoadview,
#btnMap {
  position: absolute;
  top: 5px;
  left: 5px;
  padding: 7px 12px;
  font-size: 14px;
  border: 1px solid #dbdbdb;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.04);
  z-index: 1;
  cursor: pointer;
}
#btnRoadview:hover,
#btnMap:hover {
  background-color: #fcfcfc;
  border: 1px solid #c1c1c1;
}
#container.view_map #mapWrapper {
  z-index: 10;
}
#container.view_map #btnMap {
  display: none;
}
#container.view_roadview #mapWrapper {
  z-index: 0;
}
#container.view_roadview #btnRoadview {
  display: none;
}

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

.outer,
table {
  width: 100%;
  height: 100%;
}
</style>

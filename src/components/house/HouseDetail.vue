<template>
  <div class="outer view_map" id="container">
    <div
      id="mapWrapper"
      style="width: 100%; height: 1000px; position: relative"
    >
      <div id="map" style="width: 100%; height: 100%"></div>
      <!-- 지도를 표시할 div 입니다 -->
      <ul id="category">
        <b-form-select
          v-model="category"
          :options="categories"
          @change="onChangeCategory"
        ></b-form-select>
      </ul>
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
      map: null, // 지도 객체
      markers: [], // 마커들을 담기 위한 배열
      overlays: [], // 아파트 오버레이를 담기 위한 배열
      roadMarkers: [], // 로드뷰에 찍히는 마커를 담기 위한 배열
      placeMarkers: [], // 주변 상권 마커를 담기 위한 배열
      contentNode: null, // 커스텀 오버레이 컨텐츠 엘리먼트
      ps: null, // 장소 검색 객체
      infowindow: null,
      customOverlay: null, // 커스텀 오버레이
      placeOverlay: null, // 장소 정보 오버레이
      geocoder: null,
      container: null,
      mapWrapper: null,
      btnRoadview: null, // 로드뷰 버튼
      btnMap: null, // 지도 버튼
      rvContainer: null,
      mapContainer: null,
      roadview: null, // 로드뷰 객체
      roadviewClient: null, // 로드뷰 헬퍼
      category: null, // 현재 선택된 카테고리를 가지고 있을 변수
      categories: [
        { value: null, text: "카테고리" },
        { value: "MT1", text: "대형마트" },
        { value: "CS2", text: "편의점" },
        { value: "PS3", text: "어린이집, 유치원" },
        { value: "SC4", text: "학교" },
        { value: "AC5", text: "학원" },
        { value: "PK6", text: "주차장" },
        { value: "OL7", text: "주유소, 충전소" },
        { value: "SW8", text: "지하철역" },
        { value: "BK9", text: "은행" },
        { value: "CT1", text: "문화시설" },
        { value: "AG2", text: "중개업소" },
        { value: "PO3", text: "공공기관" },
        { value: "AT4", text: "관광명소" },
        { value: "AD5", text: "숙박" },
        { value: "FD6", text: "음식점" },
        { value: "CE7", text: "카페" },
        { value: "HP8", text: "병원" },
        { value: "PM9", text: "약국" },
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
    this.$nextTick(function () {
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

            // 상권 정보 시작
            this.placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
            this.contentNode = document.createElement("div"); // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
            // 상권 정보 끝

            // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
            this.contentNode.className = "placeinfo_wrap";

            // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
            // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
            this.addEventHandle(
              this.contentNode,
              "mousedown",
              kakao.maps.event.preventMap,
            );
            this.addEventHandle(
              this.contentNode,
              "touchstart",
              kakao.maps.event.preventMap,
            );

            // 커스텀 오버레이 컨텐츠를 설정합니다
            this.placeOverlay.setContent(this.contentNode);
          });
        });

        document.head.appendChild(script);
      }
    });
  },
  methods: {
    onChangeCategory() {
      // 지도에 표시되고 있는 마커를 제거합니다
      this.removePlaceMarker();
      if (!this.category) return;

      this.searchPlaces();
    },
    // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent("on" + type, callback);
      }
    },
    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removePlaceMarker() {
      // console.log("주변 마커 지우기");
      for (var i = 0; i < this.placeMarkers.length; i++) {
        this.placeMarkers[i].setMap(null);
      }
      this.placeMarkers = [];
    },
    // 카테고리 검색을 요청하는 함수입니다
    searchPlaces() {
      if (!this.category) {
        return;
      }

      // // 커스텀 오버레이를 숨깁니다
      this.placeOverlay.setMap(null);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removePlaceMarker();

      // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
      let callback = (data, status, pagination) => {
        if (status === kakao.maps.services.Status.OK) {
          // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
          // console.log(data);
          this.displayPlaces(data);
        } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
          // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
          console.log("데이터 없음!!");
        } else if (status === kakao.maps.services.Status.ERROR) {
          // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
          console.log("에러!!");
        }
      };

      let placePosition;

      if (!this.house) {
        placePosition = new kakao.maps.LatLng(37.566826, 126.9786567);
      } else {
        placePosition = new kakao.maps.LatLng(this.house.lat, this.house.lng);
      }

      this.ps.categorySearch(this.category, callback, {
        location: placePosition,
        useMapBounds: true,
      });
    },
    // 지도에 마커를 표출하는 함수입니다
    displayPlaces(places) {
      let displayPlaceInfo = this.displayPlaceInfo;
      let temp = this.map;
      let status = true;

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = this.addMarker(
          new kakao.maps.LatLng(places[i].y, places[i].x),
        );
        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        (function (marker, place) {
          kakao.maps.event.addListener(marker, "click", function () {
            displayPlaceInfo(place);
          });
        })(marker, places[i]);
      }
    },
    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker(position) {
      var imageSrc =
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; // 마커 이미지 url, 스프라이트 이미지를 씁니다
      // 마커 이미지의 이미지 크기 입니다
      var imageSize = new kakao.maps.Size(24, 35);

      // 마커 이미지를 생성합니다
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
        map: this.map, // 마커를 표시할 지도
        position: position, // 마커를 표시할 위치
        title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image: markerImage, // 마커 이미지
      });

      // console.log(marker);

      marker.setMap(this.map);
      this.placeMarkers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },
    // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
    displayPlaceInfo(place) {
      var content =
        '<div class="placeinfo">' +
        '   <a class="title" href="' +
        place.place_url +
        '" target="_blank" title="' +
        place.place_name +
        '">' +
        place.place_name +
        "</a>";

      if (place.road_address_name) {
        content +=
          '    <span title="' +
          place.road_address_name +
          '">' +
          place.road_address_name +
          "</span>" +
          '  <span class="jibun" title="' +
          place.address_name +
          '">(지번 : ' +
          place.address_name +
          ")</span>";
      } else {
        content +=
          '    <span title="' +
          place.address_name +
          '">' +
          place.address_name +
          "</span>";
      }

      content +=
        '    <span class="tel">' +
        place.phone +
        "</span>" +
        "</div>" +
        '<div class="after"></div>';

      this.contentNode.innerHTML = content;
      this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
      this.placeOverlay.setMap(this.map);
    },
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
      // 로드뷰 시작
      this.container = document.getElementById("container"); // 지도와 로드뷰를 감싸고 있는 div 입니다
      this.mapWrapper = document.getElementById("mapWrapper"); // 지도를 감싸고 있는 div 입니다
      this.btnRoadview = document.getElementById("btnRoadview"); // 지도 위의 로드뷰 버튼, 클릭하면 지도는 감춰지고 로드뷰가 보입니다
      this.btnMap = document.getElementById("btnMap"); // 로드뷰 위의 지도 버튼, 클릭하면 로드뷰는 감춰지고 지도가 보입니다
      this.rvContainer = document.getElementById("roadview"); // 로드뷰를 표시할 div 입니다
      this.mapContainer = document.getElementById("map"); // 지도를 표시할 div 입니다
      // 로드뷰 끝

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

      // 지도에 idle 이벤트를 등록합니다
      kakao.maps.event.addListener(this.map, "idle", this.searchPlaces);
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

      // console.log(marker);

      this.removeMarker();

      if (this.house) {
        let bubun = this.house.roadNameBubun.replace(/^0*/g, "")
          ? "-" + this.house.roadNameBubun.replace(/^0*/g, "")
          : "";

        // 커스텀 오버레이에 표시할 컨텐츠 입니다
        // 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
        // 별도의 이벤트 메소드를 제공하지 않습니다
        var content =
          `<div class="wrap">
            <div class="info">
                <div class="title">` +
          this.house.apartmentName +
          `</div>
                <div class="body">
                    <div class="desc">
                        <div class="ellipsis">` +
          this.house.sidoName +
          " " +
          this.house.gugunName +
          " " +
          this.house.dong +
          `</div>
                        <div class="jibun ellipsis">` +
          this.house.roadName +
          " " +
          this.house.roadNameBonbun.replace(/^0*/g, "") +
          bubun +
          `(${this.house.jibun})` +
          `</div>
                        <div class="ellipsis">가격: ` +
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
      this.roadviewClient.getNearestPanoId(placePosition, 100, (panoId) => {
        currView.setPanoId(panoId, placePosition); //panoId와 중심좌표를 통해 로드뷰 실행
      });

      // 특정 장소가 잘보이도록 로드뷰의 적절한 시점(ViewPoint)을 설정합니다
      // Wizard를 사용하면 적절한 로드뷰 시점(ViewPoint)값을 쉽게 확인할 수 있습니다
      currView.setViewpoint({
        pan: 321,
        tilt: 0,
        zoom: 0,
      });

      let roadMarkers = this.roadMarkers;

      // 로드뷰 초기화가 완료되면
      kakao.maps.event.addListener(currView, "init", function () {
        // 로드뷰에 특정 장소를 표시할 마커를 생성하고 로드뷰 위에 표시합니다
        let roadMarker = new kakao.maps.Marker({
          position: placePosition,
          map: currView,
        });

        roadMarker.setMap(currView);
        roadMarkers.push(roadMarker);
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
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 350px;
}
#category {
  position: absolute;
  top: 5px;
  left: 80px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
  width: 200px;
}
ul {
  padding: 0;
}

.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}

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
  border-radius: 5px;
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

<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글작성</h3></b-alert>
      </b-col>
    </b-row>
    <announcement-input-item type="register" />
  </b-container>
</template>

<script>
import AnnouncementInputItem from "@/components/announcement/item/AnnouncementInputItem.vue";

export default {
  name: "AnnouncementWrite",
  components: {
    AnnouncementInputItem,
  },
};
</script>

<style></style>

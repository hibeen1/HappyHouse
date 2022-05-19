<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글작성</h3></b-alert>
      </b-col>
    </b-row>
    <board-input-item type="register" />
  </b-container>
</template>

<script>
import BoardInputItem from "@/components/announcement/item/AnnouncementInputItem.vue";

export default {
  name: "AnnouncementWrite",
  components: {
    BoardInputItem,
  },
};
</script>

<style></style>

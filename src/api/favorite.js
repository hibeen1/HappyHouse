import { apiInstance } from "./index.js";

const api = apiInstance();

async function listFavorite(userid, success, fail) {
  api.get(`/favorite`, { params: userid }).then(success).catch(fail);
}

async function addFavorite(params, success, fail) {
  console.log("add: " + params);
  api.post(`/favorite`, params).then(success).catch(fail);
}

async function deleteFavorite(params, success, fail) {
  console.log("delete: " + params.userid);
  api
    .delete(`/favorite`, {
      data: { userid: params.userid, aptCode: params.aptCode },
    })
    .then(success)
    .catch(fail);
}

export { listFavorite, addFavorite, deleteFavorite };

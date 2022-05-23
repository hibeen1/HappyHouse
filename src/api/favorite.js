import { apiInstance } from "./index.js";

const api = apiInstance();

function listFavorite(userid, success, fail) {
  api.get(`/favorite/${userid}`).then(success).catch(fail);
}

function addFavorite(params, success, fail) {
  console.log(params);
  api.post(`/favorite/add`, { params: params }).then(success).catch(fail);
}

function deleteFavorite(params, success, fail) {
  api.delete(`/favorite/${params[0]}`).then(success).catch(fail);
}

export { listFavorite, addFavorite, deleteFavorite };

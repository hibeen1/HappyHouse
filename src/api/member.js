import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

function registerMemeber(user, success, fail) {
  api.post(`/user/register`, JSON.stringify(user)).then(success).catch(fail);
}

function idCheck(userid, success, fail) {
  api.get(`/user/idcheck`, { params: userid }).then(success).catch(fail);
}

function modifyMember(userInfo, success, fail) {
  api
    .put(`/user/${userInfo.userid}`, JSON.stringify(userInfo))
    .then(success)
    .catch(fail);
}

function deleteMember(userid, success, fail) {
  api.delete(`/user/${userid}`).then(success).catch(fail);
}

export {
  login,
  findById,
  registerMemeber,
  idCheck,
  modifyMember,
  deleteMember,
};

// eslint-disable-next-line no-param-reassign
import axios from "axios";
import gui from "../gui";

const mainUrl = "https://183.182.100.198:8082/api";

axios.interceptors.response.use(
  (response) => response.data,
  (error) => Promise.reject(error)
);

// axios.defaults.withCredentials = true;

export function callApi(url, method, data = {}) {
  return axios({
    method,
    url: url || `${mainUrl}`,
    data,
  }).catch((e) => {
    console.log("error", e.response);
    if (e.response.data.status !== 100) {
      localStorage.setItem("token", "");
      localStorage.setItem("isdn", "");
      return (window.location.href = gui.urlUniId);
    }
  });
}

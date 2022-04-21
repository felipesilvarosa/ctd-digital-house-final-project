import axios from "axios"

axios.defaults.baseURL = "http://digitalbooking-t2g1.ctdprojetos.com.br:8080"
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';
axios.defaults.headers.get['Access-Control-Allow-Origin'] = '*';
axios.defaults.withCredentials = true
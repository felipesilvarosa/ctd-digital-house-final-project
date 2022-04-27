import axios from "axios"

const csrfToken = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*=\s*([^;]*).*$)|^.*$/, '$1')

axios.defaults.baseURL = "http://digitalbooking-t2g1.ctdprojetos.com.br:8080"
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';
axios.defaults.headers.get['Access-Control-Allow-Origin'] = '*';
axios.defaults.headers.common['X-XSRF-TOKEN'] = csrfToken
axios.defaults.withCredentials = true

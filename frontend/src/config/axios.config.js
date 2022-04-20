import axios from "axios"

axios.defaults.baseURL = "http://ec2-54-84-235-166.compute-1.amazonaws.com:8080"
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';
axios.defaults.headers.get['Access-Control-Allow-Origin'] = '*';
axios.defaults.withCredentials = true
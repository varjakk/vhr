import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'font-awesome/css/font-awesome.min.css'

import {deleteRequest, getRequest, postKeyValueRequest, postRequest, putRequest} from "@/utils/api";
import {initMenu} from "@/utils/menu";

Vue.prototype.getRequest = getRequest;
Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.postKeyValueRequest = postKeyValueRequest;


Vue.config.productionTip = false

Vue.use(ElementUI, {size: 'small'});

//路由导航守卫
router.beforeEach((to, from, next) => {
    //访问登录页，直接放行
    if (to.path === "/") {
        next();
    } else {
        //访问其他页面，需判断是否登录。
        //若已登录，则初始化菜单，否则跳转至登录页
        if (window.sessionStorage.getItem("user")) {
            initMenu(router, store);
            next();
        } else {
            next("/?redirect=" + to.path);
        }
    }
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')

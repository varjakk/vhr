import {getRequest} from './api';

//封装菜单请求工具类
export const initMenu = (router, store) => {
    if (store.state.routes.length > 0) {
        return;
    }
    getRequest("/system/config/menu").then(data => {
        if (data) {
            let fmtRoutes = formatRoutes(data);
            router.addRoutes(fmtRoutes);//将格式化后的路由添加到router中去
            store.commit("initRoutes", fmtRoutes)//调用initRoutes方法
        }
    })
}

//格式化从后台传过来的路由组件字符串的方法
export const formatRoutes = (routes) => {
    let fmtRoutes = [];
    routes.forEach(router => {
        let {
            name,
            component,
            path,
            meta,
            iconCls,
            children
        } = router;
        if (children && children instanceof Array) {
            children = formatRoutes(children);
        }
        let fmtRouter = {
            name: name,
            path: path,
            meta: meta,
            iconCls: iconCls,
            children: children,
            component(resolve) {
                if (component.startsWith("Home")) {
                    require(['../views/' + component + ".vue"], resolve);//字符串拼接出要加载的component路径
                } else if (component.startsWith("Emp")) {
                    require(['../views/emp/' + component + ".vue"], resolve);//字符串拼接出要加载的component路径
                } else if (component.startsWith("Per")) {
                    require(['../views/per/' + component + ".vue"], resolve);//字符串拼接出要加载的component路径
                } else if (component.startsWith("Sal")) {
                    require(['../views/sal/' + component + ".vue"], resolve);//字符串拼接出要加载的component路径
                } else if (component.startsWith("Sta")) {
                    require(['../views/sta/' + component + ".vue"], resolve);//字符串拼接出要加载的component路径
                } else if (component.startsWith("Sys")) {
                    require(['../views/sys/' + component + ".vue"], resolve);//字符串拼接出要加载的component路径)
                }
            }
        }
        fmtRoutes.push(fmtRouter);
    })
    return fmtRoutes;
}
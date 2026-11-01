import { createApp } from "vue";
/*
部署nginx方法：
部署:将打包好的dist目录下的文件 复制到nginx安装目录的html目录下
启动:双击nginx.exe文件即可 Nginx服务器默认占用80端口号 (在conf文件下的nginx.conf文件配置的)
访问:http://localhost:80
 */
import App from "./App.vue";
import router from "./router";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import zhCn from "element-plus/es/locale/lang/zh-cn";
import * as ElementPlusIconsVue from "@element-plus/icons-vue";

import "./assets/main.css";

const app = createApp(App);

app.use(router);
app.use(ElementPlus, { locale: zhCn });
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

app.mount("#app");

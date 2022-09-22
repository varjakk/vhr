module.exports = {
  transpileDependencies: true
}

let proxyObj = {};
proxyObj['/'] = {
  ws: false,
  // target: 'http://localhost:8081',
  target: 'http://101.35.217.8:8081',
  changeOrigin: true,
  pathRewrite: {
    '^/': ''
  }
}
module.exports = {
  devServer: {
    host: 'localhost',
    port: 8080,
    proxy: proxyObj
  }
}

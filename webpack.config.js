var webpack = require("webpack");
var path = require("path");

module.exports = {
  entry: "./src/main/script/index.js", // 项目打包入口文件
  output: {
    path: __dirname,
    filename: "./src/main/resources/static/built/bundle.js" // 项目打包出口文件
  },
  module: {
    loaders: [
      {
        test: /\.js[x]?$/,
        exclude: /node_modules/,
        loader: 'babel-loader',
        query: {
          cacheDirectory: true,
          presets: ['env', 'react', 'stage-2'],
          plugins: [
            ['transform-class-properties'],
            ["transform-runtime", {
              "polyfill": false,
              "regenerator": true
            }]
          ]
        }
      },
      {
        test: /\.css$/,
        loader: 'style-loader!css-loader'
      },
      {
        test: /\.less$/,
        loader: "style-loader!css-loader!less-loader"
      },
      {
        test:/\.(png|jpg|gif|svg)$/ ,
        use:[{
          loader:'url-loader',
          // options:{
          //   limit:5000,
          //   outputPath:'./src/main/resources/static/images/',
          // }
        }]
      },
      // {
      //   test: /\.(htm|html)$/i,
      //   use:[ 'html-withimg-loader']
      // }
      ]
  },
  watchOptions: {
    //检测修改的时间，以毫秒为单位
    poll:1000,
    //防止重复保存而发生重复编译错误。这里设置的500是半秒内重复保存，不进行打包操作
    aggregeateTimeout:500,
    //不监听的目录
    ignored:/node_modules/,
  }
};

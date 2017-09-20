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
        test: /\.(png|jpg)$/,
        loader: 'url-loader?limit=8192'
      }
      ]
  }
};

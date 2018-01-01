var webpack = require("webpack");
var path = require("path");

module.exports = {
  entry: "./src/main/script/index.js", // 项目打包入口文件
  output: {
    path: __dirname,
    filename: "./src/main/resources/static/built/bundle.js", // 项目打包出口文件
    // publicPath: "temp/"
  },
  // devServer: {
  //   contentBase: "./",
  //   host: "localhost",
  //   compress: true,
  //   port: 1717
  // },
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
        loader: 'style-loader!css-loader!less-loader'
      },
      {
        test:/\.(png|jpg|gif)$/ ,
        use:[{
          loader: 'url-loader',
          options: {
            limit: 8192,
            outputPath: './src/main/resources/static/images/',
          }
        }]
      },
      {
        test: /\.(htm|html)$/i,
        use: [ 'html-withimg-loader']
      }
      ]
  },
  watchOptions: {
    poll: 1000,
    aggregateTimeout: 500,
    ignored: /node-modules/,
  }
};

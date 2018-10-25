const webpack = require('webpack');
const path = require("path");
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require("extract-text-webpack-plugin");
const uglify = require('uglifyjs-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
  entry: {
    bundle: './src/main/script/index.js',
    vendor: ['react', 'react-dom', 'react-redux', 'react-router-dom', 'redux',
      'redux-saga', 'redux-thunk', 'prop-types'],
  },
  output: {
    path: path.resolve(__dirname, 'src/main/resources/static/'),
    filename: 'built/[name].[hash].js',
    chunkFilename: 'built/[name].[hash].js',
    publicPath: '/',
  },
  module: {
    rules: [
      {
        test: /\.js[x]?$/,
        exclude: /node_modules/,
        loader: 'babel-loader',
      },
      {
        test: /\.css$/,
        use: [
          {
            loader: 'style-loader',
          },
          {
            loader: 'css-loader',
          },
        ],
      },
      {
        test: /\.less$/,
        use: [
          {
            loader: 'style-loader',
          },
          {
            loader: 'css-loader',
          },
          {
            loader: 'less-loader',
            options: {
              javascriptEnabled: true,
            },
          },
        ],
      },
      {
        test: /\.(png|jpg|gif)$/,
        use: [{
          loader: 'url-loader',
          options: {
            limit: 8192,
            outputPath: './src/main/resources/static/images/',
          },
        }],
      },
    ],
  },
  plugins: [
    // new ExtractTextPlugin("built/bundle.css?[hash]"),
    new HtmlWebpackPlugin({
      template: './src/main/resources/html-template/index.html',
      filename: '../templates/index.html',
    }),
    // new uglify(),
    new CleanWebpackPlugin('./src/main/resources/static/built/*.*', {}),
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: JSON.stringify(process.env.NODE_ENV),
      }
    }),
    new webpack.optimize.CommonsChunkPlugin({
      name: 'vendor',
      minChunks: Infinity,
    }),
  ],
};

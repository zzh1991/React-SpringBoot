const webpack = require('webpack');
const path = require("path");
const HtmlWebpackPlugin = require('html-webpack-plugin');

const port = process.env.PORT || 3000;

module.exports = {
  entry: {
    bundle: './src/main/script/index.js',
    vendor: ['react', 'react-dom', 'react-redux', 'react-router-dom', 'redux',
    'redux-saga', 'redux-thunk', 'prop-types'],
  },
  output: {
    // path: path.resolve(__dirname, 'src/main/resources/static/built'),
    filename: '[name].js',
    publicPath: '/built/'
  },
  devtool: 'inline-source-map',
  module: {
    rules: [
      {
        test: /\.(js)$/,
        exclude: /node_modules/,
        use: ['babel-loader']
      },
      {
        test: /\.css$/,
        use: [
          {
            loader: 'style-loader'
          },
          {
            loader: 'css-loader',
            options: {
              modules: true,
              camelCase: true,
              sourceMap: true
            }
          }
        ]
      },
      {
        test: /\.less$/,
        use: [
          {
            loader: 'style-loader'
          },
          {
            loader: 'css-loader',
          },
          {
            loader: 'less-loader',
            options: {
              javascriptEnabled: true
            }
          }
        ]
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
    ]
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    new webpack.optimize.CommonsChunkPlugin({
      name: 'vendor',
      minChunks: Infinity,
    }),
  ],
  devServer: {
    host: 'localhost',
    port: port,
    proxy: {
      '*': {
        target: 'http://localhost:8080',
        secure: false,
        prependPath: false,
      },
    },
    historyApiFallback: true,
    open: false,
    hot: true,
  }
};
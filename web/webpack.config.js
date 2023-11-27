const path = require('path');
const CopyPlugin = require("copy-webpack-plugin");
const Dotenv = require('dotenv-webpack');

// Get the name of the appropriate environment variable (`.env`) file for this build/run of the app
const dotenvFile = process.env.API_LOCATION ? `.env.${process.env.API_LOCATION}` : '.env';

module.exports = {
  plugins: [
    new CopyPlugin({
      patterns: [
        {
          from: "static_assets", to: "../",
          globOptions: {
            ignore: ["**/.DS_Store"],
          },
        },
      ],
    }),
    new Dotenv({ path: dotenvFile }),
  ],
  optimization: {
    usedExports: true
  },
  entry: {
    createPlaylist: path.resolve(__dirname, 'src', 'pages', 'createPlaylist.js'),
    viewCities: path.resolve(__dirname, 'src', 'pages', 'viewCities.js'),
    viewCity: path.resolve(__dirname, 'src', 'pages', 'viewCity.js'),
    createNewActivity: path.resolve(__dirname, 'src', 'pages', 'createNewActivity.js'),
    viewActivity: path.resolve(__dirname, 'src', 'pages', 'viewActivity.js'),
    personalPage: path.resolve(__dirname, 'src', 'pages', 'personalPage.js'),
    editComment: path.resolve(__dirname, 'src', 'pages', 'editComment.js'),
    createComment: path.resolve(__dirname, 'src', 'pages', 'createComment.js'),
    searchPlaylists: path.resolve(__dirname, 'src', 'pages', 'searchPlaylists.js'),
    home: path.resolve(__dirname, 'src', 'pages', 'home.js'),
  },
  output: {
    path: path.resolve(__dirname, 'build', 'assets'),
    filename: '[name].js',
    publicPath: '/assets/'
  },
  devServer: {
    static: {
      directory: path.join(__dirname, 'static_assets'),
    },
    port: 8000,
    client: {
      // overlay shows a full-screen overlay in the browser when there are js compiler errors or warnings
      overlay: true,
    },
  }
}

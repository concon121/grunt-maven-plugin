var express = require('express');
var bodyParser = require('body-parser');

var app = express();

app.use(express.static(__dirname + '/'));

app.get('/registration', function (req, res) {
  res.sendFile(__dirname + '/index.html');
});

app.listen(3001);

module.exports = app;

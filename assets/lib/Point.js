'use strict';

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _get = function get(object, property, receiver) { if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { return get(parent, property, receiver); } } else if ("value" in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } };

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Point = function (_BaseElement) {
    _inherits(Point, _BaseElement);

    function Point() {
        _classCallCheck(this, Point);

        var _this = _possibleConstructorReturn(this, (Point.__proto__ || Object.getPrototypeOf(Point)).call(this));

        _this.canvas = document.getElementById('weather-canvas');
        _this.ctx = _this.canvas.getContext('2d');
        _this.x = Math.random() * _this.canvas.width;
        _this.y = 0;
        _this.x_speed = 0;
        _this.y_speed = 5;
        _this.style = 'rgb(255,255,255)';
        _this.diameter = 3;
        _this.isLive = true;
        _this.mouseSize = 150;
        _this.mousePower = 0.05;
        return _this;
    }

    _createClass(Point, [{
        key: 'draw',
        value: function draw() {
            this.ctx.beginPath();
            this.ctx.lineCap = 'round';
            this.ctx.lineWidth = this.diameter;
            this.ctx.strokeStyle = this.style;
            this.ctx.moveTo(this.x, this.y);
            this.nextPosition();
            this.ctx.lineTo(this.x, this.y);
            this.ctx.stroke();
            this.ctx.closePath();
            this.shouldRemove();
        }
    }, {
        key: 'nextPosition',
        value: function nextPosition() {
            this.x += this.x_speed;
            this.y += this.y_speed;
        }
    }, {
        key: '_getDist',
        value: function _getDist(point) {
            var tx = this.x - point.x;
            var ty = this.y - point.y;
            return Math.sqrt(tx * tx + ty * ty);
        }
    }, {
        key: 'makeWind',
        value: function makeWind(mouse) {
            _get(Point.prototype.__proto__ || Object.getPrototypeOf(Point.prototype), 'makeWind', this).call(this);
            if (this._getDist(mouse) < this.mouseSize) {
                this.x_speed += mouse.vx * this.mousePower;
                this.y_speed += mouse.vy * this.mousePower;
            }
        }
    }, {
        key: 'shouldRemove',
        value: function shouldRemove() {
            if (this.x > this.canvas.width * 1.2 || this.x < this.canvas.width * -0.2) {
                this.isLive = false;
            }
            if (this.y > this.canvas.height * 1.2 || this.y < this.canvas.height * -0.) {
                this.isLive = false;
            }
        }
    }]);

    return Point;
}(BaseElement);
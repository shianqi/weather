'use strict';

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Cloud = function (_Point) {
    _inherits(Cloud, _Point);

    function Cloud(args) {
        _classCallCheck(this, Cloud);

        var _this = _possibleConstructorReturn(this, (Cloud.__proto__ || Object.getPrototypeOf(Cloud)).call(this, args));

        _this.x = Math.random() * _this.canvas.width - _this.canvas.width * 0.2;
        _this.y = Math.random() * _this.canvas.height * 0.2 + _this.canvas.height * 0.1;
        _this.x_speed = Math.random() * 0.6 + 0.2;
        _this.y_speed = 0;
        _this.style = 'rgba(90,191,246,' + (Math.random() + 0.2) + ')';
        _this.diameter = Math.random() * 2 + 1;

        return _this;
    }

    _createClass(Cloud, [{
        key: 'draw',
        value: function draw() {
            var cloud_element = document.createElement('img');
            cloud_element.src = "./img/cloud_img.png";
            this.ctx.beginPath();
            this.ctx.lineCap = 'round';
            this.ctx.lineWidth = this.diameter;
            this.ctx.strokeStyle = this.style;
            this.ctx.globalAlpha = 0.9;
            this.ctx.drawImage(cloud_element, this.x - cloud_element.width / 2, this.y - cloud_element.height / 2);
            this.ctx.globalAlpha = 1;
            this.nextPosition();
            this.ctx.stroke();
            this.ctx.closePath();
            this.shouldRemove();
        }
    }]);

    return Cloud;
}(Point);
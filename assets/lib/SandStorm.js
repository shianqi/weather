'use strict';

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var SandStorm = function (_Point) {
    _inherits(SandStorm, _Point);

    function SandStorm(args) {
        _classCallCheck(this, SandStorm);

        var _this = _possibleConstructorReturn(this, (SandStorm.__proto__ || Object.getPrototypeOf(SandStorm)).call(this, args));

        _this.x = 0;
        _this.y = Math.random() * _this.canvas.height * 0.8;
        _this.x_speed = Math.random() * 10 - 0.5;
        _this.y_speed = Math.random() - 0.5;
        _this.style = 'rgba(90,191,246,' + (Math.random() + 0.2) + ')';
        _this.diameter = Math.random() * 2 + 1;

        return _this;
    }

    _createClass(SandStorm, [{
        key: 'draw',
        value: function draw() {
            var sand_element = document.createElement('img');
            sand_element.src = "./img/sand_img.png";
            this.ctx.beginPath();
            this.ctx.lineCap = 'round';
            this.ctx.lineWidth = this.diameter;
            this.ctx.strokeStyle = this.style;
            this.ctx.globalAlpha = 0.04;
            this.ctx.drawImage(sand_element, this.x - sand_element.width / 2, this.y - sand_element.height / 2);
            this.ctx.globalAlpha = 1;
            this.nextPosition();
            this.ctx.stroke();
            this.ctx.closePath();
            this.shouldRemove();
        }
    }]);

    return SandStorm;
}(Point);
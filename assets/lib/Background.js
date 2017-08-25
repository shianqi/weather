'use strict';

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Background = function (_BaseElement) {
    _inherits(Background, _BaseElement);

    function Background(mouse) {
        _classCallCheck(this, Background);

        var _this = _possibleConstructorReturn(this, (Background.__proto__ || Object.getPrototypeOf(Background)).call(this));

        _this.mouse = mouse;
        _this.shiftX = 40;
        _this.shiftY = 10;

        var background_element1 = document.createElement('img');
        background_element1.src = "./img/background1.png";
        var background_element2 = document.createElement('img');
        background_element2.src = "./img/background2.png";
        var background_element3 = document.createElement('img');
        background_element3.src = "./img/background3.png";
        var background_element4 = document.createElement('img');
        background_element4.src = "./img/background4.png";

        _this.background = [{
            element: background_element1,
            layer: 0
        }, {
            element: background_element2,
            layer: 33.33
        }, {
            element: background_element3,
            layer: 66.67
        }, {
            element: background_element4,
            layer: 100
        }];

        return _this;
    }

    _createClass(Background, [{
        key: 'draw',
        value: function draw() {
            var _this2 = this;

            this.ctx.globalAlpha = 1;
            var scale = 0;
            var dx = 0;
            var dy = 0;
            this.height = this.background[0].element.height;
            this.width = this.background[0].element.width;
            if (this.canvas.height > this.canvas.width) {
                scale = this.canvas.height / this.height;
                dx = (this.canvas.width - this.width * scale) / 2;
            } else {
                scale = this.canvas.width / this.width;
                dy = (this.canvas.height - this.height * scale) / 2;
            }

            this.background.forEach(function (item) {
                var element = item.element,
                    layer = item.layer;

                var drawX = dx + (_this2.mouse.x - _this2.canvas.width / 2) / (_this2.canvas.width / 2) * _this2.shiftX * (layer / 50 - 1);
                var drawY = dy + (_this2.mouse.y - _this2.canvas.height / 2) / (_this2.canvas.height / 2) * _this2.shiftY * (layer / 50 - 1);
                _this2.ctx.drawImage(element, drawX - _this2.shiftX, drawY - _this2.shiftY, element.width * scale + 2 * _this2.shiftX, element.height * scale + 2 * _this2.shiftY);
            });
            this.ctx.globalAlpha = 1;
        }
    }]);

    return Background;
}(BaseElement);
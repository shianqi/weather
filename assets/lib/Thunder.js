"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Thunder = function (_BaseElement) {
    _inherits(Thunder, _BaseElement);

    function Thunder() {
        _classCallCheck(this, Thunder);

        var _this = _possibleConstructorReturn(this, (Thunder.__proto__ || Object.getPrototypeOf(Thunder)).call(this));

        _this.alpha = 0;
        _this.interval = 15;
        _this.nowInterval = 0;
        return _this;
    }

    _createClass(Thunder, [{
        key: "draw",
        value: function draw() {
            this.ctx.fillStyle = "rgba(255,255,255," + this.alpha + ")";
            this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);

            this._nextPosition();
        }
    }, {
        key: "makeWind",
        value: function makeWind() {}
    }, {
        key: "_nextPosition",
        value: function _nextPosition() {
            if (Math.random() < 0.002) {
                this.alpha = 0.7;
                this.nowInterval = this.interval;
            }

            if (this.nowInterval > 1) {
                this.nowInterval--;
            } else if (this.nowInterval === 1) {
                this.alpha = 1;
                this.nowInterval = 0;
            }
            if (this.alpha >= 0) {
                this.alpha = this.alpha - 0.06;
            }
        }
    }]);

    return Thunder;
}(BaseElement);
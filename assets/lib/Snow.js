"use strict";

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Snow = function (_Point) {
    _inherits(Snow, _Point);

    function Snow(args) {
        _classCallCheck(this, Snow);

        var _this = _possibleConstructorReturn(this, (Snow.__proto__ || Object.getPrototypeOf(Snow)).call(this, args));

        _this.x_speed = Math.random() * 2 - 1;
        _this.y_speed = Math.random() * 0.6 + 0.5;
        _this.style = "rgba(255,255,255," + (Math.random() + 0.2) + ")";
        _this.diameter = Math.random() * 5 + 2;
        return _this;
    }

    return Snow;
}(Point);
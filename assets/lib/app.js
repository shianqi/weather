'use strict';

var canvas = document.getElementById('weather-canvas');
var ctx = canvas.getContext('2d');
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;
window.onresize = function () {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
};
document.documentElement.style.overflowY = 'hidden';
window.requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame || window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;

function Weather() {
    this.pointArray = [];
    this.weather = 0;
    this.mouse = {
        vx: 0,
        vy: 0,
        px: 0,
        py: 0,
        x: 0,
        y: 0
    };
};

Weather.prototype.draw = function () {
    this.pointArray.push(new Background(this.mouse));
    this.pointArray.push(new Thunder());

    canvas.addEventListener('touchstart', this.updateTouchStart.bind(this), {
        capture: false,
        passive: true,
        once: false
    });
    canvas.addEventListener('touchmove', this.updateTouch.bind(this), {
        capture: false,
        passive: false,
        once: false
    });
    canvas.addEventListener('mousemove', this.updateMouse.bind(this), {
        capture: false,
        passive: true,
        once: false
    });
    this.nextPosition();
};

Weather.prototype.updateTouchStart = function (e) {
    this.mouse.x = e.touches[0].pageX;
    this.mouse.y = e.touches[0].pageY;
};

Weather.prototype.updateTouch = function (e) {
    e.preventDefault();
    this.mouse.px = this.mouse.x;
    this.mouse.py = this.mouse.y;

    this.mouse.x = e.touches[0].pageX;
    this.mouse.y = e.touches[0].pageY;

    this.mouse.vx = this.mouse.x - this.mouse.px;
    this.mouse.vy = this.mouse.y - this.mouse.py;

    this.makeWind(this.mouse);
};

Weather.prototype.nextPosition = function () {
    Thunder.shouldDraw = false;
    if (this.weather === 0) {
        this.addPoint(Snow, 1);
    } else if (this.weather === 1) {
        this.addPoint(Rain, 1);
        this.addPoint(Cloud, 0.03);
        this.addPoint(BlackCloud, 0.1);
    } else if (this.weather === 2) {
        this.addPoint(Fog, 0.5);
    } else if (this.weather === 3) {
        this.addPoint(Cloud, 0.1);
    } else if (this.weather === 4) {
        this.addPoint(SandStorm, 0.2);
    } else if (this.weather === 6) {
        this.addPoint(Rain, 1);
        this.addPoint(Snow, 1);
    } else if (this.weather === 7) {
        this.addPoint(Cloud, 0.03);
        this.addPoint(BlackCloud, 0.1);
    } else if (this.weather === 8) {
        this.addPoint(Rain, 1);
        this.addPoint(Cloud, 0.03);
        this.addPoint(BlackCloud, 0.1);
        Thunder.shouldDraw = true;
    }
    for (var i = 0; i < this.pointArray.length; i++) {
        this.pointArray[i].draw();
    }
    this.pointArray = this.pointArray.filter(function (item) {
        return item.isLive;
    });
    requestAnimationFrame(this.nextPosition.bind(this));
};

Weather.prototype.updateMouse = function (e) {
    this.mouse.px = this.mouse.x;
    this.mouse.py = this.mouse.y;

    this.mouse.x = e.clientX;
    this.mouse.y = e.clientY;

    this.mouse.vx = this.mouse.x - this.mouse.px;
    this.mouse.vy = this.mouse.y - this.mouse.py;

    this.makeWind(this.mouse);
};

Weather.prototype.addPoint = function (Ele, probability) {
    if (probability === 1) {
        this.pointArray.push(new Ele());
    } else if (Math.random() < probability) {
        this.pointArray.push(new Ele());
    }
};

Weather.prototype.makeWind = function (mouse) {
    for (var i = 0; i < this.pointArray.length; i++) {
        this.pointArray[i].makeWind(mouse);
    }
};

Weather.prototype.changeWeather = function (number) {
    this.weather = number;
};

var weather = new Weather();
weather.draw();

function changeWeather(num) {
    weather.changeWeather(num);
}
var Flipper = /** @class */ (function () {
    function Flipper(node, currentTime, nextTime) {
        this.isFlipping = false;
        this.duration = 600;
        this.flipNode = node;
        this.frontNode = node.querySelector(".front");
        this.backNode = node.querySelector(".back");
        this.setFrontTime(currentTime);
        this.setBackTime(nextTime);
    }

    Flipper.prototype.setFrontTime = function (time) {
        this.frontNode.dataset.number = time;
    };
    Flipper.prototype.setBackTime = function (time) {
        this.backNode.dataset.number = time;
    };
    Flipper.prototype.flipDown = function (currentTime, nextTime) {
        var _this = this;
        if (this.isFlipping) {
            return false;
        }
        this.isFlipping = true;
        this.setFrontTime(currentTime);
        this.setBackTime(nextTime);
        this.flipNode.classList.add("running");
        setTimeout(function () {
            _this.flipNode.classList.remove("running");
            _this.isFlipping = false;
            _this.setFrontTime(nextTime);
        }, this.duration);
    };
    return Flipper;
}());
var getTimeFromDate = function (date) {
    return date
        .toTimeString()
        .slice(0, 8)
        .split(":")
        .join("");
};
var flips = document.querySelectorAll(".flip");
var endTime = new Date("2020/09/17,00:05:20");
var startTime= new Date("2020/09/17,00:00:00");
var timeDifferenceFix=57600000;

var nowTimeStr = getTimeFromDate(new Date(endTime.getTime()-startTime.getTime() +timeDifferenceFix));
var nextTimeStr = getTimeFromDate(new Date(endTime.getTime()-startTime.getTime()+timeDifferenceFix-1000));
var flippers = Array.from(flips).map(function (flip, i) {
    return new Flipper(flip, nowTimeStr[i], nextTimeStr[i]);
});

for (var i = 0; i < flippers.length; i++) {
    if (nowTimeStr[i] === nextTimeStr[i]) {
        continue;
    }
    flippers[i].flipDown(nowTimeStr[i], nextTimeStr[i]);
};


var test=new Date(endTime.getTime()-startTime.getTime()+timeDifferenceFix-1000)

setInterval(function () {

    var nowTime=test;
    var nextTime=new Date(test.getTime()-1000);

    test=new Date(test.getTime()-1000);

    var nowTimeStr = getTimeFromDate(nowTime);

    var nextTimeStr = getTimeFromDate(nextTime);
    for (var i = 0; i < flippers.length; i++) {
        if (nowTimeStr[i] === nextTimeStr[i]) {
            continue;
        }
        flippers[i].flipDown(nowTimeStr[i], nextTimeStr[i]);
    }

    //可以写alert！！！！！！！
    //
    //
    //
    //
    //
    //
}, 1000);
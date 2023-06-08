// 스크롤의 높이에 따른 글자색 변경 이벤트(높이 900기준으로 변경하기)
var colors = [
    '#fff',
    '#000',
]

var container = document.getElementById('container');
var text = document.getElementById('value');
var color = document.getElementById('color');

container.onwheel = changeBgColor;

var colorIndex = 0;
var scrollValue = 0;
var dateNow = Date.now();

function changeBgColor(e) {
    scrollValue += e.deltaY * 0.01;
    text.textContent = Math.floor(scrollValue);
  
    timePassed = Date.now() - dateNow;
    if (scrollValue > 900 && timePassed > 500) {
        dateNow = Date.now();
        colorIndex += 1;
        if (colorIndex > colors.length-1) colorIndex = 0;
        color.textContent = colors[colorIndex];
        container.style.backgroundColor = colors[colorIndex];
        scrollValue = 0;
    }

    if (scrollValue < -900 && timePassed > 500) {
        dateNow = Date.now();
        colorIndex -= 1;
        if (colorIndex < 0) colorIndex = colors.length-1;
        color.textContent = colors[colorIndex];
        container.style.backgroundColor = colors[colorIndex];
        scrollValue = 0;
    }
    e.preventDefault(); // disable the actual scrolling
}
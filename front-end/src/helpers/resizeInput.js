const resizeInput = (arr) => {
  const events = 'keyup,keypress,focus,blur,change,input'.split(',');

  for (let i = 0; i < arr.length; i++) {
    const spanEl = document.createElement('span');
    spanEl.className = 'span-helper';
    spanEl.innerHTML = arr[i].value;
    arr[i].after(spanEl);
    arr[i].style.width = spanEl.clientWidth + 'px';
    spanEl.remove();

    events.forEach(function (item) {
      arr[i].addEventListener(item, function () {
        const spanEl = document.createElement('span');
        spanEl.className = 'span-helper';
        spanEl.innerHTML = arr[i].value;
        arr[i].after(spanEl);
        arr[i].style.width = spanEl.clientWidth + 'px';
        spanEl.remove();
      });
    });
  }
};

export default resizeInput;